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
    onAccessibilityClick: () -> Unit = {},
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
        onAccessibilityClick = onAccessibilityClick,
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
    onAccessibilityClick: () -> Unit,
    onConfirmLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    val displayName = uiState.fullName.ifBlank { "User" }
    val displayEmail = uiState.email.ifBlank { "No email" }

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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )

            // صورة البروفايل الدائرية
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(PurpleLightBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayName.take(1).uppercase(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary
                )
            }

            // الاسم والإيميل والإحصائيات الحقيقية للحساب
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
                    text = displayEmail,
                    fontSize = 13.sp,
                    color = TextMuted
                )
                Text(
                    text = "${uiState.recycledDevicesCount} recycled devices · ${uiState.pointsCount} points",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = PurplePrimary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            ProfileMenuCard(
                badge = "ST",
                title = "Statistics",
                subtitle = "Track your impact",
                onClick = onStatisticsClick
            )

            ProfileMenuCard(
                badge = "RH",
                title = "Recycling History",
                subtitle = "View previous drop-offs",
                onClick = onRecyclingHistoryClick
            )

            ProfileMenuCard(
                badge = "RW",
                title = "Rewards",
                subtitle = "Points and badges",
                onClick = onRewardsClick
            )

            ProfileMenuCard(
                badge = "AC",
                title = "Accessibility Settings",
                subtitle = "Text size, contrast and more",
                onClick = onAccessibilityClick
            )

            ProfileMenuCard(
                badge = "->",
                title = "Log out",
                subtitle = "Return to sign-in",
                onClick = { showLogoutDialog = true }
            )
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
                        text = "Your progress in this session will remain available when you sign back in.",
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
private fun ProfileMenuCard(
    badge: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PurpleLightBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = badge,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }
            Text(
                text = ">",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        uiState = ProfileUiState(
            fullName = "Preview User",
            email = "user@loopit.app",
            recycledDevicesCount = 0,
            pointsCount = 0
        ),
        onHomeTabClick = {},
        onMapTabClick = {},
        onScanTabClick = {},
        onRewardsTabClick = {},
        onStatisticsClick = {},
        onRecyclingHistoryClick = {},
        onRewardsClick = {},
        onAccessibilityClick = {},
        onConfirmLogoutClick = {}
    )
}