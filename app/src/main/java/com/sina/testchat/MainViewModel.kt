package com.sina.testchat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.testchat.db.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {
    private val _newMessageReceived = MutableStateFlow(false)
    val newMessageReceived: StateFlow<Boolean> = _newMessageReceived.asStateFlow()

    private val _messageList = MutableStateFlow(emptyList<Message>())
    val messageList: StateFlow<List<Message>> = _messageList.asStateFlow()

    private var messageCounter = 0

    init {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val currentMessageList = _messageList.value
                val newMessage = Message(text = "New Message $messageCounter")
                messageCounter++
                val updatedList = currentMessageList.toMutableList().apply { add(newMessage) }
                _messageList.value = updatedList
                _newMessageReceived.value = true

                // بعد از مدتی (مثلاً 100 میلی‌ثانیه) مقدار _newMessageReceived را دوباره به false تنظیم می‌کنیم
                viewModelScope.launch {
                    delay(100)
                    _newMessageReceived.value = false
                }
            }
        }, 1000, 1000)
    }

    // شما می‌توانید از این تابع برای کنترل تغییر _newMessageReceived به صورت دستی استفاده کنید
    fun setNewMessageReceived(value: Boolean) {
        viewModelScope.launch {
            _newMessageReceived.emit(value)
        }
    }
}