package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PaymentPackage
import com.example.model.User
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SoftGreenSurface
import com.example.ui.theme.VeryLightGreenSurface

@Composable
fun WalletScreen(
    currentUser: User?,
    packages: List<PaymentPackage>,
    onSubmitRecharge: (minutes: Int, price: Int, ref: String, method: String) -> Unit
) {
    var selectedPackage by remember { mutableStateOf<PaymentPackage?>(null) }
    var transactionRef by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf("Telebirr") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "مَقْرَأَةُ البورصة - Wallet & Credit Minutes",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryGreen
        )
        Text(
            text = "Standard Rate: 5 ETB / min | Welcome Bonus: 35 Minutes",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Balance Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SoftGreenSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.AccountBalance, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(36.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Available Balance", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = "${currentUser?.minutesBalance ?: 35} Minutes",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Select Minute Package / باقات الدقائق",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(packages) { pkg ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedPackage == pkg) VeryLightGreenSurface else MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { selectedPackage = pkg }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = pkg.titleEn, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = pkg.titleAr, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(
                            text = "${pkg.priceEtb} ETB",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = PrimaryGreen
                        )
                    }
                }
            }
        }

        if (selectedPackage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Payment Instructions / تعليمات الدفع", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Telebirr: 0911000000 (Khalid Ahmed Mustafa)")
                    Text(text = "Commercial Bank of Ethiopia (CBE): 1000000000000")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = transactionRef,
                        onValueChange = { transactionRef = it },
                        label = { Text("Transaction Reference / رقم الحركة") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            if (transactionRef.isNotBlank()) {
                                onSubmitRecharge(selectedPackage!!.minutes, selectedPackage!!.priceEtb, transactionRef, selectedMethod)
                                showSuccessDialog = true
                                transactionRef = ""
                                selectedPackage = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Submit Recharge Request / إرسال طلب الشحن")
                    }
                }
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Recharge Submitted") },
            text = { Text("Your recharge request has been sent to administrator Khalid Ahmed Mustafa for verification and approval.") },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
