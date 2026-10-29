package com.example.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val senderId: String,
    val senderName: String,
    val recipientId: String,
    val text: String,
    val timestamp: Long,
    val isAudio: Boolean,
    val audioDurationSeconds: Int,
    val isDelivered: Boolean
)
