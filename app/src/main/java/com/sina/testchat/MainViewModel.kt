package com.sina.testchat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.testchat.db.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: MessageRepository) : ViewModel() {
    private val _newMessageReceived = MutableStateFlow(false)
    val newMessageReceived: StateFlow<Boolean> = _newMessageReceived.asStateFlow()

    private val _messageList = MutableStateFlow(emptyList<Message>())
    val messageList: StateFlow<List<Message>> = _messageList.asStateFlow()

    private var messageCounter = 0
    private val newMessages = MutableStateFlow(emptyList<Message>())

    init {
        getMessage()
        viewModelScope.launch {
            while (true) {
                val newMessageText = generateRandomWord(4)
                val newMessage = Message(text = newMessageText)
                messageCounter++
                repository.insertMessage(newMessage)
                newMessages.value += newMessage
                _newMessageReceived.value = true
                delay(1000)
                _newMessageReceived.value = false
                delay(1000)
            }
        }
    }

    private fun getMessage() {
        viewModelScope.launch {
            repository.messages.collectLatest { messages ->
                _messageList.value = messages
                val updatedMessages = newMessages.value + messages
                newMessages.value = emptyList()
                _messageList.value = updatedMessages
            }
        }
    }
}

fun generateRandomWord(length: Int): String {
    val allowedChars = ('a'..'z') + ('A'..'Z')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}