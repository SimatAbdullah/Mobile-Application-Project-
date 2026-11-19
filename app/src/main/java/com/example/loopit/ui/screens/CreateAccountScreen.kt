
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AccountPurple = Color(0xFF514BCD)
private val AccountBackground = Color(0xFFF8F9FE)
private val AccountDarkText = Color(0xFF10172A)
private val AccountBorder = Color(0xFFE0E3ED)

@Composable
fun CreateAccountScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onCreateAccountClick: (String, String, String) -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AccountBackground)
            .safeDrawingPadding()
            .imePadding()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Back button
            IconButton(onClick = onBackClick) {
                Text(
                    text = "←",
                    fontSize = 30.sp,
                    color = AccountDarkText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "Create Account",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = AccountDarkText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Start recycling responsibly.",
                fontSize = 16.sp,
                color = Color(0xFF596176)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Full name
            Text(
                text = "Full name",
                fontSize = 15.sp,
                color = AccountDarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            AccountInputField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    errorMessage = ""
                },
                placeholder = "Enter your full name"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Email
            Text(
                text = "Email",
                fontSize = 15.sp,
                color = AccountDarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            AccountInputField(
                value = email,
                onValueChange = {
                    email = it
                    errorMessage = ""
                },
                placeholder = "you@example.com",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Password
            Text(
                text = "Password",
                fontSize = 15.sp,
                color = AccountDarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            AccountInputField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = ""
                },
                placeholder = "At least 8 characters",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Confirm password
            Text(
                text = "Confirm password",
                fontSize = 15.sp,
                color = AccountDarkText
            )

            Spacer(modifier = Modifier.height(8.dp))

            AccountInputField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = ""
                },
                placeholder = "Repeat password",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Validation message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Create Account button
            Button(
                onClick = {
                    errorMessage = when {
                        fullName.isBlank() ||
                                email.isBlank() ||
                                password.isBlank() ||
                                confirmPassword.isBlank() ->
                            "Please fill in all fields."

                        !Patterns.EMAIL_ADDRESS
                            .matcher(email.trim())
                            .matches() ->
                            "Please enter a valid email."

                        password.length < 8 ->
                            "Password must be at least 8 characters."

                        password != confirmPassword ->
                            "Passwords do not match."

                        else -> ""
                    }

                    if (errorMessage.isEmpty()) {
                        onCreateAccountClick(
                            fullName.trim(),
                            email.trim(),
                            password
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccountPurple
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Bottom Login link
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
                fontSize = 14.sp,
                color = Color(0xFF596176)
            )

            Text(
                text = "Log In",
                fontSize = 14.sp,
                color = AccountPurple,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    onLoginClick()
                }
            )
        }
    }
}

@Composable
private fun AccountInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        singleLine = true,
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) {
                KeyboardType.Password
            } else {
                keyboardType
            }
        ),
        shape = RoundedCornerShape(22.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = AccountPurple,
            unfocusedBorderColor = AccountBorder,
            focusedTextColor = AccountDarkText,
            unfocusedTextColor = AccountDarkText
        )
    )
}
