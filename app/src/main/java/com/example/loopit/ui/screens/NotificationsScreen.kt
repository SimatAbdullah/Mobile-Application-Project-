
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val notificationsPurple = Color(0xFF504DCC)
private val notificationsBackground = Color(0xFFF8FAFD)
private val notificationsText = Color(0xFF20202A)
private val notificationsMuted = Color(0xFF747584)
private val notificationsGold = Color(0xFFDF911A)

@Composable
fun NotificationsScreen(
    onBackClick: () -> Unit
) {
    var reminders by rememberSaveable {
        mutableStateOf(true)
    }

    var points by rememberSaveable {
        mutableStateOf(true)
    }

    var collectionPoints by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(notificationsBackground)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Text(
                    text = "←",
                    fontSize = 26.sp,
                    color = notificationsText
                )
            }

            Text(
                text = "Notifications",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = notificationsText
            )
        }

        Spacer(Modifier.height(20.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column {
                NotificationSetting(
                    title = "Recycling reminders",
                    description = "Nudges to recycle unused devices",
                    checked = reminders,
                    onCheckedChange = {
                        reminders = it
                    }
                )

                HorizontalDivider(
                    color = Color(0xFFF0F0F4),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                NotificationSetting(
                    title = "Points & badges",
                    description = "When you earn points or a badge",
                    checked = points,
                    onCheckedChange = {
                        points = it
                    }
                )

                HorizontalDivider(
                    color = Color(0xFFF0F0F4),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                NotificationSetting(
                    title = "New collection points",
                    description = "When a point opens near you",
                    checked = collectionPoints,
                    onCheckedChange = {
                        collectionPoints = it
                    }
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        Text(
            text = "Recent",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = notificationsText
        )

        Spacer(Modifier.height(12.dp))

        RecentPointsCard(
            pointsEarned = 120,
            device = "smartphone"
        )

        Spacer(Modifier.height(10.dp))

        RecentPointsCard(
            pointsEarned = 40,
            device = "charger"
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun NotificationSetting(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = checked,
                role = Role.Switch,
                onValueChange = onCheckedChange
            )
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = notificationsText
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = notificationsMuted
            )
        }

        Spacer(Modifier.width(12.dp))

        Switch(
            checked = checked,
            onCheckedChange = null,
            colors = SwitchDefaults.colors(
                checkedTrackColor = notificationsPurple,
                checkedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFE4E4EA),
                uncheckedThumbColor = Color.White,
                uncheckedBorderColor = Color(0xFFBFC0C9)
            )
        )
    }
}

@Composable
private fun RecentPointsCard(
    pointsEarned: Int,
    device: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        Color(0xFFFFF6E7),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "☆",
                    fontSize = 25.sp,
                    color = notificationsGold
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = buildAnnotatedString {
                    append("You earned ")

                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = notificationsText
                        )
                    ) {
                        append("+$pointsEarned points")
                    }

                    append(" for recycling a $device.")
                },
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = notificationsText,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Notifications"
)
@Composable
private fun NotificationsPreview() {
    MaterialTheme {
        NotificationsScreen(
            onBackClick = {}
        )
    }
}
