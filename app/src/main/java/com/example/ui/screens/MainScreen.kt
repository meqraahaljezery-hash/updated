package com.example.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.model.PaymentPackage
import com.example.model.RechargeRequest
import com.example.model.User
import com.example.model.UserRole
import com.example.ui.theme.PrimaryGreen

@Composable
fun MainScreen(
    currentUser: User?,
    currentLang: String,
    messages: List<com.example.model.ChatMessage>,
    schedules: List<com.example.model.LessonSchedule>,
    packages: List<PaymentPackage>,
    rechargeRequests: List<RechargeRequest>,
    onLanguageChange: (String) -> Unit,
    onSendMessage: (String, String, Boolean, Int) -> Unit,
    onSubmitRecharge: (Int, Int, String, String) -> Unit,
    onApproveRecharge: (String) -> Unit,
    onRejectRecharge: (String) -> Unit,
    onDeductMinutes: (Int) -> Unit,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // Default to Live Call room as requested ("اذا فتت التطبيق لا بد يشتغل مكالمه")
    val isSuperAdmin = currentUser?.email?.lowercase() == "khdahd241@gmail.com" || currentUser?.role == UserRole.SUPER_ADMIN

    val tabs = mutableListOf<Triple<String, androidx.compose.ui.graphics.vector.ImageVector, String>>(
        Triple("Live Call", Icons.Default.PhoneInTalk, "المكالمة الحية"),
        Triple("Chats", Icons.Default.Chat, "المحادثات"),
        Triple("Learning", Icons.Default.MenuBook, "التعليم"),
        Triple("Wallet", Icons.Default.AccountBalance, "المحفظة"),
        Triple("Profile", Icons.Default.Person, "الملف الشخصي")
    )

    if (isSuperAdmin) {
        tabs.add(4, Triple("Admin", Icons.Default.Security, "الإدارة"))
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                tabs.forEachIndexed { index, (title, icon, _ ) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryGreen,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            when {
                selectedTab == 0 -> LiveRecitationScreen(currentUser, onLeaveCall = { selectedTab = 1 })
                selectedTab == 1 -> ChatsScreen(currentUser, messages, onSendMessage)
                selectedTab == 2 -> LearningScreen(currentUser, schedules, onDeductMinutes)
                selectedTab == 3 -> WalletScreen(currentUser, packages, onSubmitRecharge)
                isSuperAdmin && selectedTab == 4 -> AdminScreen(currentUser, rechargeRequests, onApproveRecharge, onRejectRecharge)
                else -> ProfileScreen(currentUser, currentLang, onLanguageChange, onLogout)
            }
        }
    }
}
