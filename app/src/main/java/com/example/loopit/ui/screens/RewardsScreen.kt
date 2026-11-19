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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
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

data class RewardsUiState(
    val pointsCount: Int = 0,
    val recycledDevicesCount: Int = 0
)

private val PurplePrimary = Color(0xFF6B3CE9)
private val PurpleDark = Color(0xFF5328C7)
private val PurpleLightBg = Color(0xFFF3EFFE)
private val PurpleSoftBar = Color(0xFFE7E2FA)
private val TextDark = Color(0xFF1A1A2E)
private val TextMuted = Color(0xFF7A7A8C)
private val BackgroundGray = Color(0xFFFAFAFC)

@Composable
fun RewardsRoute(
    uiState: RewardsUiState = RewardsUiState(),
    onHomeTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    onScanTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    RewardsScreen(
        uiState = uiState,
        onHomeTabClick = onHomeTabClick,
        onMapTabClick = onMapTabClick,
        onScanTabClick = onScanTabClick,
        onProfileTabClick = onProfileTabClick,
        modifier = modifier
    )
}

@Composable
fun RewardsScreen(
    uiState: RewardsUiState,
    onHomeTabClick: () -> Unit,
    onMapTabClick: () -> Unit,
    onScanTabClick: () -> Unit,
    onProfileTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val levelStep = 100
    val pointsIntoLevel = uiState.pointsCount % levelStep
    val pointsRemaining = (levelStep - pointsIntoLevel).let { if (it == 0) levelStep else it }
    val progress = (pointsIntoLevel.toFloat() / levelStep.toFloat()).coerceIn(0f, 1f)

    val badges = listOf(
        Triple("1st", "First loop", uiState.recycledDevicesCount >= 1),
        Triple("5", "5 devices", uiState.recycledDevicesCount >= 5),
        Triple("10", "10 devices", uiState.recycledDevicesCount >= 10),
        Triple("BT", "Battery hero", uiState.pointsCount >= 100)
    )

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            LoopitBottomBar(
                selectedTab = "Rewards",
                onHomeClick = onHomeTabClick,
                onMapClick = onMapTabClick,
                onScanClick = onScanTabClick,
                onRewardsClick = {},
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Rewards",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.linearGradient(listOf(PurplePrimary, PurpleDark)))
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Your points",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Text(
                        text = "${uiState.pointsCount}",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.25f))
                    ) {
                        if (progress > 0f) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(Color.White)
                            )
                        }
                    }
                    Text(
                        text = "$pointsRemaining points to the next level",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Badges",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    badges.forEach { (code, label, unlocked) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (unlocked) PurpleLightBg else PurpleSoftBar),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = code,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (unlocked) PurplePrimary else TextMuted
                                )
                            }
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                color = if (unlocked) TextDark else TextMuted,
                                fontWeight = if (unlocked) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "How to earn",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

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
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        EarnRow("Record a drop-off", "+20–80")
                        EarnRow("Complete a checklist", "+10")
                        EarnRow("Identify a device", "+5")
                    }
                }
            }
        }
    }
}

@Composable
private fun EarnRow(action: String, points: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = action, fontSize = 13.sp, color = TextDark)
        Text(text = points, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PurplePrimary)
    }
}

@Preview(showBackground = true)
@Composable
fun RewardsScreenPreview() {
    RewardsScreen(
        uiState = RewardsUiState(pointsCount = 0, recycledDevicesCount = 0),
        onHomeTabClick = {},
        onMapTabClick = {},
        onScanTabClick = {},
        onProfileTabClick = {}
    )
}