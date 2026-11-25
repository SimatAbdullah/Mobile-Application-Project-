
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.loopit.ui.viewmodel.RewardsUiState
import com.example.loopit.ui.viewmodel.RewardsViewModel
import com.example.loopit.ui.viewmodel.RewardsViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
    userId: String = "",
    onHomeTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    onRewardsTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val factory = remember(context) {
        RewardsViewModelFactory(context)
    }

    val rewardsViewModel: RewardsViewModel = viewModel(
        factory = factory
    )

    val uiState by rewardsViewModel.uiState.collectAsState()

    // Refresh rewards when entering the screen or changing users
    LaunchedEffect(userId) {
        rewardsViewModel.loadRewards(userId)
    }

    RewardsScreen(
        uiState = uiState,
        onRetry = {
            rewardsViewModel.loadRewards(userId)
        },
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
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {}
) {
    data class BadgeUiModel(
        val symbol: String,
        val title: String,
        val isEarned: Boolean,
        val activeBg: Color,
        val activeTint: Color
    )

    val data = uiState.rewardsData

    val dateFormat = remember {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    // Map database badges to the existing visual style
    val badges = data.badges
        .sortedBy { it.requiredDevices }
        .map { badge ->

            val symbol = when (badge.iconName) {
                "first_loop" -> "♻"
                "five_devices" -> "★"
                "ten_devices" -> "✦"
                else -> "🏆"
            }

            BadgeUiModel(
                symbol = symbol,
                title = badge.title,
                isEarned = badge.isEarned,
                activeBg = if (badge.iconName == "first_loop") {
                    GreenLightBg
                } else {
                    PurpleLightBg
                },
                activeTint = if (badge.iconName == "first_loop") {
                    GreenIcon
                } else {
                    PurplePrimary
                }
            )
        }

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

            // Display loading state
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = PurplePrimary
                    )
                }

                return@Column
            }

            // Display error state and retry option
            val error = uiState.errorMessage

            if (error != null) {
                Text(
                    text = error,
                    color = TextMuted,
                    fontSize = 13.sp
                )

                TextButton(onClick = onRetry) {
                    Text(
                        text = "Try again",
                        color = PurplePrimary
                    )
                }

                return@Column
            }

            // Points section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                                colors = listOf(
                                    PurplePrimary,
                                    PurpleDark
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(
                                    Color.White.copy(alpha = 0.2f)
                                ),
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
                            text = "${data.totalPoints}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }

            // Recent earnings section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Recent earnings",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                if (data.recentEarnings.isEmpty()) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
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

                    data.recentEarnings.forEach { item ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 14.dp
                                    ),
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

                                    Spacer(
                                        modifier = Modifier.width(12.dp)
                                    )

                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = item.deviceName,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextDark
                                        )

                                        Text(
                                            text = dateFormat.format(
                                                Date(item.recycledAt)
                                            ),
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

            // Badges section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

                        val circleBg = if (badge.isEarned) {
                            badge.activeBg
                        } else {
                            PurpleSoftBar
                        }

                        val symbolColor = if (badge.isEarned) {
                            badge.activeTint
                        } else {
                            TextMuted
                        }

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
                                text = if (badge.isEarned) {
                                    "Earned"
                                } else {
                                    "Locked"
                                },
                                fontSize = 10.sp,
                                color = if (badge.isEarned) {
                                    PurplePrimary
                                } else {
                                    TextMuted
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Challenges section
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "CHALLENGES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.6.sp
                )

                data.challenges
                    .sortedBy { it.targetCount }
                    .forEach { challenge ->

                        val safeCurrent = challenge.currentProgress
                            .coerceIn(
                                0,
                                challenge.targetCount.coerceAtLeast(1)
                            )

                        val safeTarget = challenge.targetCount
                            .coerceAtLeast(1)

                        val progress = (
                                safeCurrent.toFloat() /
                                        safeTarget.toFloat()
                                ).coerceIn(0f, 1f)

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            )
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

                                // Display challenge bonus points and completion status
                                Text(
                                    text = if (challenge.isCompleted) {
                                        "Completed · +${challenge.bonusPoints} pts"
                                    } else {
                                        "+${challenge.bonusPoints} pts reward"
                                    },
                                    fontSize = 11.sp,
                                    color = if (challenge.isCompleted) {
                                        GreenIcon
                                    } else {
                                        TextMuted
                                    }
                                )

                                // Display challenge progress bar
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

// Bottom navigation bar
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
                .padding(
                    vertical = 10.dp,
                    horizontal = 12.dp
                ),
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

// Individual bottom navigation tab
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
            .padding(
                horizontal = 10.dp,
                vertical = 4.dp
            )
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) {
                PurplePrimary
            } else {
                TextMuted
            },
            fontWeight = if (isSelected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
        )
    }
}

// Preview the rewards screen without accessing the database
@Preview(showBackground = true)
@Composable
fun RewardsScreenPreview() {
    RewardsScreen(
        uiState = RewardsUiState(),
        onHomeTabClick = {},
        onMapTabClick = {},
        onRewardsTabClick = {},
        onProfileTabClick = {}
    )
}
