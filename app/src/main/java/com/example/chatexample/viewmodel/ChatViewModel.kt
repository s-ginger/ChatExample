package com.example.chatexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.*

class ChatViewModel() : ViewModel(){

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    var urlMain: String? = null;

    private var webSocket: WebSocket? = null

    private val client = OkHttpClient()

    fun setUrl(url: String) {
        urlMain = url
        connectWebSocket()
    }

    private fun connectWebSocket() {
        val request = Request.Builder()
            .url(urlMain!!) // тестовый эхо-сервер
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(ws: WebSocket, text: String) {
                viewModelScope.launch {
                    _messages.value = _messages.value + "Сервер: $text"
                }
            }
        })
    }

    fun sendMessage(msg: String) {
        if (msg.isNotBlank()) {
            webSocket?.send(msg)
            _messages.value = _messages.value + "Я: $msg"
        }
    }

    override fun onCleared() {
        super.onCleared()
        webSocket?.close(1000, "Закрыто пользователем")
        client.dispatcher.executorService.shutdown()
    }

}