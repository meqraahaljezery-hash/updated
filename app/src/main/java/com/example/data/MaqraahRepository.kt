package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object MaqraahRepository {
    private val _currentUser = MutableStateFlow<User?>(null) // Clean start: ready for real students
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _currentLanguage = MutableStateFlow("ar") // Arabic default
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList()) // Clean empty initial messages
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _rechargeRequests = MutableStateFlow<List<RechargeRequest>>(emptyList()) // Clean empty recharge requests
    val rechargeRequests: StateFlow<List<RechargeRequest>> = _rechargeRequests.asStateFlow()

    val packages = listOf(
        PaymentPackage("p1", 100, 500, "100 ደቂቃዎች - 500 ብር", "100 دقيقة - 500 بير", "100 Minutes - 500 ETB"),
        PaymentPackage("p2", 200, 1000, "200 ደቂቃዎች - 1,000 ብር", "200 دقيقة - 1,000 بير", "200 Minutes - 1,000 ETB"),
        PaymentPackage("p3", 300, 1470, "300 ደቂቃዎች - 1,470 ብር", "300 دقيقة - 1,470 بير", "300 Minutes - 1,470 ETB"),
        PaymentPackage("p4", 400, 1920, "400 ደቂቃዎች - 1,920 ብር", "400 دقيقة - 1,920 بير", "400 Minutes - 1,920 ETB"),
        PaymentPackage("p5", 500, 2350, "500 ደቂቃዎች - 2,350 ብር", "500 دقيقة - 2,350 بير", "500 Minutes - 2,350 ETB"),
        PaymentPackage("p6", 600, 2760, "600 ደቂቃዎች - 2,760 ብር", "600 دقيقة - 2,760 بير", "600 Minutes - 2,760 ETB"),
        PaymentPackage("p7", 1000, 4500, "1,000 ደቂቃዎች - 4,500 ብር", "1,000 دقيقة - 4,500 بير", "1,000 Minutes - 4,500 ETB")
    )

    val schedules = listOf(
        LessonSchedule("Tuesday, Wednesday, Thursday", "Men Sessions", "Men", "Morning: 09:00-12:00 | Afternoon: 14:00-15:30 | Night: 21:00-23:00"),
        LessonSchedule("Saturday, Sunday, Monday", "Women Sessions", "Women", "Morning: 09:00-12:00 | Afternoon: 14:00-15:30 | Night: 21:00-23:00"),
        LessonSchedule("Friday", "Weekly Vacation", "All", "No scheduled classes. Friday is weekly rest day.")
    )

    fun login(email: String, name: String, role: UserRole, gender: Gender) {
        val code = if (role == UserRole.STUDENT) {
            if (gender == Gender.MALE) "IM-${(10000..99999).random()}" else "IF-${(10000..99999).random()}"
        } else null

        val isSuperAdmin = email.lowercase() == "khdahd241@gmail.com"
        val assignedRole = if (isSuperAdmin) UserRole.SUPER_ADMIN else role

        _currentUser.value = User(
            id = "user_${System.currentTimeMillis()}",
            name = name,
            email = email,
            role = assignedRole,
            studentCode = code,
            gender = gender,
            minutesBalance = 35 // 35 free welcome minutes for real registered students
        )
    }

    fun logout() {
        _currentUser.value = null
    }

    fun setLanguage(lang: String) {
        _currentLanguage.value = lang
    }

    fun sendMessage(text: String, recipientId: String, isAudio: Boolean = false, duration: Int = 0) {
        val user = _currentUser.value ?: return
        val msg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            senderId = user.id,
            senderName = user.name,
            recipientId = recipientId,
            text = text,
            timestamp = System.currentTimeMillis(),
            isAudio = isAudio,
            audioDurationSeconds = duration
        )
        _messages.update { it + msg }
    }

    fun submitRecharge(packageMinutes: Int, priceEtb: Int, ref: String, method: String) {
        val user = _currentUser.value ?: return
        val req = RechargeRequest(
            id = "req_${System.currentTimeMillis()}",
            studentId = user.id,
            studentName = user.name,
            studentCode = user.studentCode ?: "IM-00000",
            packageMinutes = packageMinutes,
            priceEtb = priceEtb,
            transactionRef = ref,
            paymentMethod = method,
            timestamp = System.currentTimeMillis(),
            status = RechargeStatus.PENDING
        )
        _rechargeRequests.update { it + req }
    }

    fun approveRecharge(requestId: String) {
        _rechargeRequests.update { list ->
            list.map { if (it.id == requestId) it.copy(status = RechargeStatus.APPROVED) else it }
        }
    }

    fun rejectRecharge(requestId: String) {
        _rechargeRequests.update { list ->
            list.map { if (it.id == requestId) it.copy(status = RechargeStatus.REJECTED) else it }
        }
    }

    fun deductLessonMinutes(minutes: Int) {
        _currentUser.update { user ->
            user?.copy(minutesBalance = maxOf(0, user.minutesBalance - minutes))
        }
    }
}
