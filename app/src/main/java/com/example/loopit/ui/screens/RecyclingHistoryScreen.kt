package com.example.loopit.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.LaptopMac
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val PrimaryPurple = Color(0xFF504DCC)
val SoftPurpleBg = Color(0xFFEEEDFC)
val GoldAccent = Color(0xFFDF911A)
val SoftGoldBg = Color(0xFFFFF8EB)
val BorderGray = Color(0xFFE6E6EC)

enum class RecycledDeviceType {
    SMARTPHONE,
    CHARGER,
    LAPTOP,
    ACCESSORIES
}

data class RecyclingRecordItem(
    val id: String,
    val deviceName: String,
    val collectionPoint: String,
    val pointsEarned: Int,
    val deviceType: RecycledDeviceType = RecycledDeviceType.SMARTPHONE
)

data class RecyclingHistoryUiState(
    val totalDevices: Int = 5,
    val totalPoints: Int = 510,
    val isLoading: Boolean = false,
    val records: List<RecyclingRecordItem> = defaultHistoryRecords()
)

fun defaultHistoryRecords(): List<RecyclingRecordItem> = listOf(
    RecyclingRecordItem(
        id = "1",
        deviceName = "Smartphone",
        collectionPoint = "Green Loop Recycling Center",
        pointsEarned = 120,
        deviceType = RecycledDeviceType.SMARTPHONE
    ),
    RecyclingRecordItem(
        id = "2",
        deviceName = "Smartphone",
        collectionPoint = "Green Loop Recycling Center",
        pointsEarned = 120,
        deviceType = RecycledDeviceType.SMARTPHONE
    ),
    RecyclingRecordItem(
        id = "3",
        deviceName = "Charger",
        collectionPoint = "City Waste Collection Drop",
        pointsEarned = 40,
        deviceType = RecycledDeviceType.CHARGER
    ),
    RecyclingRecordItem(
        id = "4",
        deviceName = "Laptop",
        collectionPoint = "District Collection Hub",
        pointsEarned = 200,
        deviceType = RecycledDeviceType.LAPTOP
    ),
    RecyclingRecordItem(
        id = "5",
        deviceName = "Accessories",
        collectionPoint = "Green Loop Recycling Center",
        pointsEarned = 30,
        deviceType = RecycledDeviceType.ACCESSORIES
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecyclingHistoryScreen(
    modifier: Modifier = Modifier,
    uiState: RecyclingHistoryUiState = RecyclingHistoryUiState(),
    onBackClick: () -> Unit = {},
    onItemClick: (RecyclingRecordItem) -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Recycling History",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${uiState.totalDevices} devices recycled",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${uiState.totalPoints} points",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryPurple
                )
            }

            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryPurple)
                    }
                }

                uiState.records.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No recycling history yet",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Your recorded device drop-offs will appear here.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(
                            items = uiState.records,
                            key = { it.id }
                        ) { item ->
                            RecyclingHistoryCard(
                                item = item,
                                onClick = { onItemClick(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecyclingHistoryCard(
    item: RecyclingRecordItem,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, BorderGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SoftPurpleBg,
                modifier = Modifier.size(42.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = deviceTypeIcon(item.deviceType),
                        contentDescription = null,
                        tint = PrimaryPurple,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = item.deviceName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.collectionPoint,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SoftGoldBg
            ) {
                Text(
                    text = "+${item.pointsEarned}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = GoldAccent,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

private fun deviceTypeIcon(type: RecycledDeviceType): ImageVector = when (type) {
    RecycledDeviceType.SMARTPHONE -> Icons.Outlined.PhoneAndroid
    RecycledDeviceType.CHARGER -> Icons.Outlined.BatteryChargingFull
    RecycledDeviceType.LAPTOP -> Icons.Outlined.LaptopMac
    RecycledDeviceType.ACCESSORIES -> Icons.Outlined.Headphones
}

@Preview(showBackground = true)
@Composable
private fun RecyclingHistoryScreenPreview() {
    MaterialTheme {
        RecyclingHistoryScreen()
    }
}