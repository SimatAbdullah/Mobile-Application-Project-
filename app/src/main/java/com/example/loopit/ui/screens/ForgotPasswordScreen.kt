
package com.example.loopit.ui.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ResetPurple = Color(0xFF514BCD)
private val ResetBackground = Color(0xFFF8F9FE)
private val ResetDark = Color(0xFF10172A)
private val ResetBorder = Color(0xFFE0E3ED)

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onSendResetLink: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ResetBackground)
            .safeDrawingPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "←",
                fontSize = 30.sp,
                color = ResetDark,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .padding(end = 20.dp)
            )

            Text(
                text = "Reset Password",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = ResetDark
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Enter your email and we'll send you a reset link.",
            fontSize = 16.sp,
            color = Color(0xFF596176)
        )

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Email",
            fontSize = 15.sp,
            color = ResetDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            placeholder = {
                Text(
                    text = "you@example.com",
                    color = Color.Gray
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(22.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = ResetPurple,
                unfocusedBorderColor = ResetBorder
            )
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = errorMessage,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                errorMessage = when {
                    email.isBlank() ->
                        "Please enter your email."

                    !Patterns.EMAIL_ADDRESS
                        .matcher(email.trim())
                        .matches() ->
                        "Please enter a valid email."

                    else -> ""
                }

                if (errorMessage.isEmpty()) {
                    onSendResetLink(email.trim())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ResetPurple
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp
            )
        ) {
            Text(
                text = "Send Reset Link",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}
