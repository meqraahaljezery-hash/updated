package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RechargeRequest
import com.example.model.RechargeStatus
import com.example.model.User
import com.example.ui.theme.PrimaryGreen
import com.example.ui.theme.SoftGreenSurface

@Composable
fun AdminScreen(
    currentUser: User?,
    rechargeRequests: List<RechargeRequest>,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit
) {
    val isSuperAdmin = currentUser?.email?.lowercase() == "khdahd241@gmail.com" || currentUser?.role == com.example.model.UserRole.SUPER_ADMIN

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Security, contentDescription = null, tint = PrimaryGreen)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "لوحة الإدارة العليا (Super Admin Dashboard)",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )
        }
        Text(
            text = "Authorized Admin: khdahd241@gmail.com",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!isSuperAdmin) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Access Restricted: You are not authorized as Super Administrator.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        } else {
            Text(
                text = "Pending Recharge Requests / طلبات الشحن المعلقة",
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
                items(rechargeRequests) { req ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = req.studentName, fontWeight = FontWeight.Bold)
                                Text(text = "${req.priceEtb} ETB (${req.packageMinutes}m)", color = PrimaryGreen, fontWeight = FontWeight.Bold)
                            }
                            Text(text = "Code: ${req.studentCode} | Ref: ${req.transactionRef}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(text = "Method: ${req.paymentMethod}", fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                if (req.status == RechargeStatus.PENDING) {
                                    IconButton(onClick = { onReject(req.id) }) {
                                        Icon(Icons.Default.Close, contentDescription = "Reject", tint = Color.Red)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = { onApprove(req.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = "Approve")
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Approve")
                                    }
                                } else {
                                    Text(text = "Status: ${req.status}", fontWeight = FontWeight.Bold, color = PrimaryGreen)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
