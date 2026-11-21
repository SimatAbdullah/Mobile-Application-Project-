package com.example.loopit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class RecentEarningItem(
    val id: String,
    val title: String,
    val dateLabel: String,
    val pointsEarned: Int
)

data class ChallengeProgressItem(
    val title: String,
    val currentCount: Int,
    val targetCount: Int
)

data class RewardsUiState(
    val pointsCount: Int = 0,
    val recycledDevicesCount: Int = 0,
    val recentEarnings: List<RecentEarningItem> = emptyList(),
    val challenges: List<ChallengeProgressItem> = listOf(
        ChallengeProgressItem(
            title = "Recycle 3 chargers",
            currentCount = 0,
            targetCount = 3
        )
    )
)

private val PurplePrimary = Color(0xFF6B3CE9)
private val PurpleDark = Color(0xFF5328C7)
private val PurpleLightBg = Color(0xFFF3EFFE)
private val PurpleSoftBar = Color(0xFFE7E2FA)
private val GreenLightBg = Color(0xFFE6F7ED)
private val GreenIcon = Color(0xFF1E9E5A)
private val AmberLightBg = Color(0xFFFFF4E0)
private val AmberIcon = Color(0xFFD98200)
private val TextDark = Color(0xFF1A1A2E)
private val TextMuted = Color(0xFF7A7A8C)
private val BackgroundGray = Color(0xFFFAFAFC)

@Composable
fun RewardsRoute(
    modifier: Modifier = Modifier,
    uiState: RewardsUiState = RewardsUiState(),
    onHomeTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    onRewardsTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {}
) {
    RewardsScreen(
        uiState = uiState,
        onHomeTabClick = onHomeTabClick,
        onMapTabClick = onMapTabClick,
        onRewardsTabClick = onRewardsTabClick,
        onProfileTabClick = onProfileTabClick,
        modifier = modifier
    )
}

@Composable
fun RewardsScreen(
    uiState: RewardsUiState,
    onHomeTabClick: () -> Unit,
    onMapTabClick: () -> Unit,
    onRewardsTabClick: () -> Unit,
    onProfileTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    data class BadgeUiModel(
        val symbol: String,
        val title: String,
        val isEarned: Boolean,
        val activeBg: Color,
        val activeTint: Color
    )

    val badges = listOf(
        BadgeUiModel(
            symbol = "♻",
            title = "First Loop",
            isEarned = uiState.recycledDevicesCount >= 1,
            activeBg = GreenLightBg,
            activeTint = GreenIcon
        ),
        BadgeUiModel(
            symbol = "★",
            title = "5 Devices",
            isEarned = uiState.recycledDevicesCount >= 5,
            activeBg = PurpleLightBg,
            activeTint = PurplePrimary
        ),
        BadgeUiModel(
            symbol = "✦",
            title = "10 Devices",
            isEarned = uiState.recycledDevicesCount >= 10,
            activeBg = PurpleLightBg,
            activeTint = PurplePrimary
        ),
        BadgeUiModel(
            symbol = "🏆",
            title = "25 Devices",
            isEarned = uiState.recycledDevicesCount >= 25,
            activeBg = AmberLightBg,
            activeTint = AmberIcon
        )
    )

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            RewardsBottomBar(
                selectedTab = "Rewards",
                onHomeClick = onHomeTabClick,
                onMapClick = onMapTabClick,
                onRewardsClick = onRewardsTabClick,
                onProfileClick = onProfileTabClick
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Rewards",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            // قسم POINTS
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "POINTS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.6.sp
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(PurplePrimary, PurpleDark)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "★",
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                        Text(
                            text = "Total points",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                        Text(
                            text = "${uiState.pointsCount}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }

            // قسم Recent earnings
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Recent earnings",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                if (uiState.recentEarnings.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Text(
                            text = "No recent point earnings for this account yet.",
                            fontSize = 12.sp,
                            color = TextMuted,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                } else {
                    uiState.recentEarnings.forEach { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
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
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(PurpleLightBg),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "★",
                                            fontSize = 13.sp,
                                            color = PurplePrimary
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                        Text(
                                            text = item.title,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextDark
                                        )
                                        Text(
                                            text = item.dateLabel,
                                            fontSize = 11.sp,
                                            color = TextMuted
                                        )
                                    }
                                }
                                Text(
                                    text = "+${item.pointsEarned}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PurplePrimary
                                )
                            }
                        }
                    }
                }
            }

            // قسم BADGES بأشكال واضحة
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "BADGES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.6.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    badges.forEach { badge ->
                        val circleBg = if (badge.isEarned) badge.activeBg else PurpleSoftBar
                        val symbolColor = if (badge.isEarned) badge.activeTint else TextMuted

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(circleBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = badge.symbol,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = symbolColor
                                )
                            }
                            Text(
                                text = badge.title,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextDark,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = if (badge.isEarned) "Earned" else "Locked",
                                fontSize = 10.sp,
                                color = if (badge.isEarned) PurplePrimary else TextMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // قسم CHALLENGES
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "CHALLENGES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.6.sp
                )

                uiState.challenges.forEach { challenge ->
                    val safeCurrent = if (uiState.recycledDevicesCount == 0) 0 else challenge.currentCount
                    val safeTarget = challenge.targetCount.coerceAtLeast(1)
                    val progress = (safeCurrent.toFloat() / safeTarget.toFloat()).coerceIn(0f, 1f)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = challenge.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextDark
                                )
                                Text(
                                    text = "$safeCurrent/$safeTarget",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextMuted
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(PurpleSoftBar)
                            ) {
                                if (progress > 0f) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(progress)
                                            .height(6.dp)
                                            .clip(RoundedCornerShape(50))
                                            .background(PurplePrimary)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RewardsBottomBar(
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
            RewardsBottomTabItem(
                label = "Home",
                isSelected = selectedTab == "Home",
                onClick = onHomeClick
            )
            RewardsBottomTabItem(
                label = "Map",
                isSelected = selectedTab == "Map",
                onClick = onMapClick
            )
            RewardsBottomTabItem(
                label = "Rewards",
                isSelected = selectedTab == "Rewards",
                onClick = onRewardsClick
            )
            RewardsBottomTabItem(
                label = "Profile",
                isSelected = selectedTab == "Profile",
                onClick = onProfileClick
            )
        }
    }
}

@Composable
private fun RewardsBottomTabItem(
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
fun RewardsScreenPreview() {
    RewardsRoute(
        uiState = RewardsUiState(
            pointsCount = 0,
            recycledDevicesCount = 0
        )
    )
}