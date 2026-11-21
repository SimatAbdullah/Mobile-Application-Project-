
package com.example.loopit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ProfileUiState(
    val fullName: String = "",
    val email: String = "",
    val recycledDevicesCount: Int = 0,
    val pointsCount: Int = 0
)

private val PurplePrimary = Color(0xFF6B3CE9)
private val PurpleLightBg = Color(0xFFF3EFFE)
private val TextDark = Color(0xFF1A1A2E)
private val TextMuted = Color(0xFF7A7A8C)
private val BackgroundGray = Color(0xFFFAFAFC)

@Composable
fun ProfileRoute(
    uiState: ProfileUiState = ProfileUiState(),
    onHomeTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    onScanTabClick: () -> Unit = {},
    onRewardsTabClick: () -> Unit = {},
    onStatisticsClick: () -> Unit = {},
    onRecyclingHistoryClick: () -> Unit = {},
    onRewardsClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onAccountSettingsClick: () -> Unit = {},
    onConfirmLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ProfileScreen(
        uiState = uiState,
        onHomeTabClick = onHomeTabClick,
        onMapTabClick = onMapTabClick,
        onScanTabClick = onScanTabClick,
        onRewardsTabClick = onRewardsTabClick,
        onStatisticsClick = onStatisticsClick,
        onRecyclingHistoryClick = onRecyclingHistoryClick,
        onRewardsClick = onRewardsClick,
        onEditProfileClick = onEditProfileClick,
        onNotificationsClick = onNotificationsClick,
        onAccountSettingsClick = onAccountSettingsClick,
        onConfirmLogoutClick = onConfirmLogoutClick,
        modifier = modifier
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onHomeTabClick: () -> Unit,
    onMapTabClick: () -> Unit,
    onScanTabClick: () -> Unit,
    onRewardsTabClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onRecyclingHistoryClick: () -> Unit,
    onRewardsClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onAccountSettingsClick: () -> Unit,
    onConfirmLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    val displayName = uiState.fullName.ifBlank { "User" }
    val displayEmail = uiState.email.ifBlank { "No email" }

    val initials = displayName
        .trim()
        .split(Regex("\\s+"))
        .filter { it.isNotBlank() }
        .let { parts ->
            when {
                parts.isEmpty() -> "U"
                parts.size == 1 -> parts[0].take(2).uppercase()
                else -> (
                        parts.first().take(1) +
                                parts.last().take(1)
                        ).uppercase()
            }
        }

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            LoopitBottomBar(
                selectedTab = "Profile",
                onHomeClick = onHomeTabClick,
                onMapClick = onMapTabClick,
                onScanClick = onScanTabClick,
                onRewardsClick = onRewardsTabClick,
                onProfileClick = {}
            )
        },
        modifier = modifier
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Page title
            Text(
                text = "Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Profile avatar
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(PurpleLightBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary
                )
            }

            // User information
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = displayName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Text(
                    text = "✉  $displayEmail",
                    fontSize = 13.sp,
                    color = TextMuted
                )
            }

            // Statistics cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileStatCard(
                    value = uiState.recycledDevicesCount.toString(),
                    label = "Devices recycled",
                    modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    value = uiState.pointsCount.toString(),
                    label = "Points",
                    modifier = Modifier.weight(1f)
                )
            }

            // Profile options matching the approved design
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Column {

                    ProfileMenuItem(
                        symbol = "◷",
                        title = "Recycling History",
                        onClick = onRecyclingHistoryClick
                    )

                    HorizontalDivider(
                        color = Color(0xFFEDEDF2)
                    )

                    ProfileMenuItem(
                        symbol = "▥",
                        title = "Your Impact",
                        onClick = onStatisticsClick
                    )

                    HorizontalDivider(
                        color = Color(0xFFEDEDF2)
                    )

                    ProfileMenuItem(
                        symbol = "★",
                        title = "Rewards",
                        onClick = onRewardsClick
                    )

                    HorizontalDivider(
                        color = Color(0xFFEDEDF2)
                    )

                    // Dalia's existing screen
                    ProfileMenuItem(
                        symbol = "✎",
                        title = "Edit Profile",
                        onClick = onEditProfileClick
                    )

                    HorizontalDivider(
                        color = Color(0xFFEDEDF2)
                    )

                    // Navigation will be connected later
                    ProfileMenuItem(
                        symbol = "♧",
                        title = "Notifications",
                        onClick = onNotificationsClick
                    )

                    HorizontalDivider(
                        color = Color(0xFFEDEDF2)
                    )

                    // Navigation will be connected later
                    ProfileMenuItem(
                        symbol = "⚙",
                        title = "Account Settings",
                        onClick = onAccountSettingsClick
                    )
                }
            }

            // Logout button
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showLogoutDialog = true
                    },
                shape = RoundedCornerShape(50.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    PurplePrimary
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "⇥  Log Out",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = PurplePrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = {
                    showLogoutDialog = false
                },
                containerColor = Color.White,
                title = {
                    Text(
                        text = "Log out?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to log out?",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showLogoutDialog = false
                            onConfirmLogoutClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurplePrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(
                            horizontal = 20.dp,
                            vertical = 8.dp
                        )
                    ) {
                        Text("Log out")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = TextMuted
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileStatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(84.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = label,
                fontSize = 12.sp,
                color = TextMuted
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    symbol: String,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = symbol,
            fontSize = 21.sp,
            color = PurplePrimary,
            modifier = Modifier.width(34.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "›",
            fontSize = 24.sp,
            color = TextMuted
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileRoute(
        uiState = ProfileUiState(
            fullName = "Dalia Ahmed",
            email = "user@example.com",
            recycledDevicesCount = 4,
            pointsCount = 390
        )
    )
}
