
package com.example.loopit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loopit.R

private val LoopitPurple = Color(0xFF514BCD)
private val LoopitBackground = Color(0xFFF8F9FE)
private val LoopitDarkText = Color(0xFF10172A)
private val LoopitBorder = Color(0xFFE0E3ED)

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LoopitBackground)
            .safeDrawingPadding()
            .imePadding()
            .padding(horizontal = 24.dp)
    ) {
        // Main scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Original Loopit logo
            Image(
                painter = painterResource(
                    id = R.drawable.loopit_logo
                ),
                contentDescription = "Loopit Logo",
                modifier = Modifier.size(165.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Give old electronics a second loop.",
                fontSize = 15.sp,
                color = Color(0xFF596176),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Email label
            Text(
                text = "Email",
                fontSize = 15.sp,
                color = LoopitDarkText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email input
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    showError = false
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
                    focusedBorderColor = LoopitPurple,
                    unfocusedBorderColor = LoopitBorder,
                    focusedTextColor = LoopitDarkText,
                    unfocusedTextColor = LoopitDarkText
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Password label
            Text(
                text = "Password",
                fontSize = 15.sp,
                color = LoopitDarkText,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password input
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    showError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                placeholder = {
                    Text(
                        text = "••••••••",
                        color = Color.Gray
                    )
                },
                visualTransformation =
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                shape = RoundedCornerShape(22.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = LoopitPurple,
                    unfocusedBorderColor = LoopitBorder,
                    focusedTextColor = LoopitDarkText,
                    unfocusedTextColor = LoopitDarkText
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Forgot Password link
            Text(
                text = "Forgot password?",
                fontSize = 14.sp,
                color = LoopitPurple,
                modifier = Modifier
                    .align(Alignment.Start)
                    .clickable {
                        onForgotPasswordClick()
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (showError) {
                Text(
                    text = "Please enter your email and password.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            // Login button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        showError = true
                    } else {
                        onLoginClick(email.trim(), password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LoopitPurple
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Text(
                    text = "Log In",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        // Create Account fixed at bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New to Loopit? ",
                fontSize = 14.sp,
                color = Color(0xFF596176)
            )

            Text(
                text = "Create Account",
                fontSize = 14.sp,
                color = LoopitPurple,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    onCreateAccountClick()
                }
            )
        }
    }
}
