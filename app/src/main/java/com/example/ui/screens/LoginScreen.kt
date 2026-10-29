package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Gender
import com.example.model.UserRole
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SoftGreenSurface

@Composable
fun LoginScreen(
    currentLang: String,
    onLanguageChange: (String) -> Unit,
    onLogin: (email: String, name: String, role: UserRole, gender: Gender) -> Unit
) {
    var selectedLang by remember { mutableStateOf(currentLang) }
    var emailInput by remember { mutableStateOf("khdahd241@gmail.com") }
    var nameInput by remember { mutableStateOf("Khalid Ahmed Mustafa") }
    var selectedRole by remember { mutableStateOf(UserRole.SUPER_ADMIN) }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Language Selector Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                listOf("am" to "አማርኛ", "ar" to "العربية", "en" to "English").forEach { (code, label) ->
                    TextButton(
                        onClick = {
                            selectedLang = code
                            onLanguageChange(code)
                        }
                    ) {
                        Text(
                            text = label,
                            color = if (selectedLang == code) PrimaryGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = if (selectedLang == code) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Brand Header
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SoftGreenSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "مَقْرَأَةُ الإِمَامِ الْجَزَرِيِّ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Maqraah Imam Al-Jazari — Sanad & Ijazah",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Full Name / الاسم الكامل") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = emailInput,
                onValueChange = { emailInput = it },
                label = { Text("Email / البريد الإلكتروني") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Role selection buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { selectedRole = UserRole.SUPER_ADMIN; emailInput = "khdahd241@gmail.com" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRole == UserRole.SUPER_ADMIN) PrimaryGreen else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("Super Admin")
                }
                Button(
                    onClick = { selectedRole = UserRole.TEACHER },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRole == UserRole.TEACHER) PrimaryGreen else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("Muqri' (Teacher)")
                }
                Button(
                    onClick = { selectedRole = UserRole.STUDENT },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRole == UserRole.STUDENT) PrimaryGreen else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text("Student")
                }
            }

            if (selectedRole == UserRole.STUDENT) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Gender: ")
                    Spacer(modifier = Modifier.width(8.dp))
                    RadioButton(
                        selected = selectedGender == Gender.MALE,
                        onClick = { selectedGender = Gender.MALE }
                    )
                    Text("Male (IM-)")
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = selectedGender == Gender.FEMALE,
                        onClick = { selectedGender = Gender.FEMALE }
                    )
                    Text("Female (IF-)")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onLogin(emailInput, nameInput, selectedRole, selectedGender) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text(
                    text = "Continue with Google / ߊߟߌߞߏ߲ / መግቢያ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
