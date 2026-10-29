package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.MaqraahRepository
import com.example.model.UserRole
import com.example.model.Gender
import kotlinx.coroutines.flow.StateFlow

class MaqraahViewModel : ViewModel() {
    val currentUser = MaqraahRepository.currentUser
    val currentLanguage = MaqraahRepository.currentLanguage
    val messages = MaqraahRepository.messages
    val rechargeRequests = MaqraahRepository.rechargeRequests
    val packages = MaqraahRepository.packages
    val schedules = MaqraahRepository.schedules

    fun login(email: String, name: String, role: UserRole, gender: Gender) {
        MaqraahRepository.login(email, name, role, gender)
    }

    fun logout() {
        MaqraahRepository.logout()
    }

    fun setLanguage(lang: String) {
        MaqraahRepository.setLanguage(lang)
    }

    fun sendMessage(text: String, recipientId: String, isAudio: Boolean = false, duration: Int = 0) {
        MaqraahRepository.sendMessage(text, recipientId, isAudio, duration)
    }

    fun submitRecharge(packageMinutes: Int, priceEtb: Int, ref: String, method: String) {
        MaqraahRepository.submitRecharge(packageMinutes, priceEtb, ref, method)
    }

    fun approveRecharge(requestId: String) {
        MaqraahRepository.approveRecharge(requestId)
    }

    fun rejectRecharge(requestId: String) {
        MaqraahRepository.rejectRecharge(requestId)
    }

    fun deductLessonMinutes(minutes: Int) {
        MaqraahRepository.deductLessonMinutes(minutes)
    }
}
