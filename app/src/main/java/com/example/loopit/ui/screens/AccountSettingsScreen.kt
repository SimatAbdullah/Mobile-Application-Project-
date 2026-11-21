
package com.example.loopit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val accountPurple = Color(0xFF504DCC)
private val accountBackground = Color(0xFFF8F9FE)
private val accountText = Color(0xFF20202A)
private val accountDivider = Color(0xFFE1E2E9)
private val accountArrow = Color(0xFF626570)

@Composable
fun AccountSettingsScreen(
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(accountBackground)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {
                Text(
                    text = "←",
                    fontSize = 28.sp,
                    color = accountText
                )
            }

            Spacer(Modifier.width(4.dp))

            Text(
                text = "Account Settings",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = accountText
            )
        }

        Spacer(Modifier.height(8.dp))

        // Settings options
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column {
                AccountOptionRow(
                    title = "Edit Profile",
                    onClick = onEditProfileClick
                )

                HorizontalDivider(
                    color = accountDivider
                )

                AccountOptionRow(
                    title = "Change Password",
                    onClick = onChangePasswordClick
                )

                HorizontalDivider(
                    color = accountDivider
                )

                AccountOptionRow(
                    title = "Notifications",
                    onClick = onNotificationsClick
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Log out button
        OutlinedButton(
            onClick = onLogoutClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(
                width = 1.5.dp,
                color = accountPurple
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = accountPurple
            )
        ) {
            LogoutSymbol(
                modifier = Modifier.size(21.dp)
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Log Out",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = accountPurple
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun AccountOptionRow(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 54.dp)
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = accountText
        )

        Text(
            text = "›",
            fontSize = 27.sp,
            color = accountArrow
        )
    }
}

@Composable
private fun LogoutSymbol(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 1.8.dp.toPx()

        // Door
        drawRoundRect(
            color = accountPurple,
            topLeft = Offset(
                w * 0.10f,
                h * 0.15f
            ),
            size = Size(
                w * 0.42f,
                h * 0.70f
            ),
            cornerRadius = CornerRadius(2.dp.toPx()),
            style = Stroke(width = stroke)
        )

        // Arrow pointing out
        drawLine(
            color = accountPurple,
            start = Offset(
                w * 0.36f,
                h * 0.50f
            ),
            end = Offset(
                w * 0.91f,
                h * 0.50f
            ),
            strokeWidth = stroke
        )

        drawLine(
            color = accountPurple,
            start = Offset(
                w * 0.68f,
                h * 0.28f
            ),
            end = Offset(
                w * 0.91f,
                h * 0.50f
            ),
            strokeWidth = stroke
        )

        drawLine(
            color = accountPurple,
            start = Offset(
                w * 0.91f,
                h * 0.50f
            ),
            end = Offset(
                w * 0.68f,
                h * 0.72f
            ),
            strokeWidth = stroke
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Account Settings"
)
@Composable
private fun AccountSettingsPreview() {
    MaterialTheme {
        AccountSettingsScreen(
            onBackClick = {},
            onEditProfileClick = {},
            onChangePasswordClick = {},
            onNotificationsClick = {},
            onLogoutClick = {}
        )
    }
}
