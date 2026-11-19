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

data class MonthlyDeviceStat(
    val month: String,
    val count: Int,
    val isHighlighted: Boolean = false
)

data class CategoryStat(
    val badge: String,
    val name: String,
    val count: Int
)

data class YourImpactUiState(
    val periodLabel: String = "6 months",
    val devicesRecycled: Int = 0,
    val pointsEarned: Int = 0,
    val monthlyStats: List<MonthlyDeviceStat> = listOf(
        MonthlyDeviceStat("May", 0),
        MonthlyDeviceStat("Jun", 0),
        MonthlyDeviceStat("Jul", 0),
        MonthlyDeviceStat("Aug", 0),
        MonthlyDeviceStat("Sep", 0),
        MonthlyDeviceStat("Oct", 0, isHighlighted = true)
    ),
    val categoryStats: List<CategoryStat> = listOf(
        CategoryStat("PH", "Phones", 0),
        CategoryStat("LP", "Laptops", 0),
        CategoryStat("BT", "Batteries", 0)
    )
)

private val PurplePrimary = Color(0xFF6B3CE9)
private val PurpleDark = Color(0xFF5328C7)
private val PurpleLightBg = Color(0xFFF3EFFE)
private val PurpleSoftBar = Color(0xFFE7E2FA)
private val TextDark = Color(0xFF1A1A2E)
private val TextMuted = Color(0xFF7A7A8C)
private val BackgroundGray = Color(0xFFFAFAFC)

@Composable
fun YourImpactRoute(
    uiState: YourImpactUiState = YourImpactUiState(),
    onBackClick: () -> Unit = {},
    onHomeTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    onScanTabClick: () -> Unit = {},
    onRewardsTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    YourImpactScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onHomeTabClick = onHomeTabClick,
        onMapTabClick = onMapTabClick,
        onScanTabClick = onScanTabClick,
        onRewardsTabClick = onRewardsTabClick,
        onProfileTabClick = onProfileTabClick,
        modifier = modifier
    )
}

@Composable
fun YourImpactScreen(
    uiState: YourImpactUiState,
    onBackClick: () -> Unit,
    onHomeTabClick: () -> Unit,
    onMapTabClick: () -> Unit,
    onScanTabClick: () -> Unit,
    onRewardsTabClick: () -> Unit,
    onProfileTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val maxMonthCount = (uiState.monthlyStats.maxOfOrNull { it.count } ?: 1).coerceAtLeast(1)
    val totalCategoryCount = uiState.categoryStats.sumOf { it.count }.coerceAtLeast(1)

    Scaffold(
        containerColor = BackgroundGray,
        bottomBar = {
            LoopitBottomBar(
                selectedTab = "Profile",
                onHomeClick = onHomeTabClick,
                onMapClick = onMapTabClick,
                onScanClick = onScanTabClick,
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
            // الشريط العلوي
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "<",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    }
                    Text(
                        text = "Statistics",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFEDEDF2))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = uiState.periodLabel,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDark
                    )
                }
            }

            // بطاقتا الإحصائيات السريعة
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                StatSummaryCard(
                    title = "Devices recycled",
                    value = "${uiState.devicesRecycled}",
                    valueColor = TextDark,
                    modifier = Modifier.weight(1f)
                )
                StatSummaryCard(
                    title = "Points earned",
                    value = "${uiState.pointsEarned}",
                    valueColor = PurplePrimary,
                    modifier = Modifier.weight(1f)
                )
            }

            // بطاقة الرسم البياني الشهري
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Devices per month",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(125.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        uiState.monthlyStats.forEach { stat ->
                            val fraction = if (uiState.devicesRecycled == 0 || stat.count == 0) {
                                0.08f
                            } else {
                                (stat.count.toFloat() / maxMonthCount.toFloat()).coerceIn(0.15f, 1f)
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height((95 * fraction).dp)
                                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                        .background(
                                            if (stat.isHighlighted && stat.count > 0) {
                                                Brush.verticalGradient(listOf(PurplePrimary, PurpleDark))
                                            } else {
                                                Brush.verticalGradient(listOf(PurpleSoftBar, PurpleSoftBar))
                                            }
                                        )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stat.month,
                                    fontSize = 11.sp,
                                    color = if (stat.isHighlighted) TextDark else TextMuted,
                                    fontWeight = if (stat.isHighlighted) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            // بطاقة التصنيف حسب الفئة
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "By category",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    uiState.categoryStats.forEach { category ->
                        val progress = if (uiState.devicesRecycled == 0) {
                            0f
                        } else {
                            (category.count.toFloat() / totalCategoryCount.toFloat()).coerceIn(0f, 1f)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PurpleLightBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = category.badge,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PurplePrimary
                                )
                            }

                            Text(
                                text = category.name,
                                fontSize = 13.sp,
                                color = TextDark,
                                modifier = Modifier.width(68.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .weight(1f)
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

                            Text(
                                text = "${category.count}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextMuted,
                                textAlign = TextAlign.End,
                                modifier = Modifier.width(20.dp)
                            )
                        }
                    }
                }
            }

            Text(
                text = "Based on recycling you've recorded.",
                fontSize = 12.sp,
                color = TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun StatSummaryCard(
    title: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
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
            Text(
                text = title,
                fontSize = 12.sp,
                color = TextMuted
            )
            Text(
                text = value,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = valueColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YourImpactScreenPreview() {
    YourImpactScreen(
        uiState = YourImpactUiState(devicesRecycled = 0, pointsEarned = 0),
        onBackClick = {},
        onHomeTabClick = {},
        onMapTabClick = {},
        onScanTabClick = {},
        onRewardsTabClick = {},
        onProfileTabClick = {}
    )
}