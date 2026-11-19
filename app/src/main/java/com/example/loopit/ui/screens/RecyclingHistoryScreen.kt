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

data class RecyclingHistoryItem(
    val id: String,
    val badge: String,
    val title: String,
    val category: String, // "Phones", "Laptops", "Batteries"
    val dateAndLocation: String,
    val pointsAwarded: Int
)

data class RecyclingHistoryUiState(
    val items: List<RecyclingHistoryItem> = emptyList()
)

private val PurplePrimary = Color(0xFF6B3CE9)
private val PurpleLightBg = Color(0xFFF3EFFE)
private val TextDark = Color(0xFF1A1A2E)
private val TextMuted = Color(0xFF7A7A8C)
private val BackgroundGray = Color(0xFFFAFAFC)

@Composable
fun RecyclingHistoryRoute(
    uiState: RecyclingHistoryUiState = RecyclingHistoryUiState(),
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    RecyclingHistoryScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun RecyclingHistoryScreen(
    uiState: RecyclingHistoryUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf("All", "Phones", "Laptops", "Batteries")
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredItems = remember(uiState.items, selectedFilter) {
        if (selectedFilter == "All") {
            uiState.items
        } else {
            uiState.items.filter { it.category.equals(selectedFilter, ignoreCase = true) }
        }
    }

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
                    text = "Recycling history",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) PurplePrimary else Color(0xFFEDEDF2))
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = filter,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color.White else TextDark
                        )
                    }
                }
            }

            if (filteredItems.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "No drop-offs recorded yet",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            text = "Recorded devices for this account will appear here.",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                }
            } else {
                filteredItems.forEach { item ->
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
                                        text = item.badge,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PurplePrimary
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        text = item.title,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                    Text(
                                        text = item.dateAndLocation,
                                        fontSize = 12.sp,
                                        color = TextMuted
                                    )
                                }
                            }
                            Text(
                                text = "+${item.pointsAwarded}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurplePrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecyclingHistoryScreenPreview() {
    RecyclingHistoryScreen(
        uiState = RecyclingHistoryUiState(),
        onBackClick = {}
    )
}