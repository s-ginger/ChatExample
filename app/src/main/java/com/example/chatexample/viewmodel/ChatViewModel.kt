package com.example.chatexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import org.json.JSONObject

class ChatViewModel() : ViewModel() {

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    var urlMain: String? = null
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()
    var userId: String? = null

    fun setUrl(url: String) {
        urlMain = url
        connectWebSocket()
    }

    private fun connectWebSocket() {
        val request = Request.Builder()
            .url(urlMain!!)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(ws: WebSocket, response: Response) {
                // при подключении отправляем свой id
                val json = JSONObject()
                json.put("id", userId)
                ws.send(json.toString())
            }

            override fun onMessage(ws: WebSocket, text: String) {
                try {
                    val data = JSONObject(text)
                    val from = data.optString("from", "Сервер")
                    val msg = data.optString("message", text)

                    viewModelScope.launch {
                        _messages.value = _messages.value + "$from: $msg"
                    }
                } catch (e: Exception) {
                    // если это не JSON
                    viewModelScope.launch {
                        _messages.value = _messages.value + "Сервер: $text"
                    }
                }
            }

            override fun onMessage(ws: WebSocket, bytes: ByteString) {
                // бинарные сообщения
                viewModelScope.launch {
                    _messages.value = _messages.value + "Получены байты: ${bytes.hex()}"
                }
            }
        })
    }

    fun sendMessage(msg: String) {
        if (msg.isNotBlank()) {
            val json = JSONObject()
            json.put("message", msg)
            webSocket?.send(json.toString())

            _messages.value = _messages.value + "Я: $msg"
        }
    }

    override fun onCleared() {
        super.onCleared()
        webSocket?.close(1000, "Закрыто пользователем")
        client.dispatcher.executorService.shutdown()
    }
}