package com.example.loopit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val notificationPurple = Color(0xFF5148C8)
private val notificationBackground = Color(0xFFF8FAFD)
private val notificationInk = Color(0xFF20202A)

@Composable
fun NotificationsScreen(onBackClick: () -> Unit) {
    var reminders by rememberSaveable { mutableStateOf(true) }
    var points by rememberSaveable { mutableStateOf(true) }
    var collectionPoints by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(notificationBackground)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.semantics { contentDescription = "Back" }
            ) {
                Text("←", fontSize = 24.sp, color = notificationInk)
            }
            Text(
                "Notifications",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = notificationInk
            )
        }

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column {
                NotificationSetting(
                    "Recycling reminders", "Nudges to recycle unused devices",
                    reminders, { reminders = it }
                )
                HorizontalDivider(color = Color(0xFFF0F0F4))
                NotificationSetting(
                    "Points & badges", "When you earn points or a badge",
                    points, { points = it }
                )
                HorizontalDivider(color = Color(0xFFF0F0F4))
                NotificationSetting(
                    "New collection points", "When a point opens near you",
                    collectionPoints, { collectionPoints = it }
                )
            }
        }

        Text(
            "Recent",
            modifier = Modifier.padding(top = 20.dp, bottom = 12.dp),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = notificationInk
        )

        repeat(2) {
            RecentPointsCard()
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun NotificationSetting(
    title: String,
    description: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(value = checked, role = Role.Switch, onValueChange = onChange)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f).padding(end = 8.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, color = notificationInk)
            Spacer(Modifier.height(3.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = SwitchDefaults.colors(
                checkedTrackColor = notificationPurple,
                checkedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFE1E1EA),
                uncheckedThumbColor = Color.White,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun RecentPointsCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("☆", color = Color(0xFFBB780C), fontSize = 24.sp)
            Spacer(Modifier.width(12.dp))
            Text(
                text = buildAnnotatedString {
                    append("You earned ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("+120 points")
                    }
                    append(" for recycling a smartphone.")
                },
                style = MaterialTheme.typography.bodyMedium,
                color = notificationInk
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationsPreview() {
    MaterialTheme { NotificationsScreen(onBackClick = {}) }
}