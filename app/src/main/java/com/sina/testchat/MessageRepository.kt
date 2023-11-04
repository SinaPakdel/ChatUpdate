package com.sina.testchat

import com.sina.testchat.db.Message
import com.sina.testchat.db.MessageDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(private val messageDao: MessageDao) {
    val messages: Flow<List<Message>> = messageDao.getAllMessages()

    suspend fun insertMessage(message: Message) {
        messageDao.insertMessage(message)
    }
}