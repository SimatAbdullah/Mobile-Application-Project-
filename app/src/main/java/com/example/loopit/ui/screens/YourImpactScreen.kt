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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MonthlyActivityStat(
    val month: String,
    val count: Int
)

data class RecycledCategoryStat(
    val badge: String,
    val name: String,
    val count: Int
)

data class YourImpactUiState(
    val devicesRecycled: Int = 0,
    val pointsEarned: Int = 0,
    val monthlyActivity: List<MonthlyActivityStat> = listOf(
        MonthlyActivityStat("May", 0),
        MonthlyActivityStat("Jun", 0),
        MonthlyActivityStat("Jul", 0),
        MonthlyActivityStat("Aug", 0),
        MonthlyActivityStat("Sep", 0),
        MonthlyActivityStat("Oct", 0)
    ),
    val recycledCategories: List<RecycledCategoryStat> = listOf(
        RecycledCategoryStat("SP", "Smartphone", 0),
        RecycledCategoryStat("CH", "Charger", 0),
        RecycledCategoryStat("LP", "Laptop", 0),
        RecycledCategoryStat("AC", "Accessories", 0)
    )
)

private val PurplePrimary = Color(0xFF6B3CE9)
private val PurpleLightBg = Color(0xFFF3EFFE)
private val PurpleSoftBar = Color(0xFFE7E2FA)
private val TextDark = Color(0xFF1A1A2E)
private val TextMuted = Color(0xFF7A7A8C)
private val BackgroundGray = Color(0xFFFAFAFC)

@Composable
fun YourImpactRoute(
    uiState: YourImpactUiState = YourImpactUiState(),
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    YourImpactScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun YourImpactScreen(
    uiState: YourImpactUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val maxMonthCount = (uiState.monthlyActivity.maxOfOrNull { it.count } ?: 1).coerceAtLeast(1)
    val totalCategoryCount = uiState.recycledCategories.sumOf { it.count }.coerceAtLeast(1)

    Scaffold(
        containerColor = BackgroundGray,
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
            // الشريط العلوي
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "←",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier
                        .clickable { onBackClick() }
                        .padding(4.dp)
                )
                Text(
                    text = "Your Impact",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            // بطاقتا الإحصائيات السريعة
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ImpactMetricCard(
                    value = "${uiState.devicesRecycled}",
                    label = "Devices recycled",
                    modifier = Modifier.weight(1f)
                )
                ImpactMetricCard(
                    value = "${uiState.pointsEarned}",
                    label = "Points earned",
                    modifier = Modifier.weight(1f)
                )
            }

            // بطاقة Recycling Activity
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(PurpleLightBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "||",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurplePrimary
                            )
                        }
                        Text(
                            text = "Recycling Activity",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        uiState.monthlyActivity.forEach { stat ->
                            val hasActivity = stat.count > 0 && uiState.devicesRecycled > 0
                            val fraction = if (!hasActivity) {
                                0.05f
                            } else {
                                (stat.count.toFloat() / maxMonthCount.toFloat()).coerceIn(0.2f, 1f)
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height((82 * fraction).dp)
                                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                        .background(if (hasActivity) PurplePrimary else PurpleSoftBar)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = stat.month,
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            }

            // بطاقة Recycled Categories
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Recycled Categories",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    uiState.recycledCategories.forEach { category ->
                        val actualCount = if (uiState.devicesRecycled == 0) 0 else category.count
                        val progress = if (uiState.devicesRecycled == 0) {
                            0f
                        } else {
                            (actualCount.toFloat() / totalCategoryCount.toFloat()).coerceIn(0f, 1f)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(RoundedCornerShape(7.dp))
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
                                modifier = Modifier.width(86.dp)
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
                                text = "$actualCount",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextDark,
                                textAlign = TextAlign.End,
                                modifier = Modifier.width(20.dp)
                            )
                        }
                    }
                }
            }

            // بطاقة Your Environmental Impact السفلية
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = PurpleLightBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(PurplePrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✦",
                                fontSize = 10.sp,
                                color = PurplePrimary
                            )
                        }
                        Text(
                            text = "Your Environmental Impact",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PurplePrimary
                        )
                    }

                    Text(
                        text = "${uiState.devicesRecycled} electronic devices responsibly recycled",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    Text(
                        text = "Keep recycling to make a bigger impact.",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun ImpactMetricCard(
    value: String,
    label: String,
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
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = TextMuted
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YourImpactScreenPreview() {
    YourImpactScreen(
        uiState = YourImpactUiState(
            devicesRecycled = 0,
            pointsEarned = 0
        ),
        onBackClick = {}
    )
}