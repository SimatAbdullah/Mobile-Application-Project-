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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HomeUiState(
    val userName: String = "",
    val recycledDevicesCount: Int = 0,
    val recyclingTip: String = "Remove batteries where possible and keep them separate — they often need a different disposal route."
)

private val PurplePrimary = Color(0xFF6B3CE9)
private val PurpleDark = Color(0xFF5328C7)
private val PurpleLightBg = Color(0xFFF3EFFE)
private val GreenLightBg = Color(0xFFE6F7ED)
private val GreenIcon = Color(0xFF1E9E5A)
private val TextDark = Color(0xFF1A1A2E)
private val TextMuted = Color(0xFF7A7A8C)
private val BackgroundGray = Color(0xFFFAFAFC)

@Composable
fun HomeRoute(
    uiState: HomeUiState = HomeUiState(),
    onNotificationsClick: () -> Unit = {},
    onRecordRecyclingClick: () -> Unit = {},
    onIdentifyClick: () -> Unit = {},
    onPointsClick: () -> Unit = {},
    onRewardsClick: () -> Unit = {},
    onChecklistClick: () -> Unit = {},
    onViewStatisticsClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    onScanTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    HomeScreen(
        uiState = uiState,
        onNotificationsClick = onNotificationsClick,
        onRecordRecyclingClick = onRecordRecyclingClick,
        onIdentifyClick = onIdentifyClick,
        onPointsClick = onPointsClick,
        onRewardsClick = onRewardsClick,
        onChecklistClick = onChecklistClick,
        onViewStatisticsClick = onViewStatisticsClick,
        onMapTabClick = onMapTabClick,
        onScanTabClick = onScanTabClick,
        onProfileTabClick = onProfileTabClick,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onNotificationsClick: () -> Unit,
    onRecordRecyclingClick: () -> Unit,
    onIdentifyClick: () -> Unit,
    onPointsClick: () -> Unit,
    onRewardsClick: () -> Unit,
    onChecklistClick: () -> Unit,
    onViewStatisticsClick: () -> Unit,
    onMapTabClick: () -> Unit,
    onScanTabClick: () -> Unit,
    onProfileTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            LoopitBottomBar(
                selectedTab = "Home",
                onHomeClick = {},
                onMapClick = onMapTabClick,
                onScanClick = onScanTabClick,
                onRewardsClick = onRewardsClick,
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Good morning",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                    Text(
                        text = if (uiState.userName.isBlank()) "Hi" else "Hi, ${uiState.userName}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onNotificationsClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }
            }

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
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Your recycled devices",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Text(
                        text = "${uiState.recycledDevicesCount}",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = "Keep the loop going — record your next drop-off.",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = onRecordRecyclingClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = PurplePrimary
                        ),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "Record recycling",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                QuickActionItem(
                    badgeText = "ID",
                    label = "Identify",
                    onClick = onIdentifyClick
                )
                QuickActionItem(
                    badgeText = "PT",
                    label = "Points",
                    onClick = onPointsClick
                )
                QuickActionItem(
                    badgeText = "+",
                    label = "Record",
                    onClick = onRecordRecyclingClick
                )
                QuickActionItem(
                    badgeText = "★",
                    label = "Rewards",
                    onClick = onRewardsClick
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onChecklistClick() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(GreenLightBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = GreenIcon
                            )
                        }
                        Column {
                            Text(
                                text = "Before you drop off",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = TextDark
                            )
                            Text(
                                text = "Back up and wipe your personal data.",
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

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Recycling tip",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PurpleLightBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "i",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurplePrimary
                            )
                        }
                        Text(
                            text = uiState.recyclingTip,
                            fontSize = 12.sp,
                            color = TextMuted,
                            lineHeight = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "View statistics →",
                    color = PurplePrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { onViewStatisticsClick() }
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun QuickActionItem(
    badgeText: String,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(PurpleLightBg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = badgeText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PurplePrimary
            )
        }
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
    }
}

@Composable
fun LoopitBottomBar(
    selectedTab: String,
    onHomeClick: () -> Unit,
    onMapClick: () -> Unit,
    onScanClick: () -> Unit,
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
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                label = "Home",
                isSelected = selectedTab == "Home",
                onClick = onHomeClick
            )
            BottomNavItem(
                label = "Map",
                isSelected = selectedTab == "Map",
                onClick = onMapClick
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .offset(y = (-8).dp)
                    .clickable { onScanClick() }
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .shadow(4.dp, CircleShape)
                        .clip(CircleShape)
                        .background(PurplePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SCAN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Scan",
                    fontSize = 11.sp,
                    color = if (selectedTab == "Scan") PurplePrimary else TextMuted,
                    fontWeight = FontWeight.Medium
                )
            }

            BottomNavItem(
                label = "Rewards",
                isSelected = selectedTab == "Rewards",
                onClick = onRewardsClick
            )
            BottomNavItem(
                label = "Profile",
                isSelected = selectedTab == "Profile",
                onClick = onProfileClick
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) PurplePrimary else TextMuted
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = color,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState(userName = "Preview", recycledDevicesCount = 0),
        onNotificationsClick = {},
        onRecordRecyclingClick = {},
        onIdentifyClick = {},
        onPointsClick = {},
        onRewardsClick = {},
        onChecklistClick = {},
        onViewStatisticsClick = {},
        onMapTabClick = {},
        onScanTabClick = {},
        onProfileTabClick = {}
    )
}