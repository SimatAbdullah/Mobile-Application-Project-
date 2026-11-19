package com.example.loopit.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HomeUiState(
    val userName: String = "",
    val recycledDevicesCount: Int = 0,
    val pointsEarned: Int = 0
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
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState(),
    onNotificationsClick: () -> Unit = {},
    onIdentifyDeviceClick: () -> Unit = {},
    onFindCollectionPointsClick: () -> Unit = {},
    onViewStatisticsClick: () -> Unit = {},
    onEnvironmentalImpactClick: () -> Unit = {},
    onHomeTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    onRewardsTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {}
) {
    HomeScreen(
        uiState = uiState,
        onNotificationsClick = onNotificationsClick,
        onIdentifyDeviceClick = onIdentifyDeviceClick,
        onFindCollectionPointsClick = onFindCollectionPointsClick,
        onViewStatisticsClick = onViewStatisticsClick,
        onEnvironmentalImpactClick = onEnvironmentalImpactClick,
        onHomeTabClick = onHomeTabClick,
        onMapTabClick = onMapTabClick,
        onRewardsTabClick = onRewardsTabClick,
        onProfileTabClick = onProfileTabClick,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onNotificationsClick: () -> Unit,
    onIdentifyDeviceClick: () -> Unit,
    onFindCollectionPointsClick: () -> Unit,
    onViewStatisticsClick: () -> Unit,
    onEnvironmentalImpactClick: () -> Unit,
    onHomeTabClick: () -> Unit,
    onMapTabClick: () -> Unit,
    onRewardsTabClick: () -> Unit,
    onProfileTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val greetingText = if (uiState.userName.isBlank()) "Hi" else "Hi, ${uiState.userName}"

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            LoopitBottomBar(
                selectedTab = "Home",
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(PurpleLightBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "∞",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PurplePrimary
                        )
                    }
                    Text(
                        text = "Loopit",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onNotificationsClick() },
                    contentAlignment = Alignment.Center
                ) {
                    BellShape(color = TextDark, modifier = Modifier.size(18.dp))
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = greetingText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "Every device recycled keeps resources in the loop.",
                    fontSize = 13.sp,
                    color = TextMuted
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(PurplePrimary, PurpleDark)
                        )
                    )
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CameraShape(color = Color.White, modifier = Modifier.size(20.dp))
                    }

                    Text(
                        text = "Identify a Device",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Scan or select your electronics to get safe recycling guidance.",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = onIdentifyDeviceClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = PurplePrimary
                        ),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "Get started  →",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFindCollectionPointsClick() },
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
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(PurpleLightBg),
                            contentAlignment = Alignment.Center
                        ) {
                            PinShape(color = PurplePrimary, modifier = Modifier.size(18.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "Find Collection Points",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "E-waste drop-offs near you",
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your Progress",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Text(
                        text = "View Statistics",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PurplePrimary,
                        modifier = Modifier
                            .clickable { onViewStatisticsClick() }
                            .padding(vertical = 2.dp)
                    )
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
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(GreenLightBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "♻",
                                    fontSize = 13.sp,
                                    color = GreenIcon
                                )
                            }
                            Text(
                                text = uiState.recycledDevicesCount.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextDark
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
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
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
                            Text(
                                text = uiState.pointsEarned.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextDark
                            )
                            Text(
                                text = "Points earned",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEnvironmentalImpactClick() },
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
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(GreenLightBg),
                            contentAlignment = Alignment.Center
                        ) {
                            LeafShape(color = GreenIcon, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "See your environmental impact",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextDark
                        )
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
    }
}

@Composable
private fun BellShape(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.14f)
            quadraticTo(w * 0.22f, h * 0.22f, w * 0.22f, h * 0.52f)
            lineTo(w * 0.14f, h * 0.72f)
            lineTo(w * 0.86f, h * 0.72f)
            lineTo(w * 0.78f, h * 0.52f)
            quadraticTo(w * 0.78f, h * 0.22f, w * 0.5f, h * 0.14f)
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 1.8.dp.toPx(), join = StrokeJoin.Round)
        )
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.38f, h * 0.72f),
            size = Size(w * 0.24f, h * 0.16f),
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun CameraShape(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.1f, h * 0.26f),
            size = Size(w * 0.8f, h * 0.54f),
            cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
            style = Stroke(width = 1.8.dp.toPx())
        )
        drawCircle(
            color = color,
            radius = w * 0.16f,
            center = Offset(w * 0.5f, h * 0.53f),
            style = Stroke(width = 1.8.dp.toPx())
        )
    }
}

@Composable
private fun PinShape(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.88f)
            quadraticTo(w * 0.18f, h * 0.55f, w * 0.22f, h * 0.34f)
            arcTo(
                rect = Rect(w * 0.22f, h * 0.12f, w * 0.78f, h * 0.56f),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            quadraticTo(w * 0.82f, h * 0.55f, w * 0.5f, h * 0.88f)
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 1.8.dp.toPx(), join = StrokeJoin.Round)
        )
        drawCircle(
            color = color,
            radius = w * 0.11f,
            center = Offset(w * 0.5f, h * 0.34f),
            style = Stroke(width = 1.6.dp.toPx())
        )
    }
}

@Composable
private fun LeafShape(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.2f, h * 0.8f)
            quadraticTo(w * 0.18f, h * 0.22f, w * 0.82f, h * 0.2f)
            quadraticTo(w * 0.78f, h * 0.82f, w * 0.2f, h * 0.8f)
            close()
        }
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 1.8.dp.toPx(), join = StrokeJoin.Round)
        )
        drawLine(
            color = color,
            start = Offset(w * 0.24f, h * 0.76f),
            end = Offset(w * 0.68f, h * 0.34f),
            strokeWidth = 1.6.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun LoopitBottomBar(
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
        verticalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        when (label) {
            "Home" -> HomeTabShape(color = color, modifier = Modifier.size(18.dp))
            "Map" -> PinShape(color = color, modifier = Modifier.size(18.dp))
            "Rewards" -> RewardTabShape(color = color, modifier = Modifier.size(18.dp))
            else -> ProfileTabShape(color = color, modifier = Modifier.size(18.dp))
        }
        Text(
            text = label,
            fontSize = 12.sp,
            color = color,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun HomeTabShape(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val roof = Path().apply {
            moveTo(w * 0.15f, h * 0.48f)
            lineTo(w * 0.5f, h * 0.18f)
            lineTo(w * 0.85f, h * 0.48f)
        }
        drawPath(
            path = roof,
            color = color,
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        drawRect(
            color = color,
            topLeft = Offset(w * 0.25f, h * 0.48f),
            size = Size(w * 0.5f, h * 0.36f),
            style = Stroke(width = 1.8.dp.toPx())
        )
    }
}

@Composable
private fun RewardTabShape(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        drawCircle(
            color = color,
            radius = w * 0.28f,
            center = Offset(w * 0.5f, h * 0.38f),
            style = Stroke(width = 1.8.dp.toPx())
        )
        val ribbon = Path().apply {
            moveTo(w * 0.36f, h * 0.62f)
            lineTo(w * 0.28f, h * 0.88f)
            moveTo(w * 0.64f, h * 0.62f)
            lineTo(w * 0.72f, h * 0.88f)
        }
        drawPath(
            path = ribbon,
            color = color,
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun ProfileTabShape(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        drawCircle(
            color = color,
            radius = w * 0.18f,
            center = Offset(w * 0.5f, h * 0.32f),
            style = Stroke(width = 1.8.dp.toPx())
        )
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(w * 0.22f, h * 0.54f),
            size = Size(w * 0.56f, h * 0.32f),
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        uiState = HomeUiState(
            userName = "User",
            recycledDevicesCount = 0,
            pointsEarned = 0
        ),
        onNotificationsClick = {},
        onIdentifyDeviceClick = {},
        onFindCollectionPointsClick = {},
        onViewStatisticsClick = {},
        onEnvironmentalImpactClick = {},
        onHomeTabClick = {},
        onMapTabClick = {},
        onRewardsTabClick = {},
        onProfileTabClick = {}
    )
}