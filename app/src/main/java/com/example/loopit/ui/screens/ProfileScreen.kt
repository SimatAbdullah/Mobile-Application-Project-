package com.example.loopit.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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

private fun buildInitials(fullName: String): String {
    val parts = fullName.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }
    return when {
        parts.isEmpty() -> "U"
        parts.size == 1 -> parts[0].take(2).uppercase()
        else -> "${parts[0].take(1)}${parts[1].take(1)}".uppercase()
    }
}

@Composable
fun ProfileRoute(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState = ProfileUiState(),
    onHomeTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    onRewardsTabClick: () -> Unit = {},
    onRecyclingHistoryClick: () -> Unit = {},
    onYourImpactClick: () -> Unit = {},
    onRewardsClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onAccountSettingsClick: () -> Unit = {},
    onConfirmLogoutClick: () -> Unit = {}
) {
    ProfileScreen(
        uiState = uiState,
        onHomeTabClick = onHomeTabClick,
        onMapTabClick = onMapTabClick,
        onRewardsTabClick = onRewardsTabClick,
        onRecyclingHistoryClick = onRecyclingHistoryClick,
        onYourImpactClick = onYourImpactClick,
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
    onRewardsTabClick: () -> Unit,
    onRecyclingHistoryClick: () -> Unit,
    onYourImpactClick: () -> Unit,
    onRewardsClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onAccountSettingsClick: () -> Unit,
    onConfirmLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    val displayName = uiState.fullName.ifBlank { "User" }
    val displayEmail = uiState.email.ifBlank { "" }
    val initials = buildInitials(displayName)

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            ProfileBottomBar(
                selectedTab = "Profile",
                onHomeClick = onHomeTabClick,
                onMapClick = onMapTabClick,
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
            )

            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(PurpleLightBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = initials,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = displayName,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                if (displayEmail.isNotBlank()) {
                    Text(
                        text = displayEmail,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "${uiState.recycledDevicesCount}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PurplePrimary
                        )
                        Text(
                            text = "Devices recycled",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "${uiState.pointsCount}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PurplePrimary
                        )
                        Text(
                            text = "Points",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            ProfileActionRow(
                symbol = "↺",
                title = "Recycling History",
                onClick = onRecyclingHistoryClick
            )

            ProfileActionRow(
                symbol = "▤",
                title = "Your Impact",
                onClick = onYourImpactClick
            )

            ProfileActionRow(
                symbol = "★",
                title = "Rewards",
                onClick = onRewardsClick
            )

            ProfileActionRow(
                symbol = "✎",
                title = "Edit Profile",
                onClick = onEditProfileClick
            )

            ProfileActionRow(
                symbol = "🔔",
                title = "Notifications",
                onClick = onNotificationsClick
            )

            ProfileActionRow(
                symbol = "⚙",
                title = "Account Settings",
                onClick = onAccountSettingsClick
            )

            Spacer(modifier = Modifier.height(2.dp))

            OutlinedButton(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, PurplePrimary.copy(alpha = 0.4f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = PurplePrimary
                )
            ) {
                Text(
                    text = "→ Log Out",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
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
                        text = "Your session data for this account will remain available when you sign back in.",
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
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Log out",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text(
                            text = "Cancel",
                            color = TextMuted,
                            fontSize = 14.sp
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileActionRow(
    symbol: String,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(PurpleLightBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = symbol,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextDark
                )
            }

            Text(
                text = ">",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted
            )
        }
    }
}

@Composable
private fun ProfileBottomBar(
    selectedTab: String,
    onHomeClick: () -> Unit,
    onMapClick: () -> Unit,
    onRewardsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileBottomTabItem(
                label = "Home",
                isSelected = selectedTab == "Home",
                onClick = onHomeClick
            )
            ProfileBottomTabItem(
                label = "Map",
                isSelected = selectedTab == "Map",
                onClick = onMapClick
            )
            ProfileBottomTabItem(
                label = "Rewards",
                isSelected = selectedTab == "Rewards",
                onClick = onRewardsClick
            )
            ProfileBottomTabItem(
                label = "Profile",
                isSelected = selectedTab == "Profile",
                onClick = onProfileClick
            )
        }
    }
}

@Composable
private fun ProfileBottomTabItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) PurplePrimary else TextMuted,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileRoute(
        uiState = ProfileUiState(
            fullName = "User",
            email = "",
            recycledDevicesCount = 0,
            pointsCount = 0
        )
    )
}