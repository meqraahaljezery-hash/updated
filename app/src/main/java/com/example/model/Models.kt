package com.example.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole, // SUPER_ADMIN, ADMIN, TEACHER, STUDENT
    val studentCode: String?, // e.g. IM-10492 or IF-08214
    val gender: Gender,
    val minutesBalance: Int = 35 // 35 free welcome minutes
)

enum class UserRole {
    SUPER_ADMIN, ADMIN, TEACHER, STUDENT
}

enum class Gender {
    MALE, FEMALE
}

data class ChatMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val recipientId: String, // group ID or user ID
    val text: String,
    val timestamp: Long,
    val isAudio: Boolean = false,
    val audioDurationSeconds: Int = 0,
    val isDelivered: Boolean = true
)

data class LessonSchedule(
    val dayOfWeek: String,
    val sessionName: String, // Morning (09:00-12:00), Afternoon (14:00-15:30), Night (21:00-23:00)
    val targetAudience: String, // Men: Tue, Wed, Thu | Women: Sat, Sun, Mon
    val description: String
)

data class PaymentPackage(
    val id: String,
    val minutes: Int,
    val priceEtb: Int,
    val titleAm: String,
    val titleAr: String,
    val titleEn: String
)

data class RechargeRequest(
    val id: String,
    val studentId: String,
    val studentName: String,
    val studentCode: String,
    val packageMinutes: Int,
    val priceEtb: Int,
    val transactionRef: String,
    val paymentMethod: String, // Telebirr, CBE, M-Pesa, Bank of Abyssinia
    val timestamp: Long,
    val status: RechargeStatus // PENDING, APPROVED, REJECTED
)

enum class RechargeStatus {
    PENDING, APPROVED, REJECTED
}
