package com.example.chatexample

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.chatexample.helper.SettingsHelper

@Composable
fun Settings(
    onClick: () -> Unit
) {
    var url by remember { mutableStateOf(SettingsHelper.url ?: "wss://echo.websocket.org/") }

    Column {
        TextField(
            value = url,
            onValueChange = {
                url = it
                SettingsHelper.url = it // сохраняем сразу
            },
            label = { Text("WebSocket URL") }
        )
        Button(
            onClick = { onClick() }
        ) {
            Text("Установить подключение")
        }
    }

}



