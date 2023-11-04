package com.sina.testchat.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "message")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
){
    val createDate: String
        get() = DateFormat.getDateInstance().format(timestamp)
}