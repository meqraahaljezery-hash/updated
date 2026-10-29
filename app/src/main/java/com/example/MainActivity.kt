package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MaqraahViewModel

class MainActivity : ComponentActivity() {
  private val viewModel: MaqraahViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val currentUser by viewModel.currentUser.collectAsState()
        val currentLang by viewModel.currentLanguage.collectAsState()
        val messages by viewModel.messages.collectAsState()
        val rechargeRequests by viewModel.rechargeRequests.collectAsState()
        val packages = viewModel.packages
        val schedules = viewModel.schedules

        val layoutDirection = if (currentLang == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
          if (currentUser == null) {
            LoginScreen(
              currentLang = currentLang,
              onLanguageChange = { viewModel.setLanguage(it) },
              onLogin = { email, name, role, gender -> viewModel.login(email, name, role, gender) }
            )
          } else {
            MainScreen(
              currentUser = currentUser,
              currentLang = currentLang,
              messages = messages,
              schedules = schedules,
              packages = packages,
              rechargeRequests = rechargeRequests,
              onLanguageChange = { viewModel.setLanguage(it) },
              onSendMessage = { text, recipientId, isAudio, duration -> viewModel.sendMessage(text, recipientId, isAudio, duration) },
              onSubmitRecharge = { minutes, price, ref, method -> viewModel.submitRecharge(minutes, price, ref, method) },
              onApproveRecharge = { viewModel.approveRecharge(it) },
              onRejectRecharge = { viewModel.rejectRecharge(it) },
              onDeductMinutes = { viewModel.deductLessonMinutes(it) },
              onLogout = { viewModel.logout() }
            )
          }
        }
      }
    }
  }
}
