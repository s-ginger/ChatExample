package com.example.chatexample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatexample.viewmodel.ChatViewModel

@Composable
fun TextOthers(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun TextYour(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .padding(8.dp),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun App(modifier: Modifier, viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()

    LazyColumn (
        modifier = modifier.fillMaxWidth()
    ) {
        items(messages.size) { index ->
            if (messages[index].startsWith("Я")) {
                TextYour(messages[index])
            } else {
                TextOthers(messages[index])
            }
        }
    }
}
