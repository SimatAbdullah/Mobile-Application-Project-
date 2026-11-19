package com.example.loopit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccountSettingsScreen(
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onResetPasswordClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFD))
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.semantics { contentDescription = "Back" }
            ) {
                Text("←", fontSize = 24.sp, color = Color.DarkGray)
            }
            Text(
                text = "Account Settings",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF20202A)
            )
        }

        Spacer(Modifier.height(12.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column {
                AccountSettingsRow(
                    title = "Edit Profile",
                    description = "Update your profile details.",
                    onClick = onEditProfileClick
                )
                HorizontalDivider(color = Color(0xFFF0F0F4))
                AccountSettingsRow(
                    title = "Reset Password",
                    description = "Request a password reset.",
                    onClick = onResetPasswordClick
                )
            }
        }
    }
}

@Composable
private fun AccountSettingsRow(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF20202A)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
        Spacer(Modifier.width(12.dp))
        Text("›", fontSize = 24.sp, color = Color(0xFF5148C8))
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountSettingsPreview() {
    MaterialTheme {
        AccountSettingsScreen(
            onBackClick = {},
            onEditProfileClick = {},
            onResetPasswordClick = {}
        )
    }
}