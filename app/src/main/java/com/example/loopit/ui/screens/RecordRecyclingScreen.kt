package com.example.loopit.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box

object RecordRecyclingPalette {
    val PrimaryPurple = Color(0xFF504DCC)
    val SoftPurpleBg = Color(0xFFEEEDFC)
    val GoldAccent = Color(0xFFDF911A)
    val SoftGoldBg = Color(0xFFFFF8EB)
    val RecErrorRed = Color(0xFFD73431)
    val BorderGray = Color(0xFFE6E6EC)
}

private val RecErrorRed = RecordRecyclingPalette.RecErrorRed
data class RecordRecyclingUiState(
    val deviceName: String = "Smartphone",
    val collectionPoint: String = "Green Loop Recycling Center",
    val estimatedPoints: Int = 120,
    val isConfirmed: Boolean = false,
    val showValidationError: Boolean = false,
    val deviceOptions: List<String> = listOf("Smartphone", "Laptop", "Charger", "Tablet", "Accessories"),
    val selectedDevice: String? = null,
    val showDeviceError: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordRecyclingScreen(
    modifier: Modifier = Modifier,
    uiState: RecordRecyclingUiState = RecordRecyclingUiState(),
    onBackClick: () -> Unit = {},
    onConfirmRecyclingClick: (deviceName: String, collectionPoint: String, points: Int) -> Unit = { _, _, _ -> },

    ) {
    var isConfirmed by remember(uiState.isConfirmed) { mutableStateOf(uiState.isConfirmed) }
    var showError by remember(uiState.showValidationError) { mutableStateOf(uiState.showValidationError) }
    var deviceMenuExpanded by remember { mutableStateOf(false) }
    var selectedDevice by remember(uiState.selectedDevice) { mutableStateOf(uiState.selectedDevice) }
    var showDeviceError by remember(uiState.showDeviceError) { mutableStateOf(uiState.showDeviceError) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Record Recycling",
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
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Confirm once you've dropped off your device",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (showDeviceError) RecordRecyclingPalette.RecErrorRed else RecordRecyclingPalette.BorderGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = RecordRecyclingPalette.SoftPurpleBg,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "📱",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = RecordRecyclingPalette.PrimaryPurple
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Device",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = selectedDevice ?: "Select device",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = if (selectedDevice != null) {
                                    MaterialTheme.colorScheme.onSurface
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }

                        Box {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = RecordRecyclingPalette.SoftPurpleBg,
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (showDeviceError) {
                                        RecordRecyclingPalette.RecErrorRed
                                    } else {
                                        RecordRecyclingPalette.PrimaryPurple.copy(alpha = 0.25f)
                                    }
                                ),
                                modifier = Modifier.clickable { deviceMenuExpanded = true }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = selectedDevice ?: "Choose",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = RecordRecyclingPalette.PrimaryPurple
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "▾",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = RecordRecyclingPalette.PrimaryPurple
                                    )
                                }
                            }

                            androidx.compose.material3.DropdownMenu(
                                expanded = deviceMenuExpanded,
                                onDismissRequest = { deviceMenuExpanded = false }
                            ) {
                                uiState.deviceOptions.forEach { option ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { Text(text = option) },
                                        onClick = {
                                            selectedDevice = option
                                            showDeviceError = false
                                            deviceMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                if (showDeviceError) {
                    Text(
                        text = "Please select a device from the list.",
                        style = MaterialTheme.typography.bodySmall,
                        color = RecordRecyclingPalette.RecErrorRed,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }

                SummaryInfoCard(
                    label = "Collection point",
                    value = uiState.collectionPoint,
                    icon = Icons.Outlined.LocationOn,
                    iconTint = RecordRecyclingPalette.PrimaryPurple,
                    iconContainerColor = RecordRecyclingPalette.SoftPurpleBg

                )

                SummaryInfoCard(
                    label = "You earn",
                    value = "+${uiState.estimatedPoints} points",
                    icon = Icons.Outlined.Stars,
                    iconTint = RecordRecyclingPalette.GoldAccent,
                    iconContainerColor = RecordRecyclingPalette.SoftPurpleBg,
                    valueColor = RecordRecyclingPalette.GoldAccent

                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (showError) RecErrorRed else RecordRecyclingPalette.BorderGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isConfirmed = !isConfirmed
                            if (isConfirmed) showError = false
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isConfirmed,
                            onCheckedChange = { checked ->
                                isConfirmed = checked
                                if (checked) showError = false
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = RecordRecyclingPalette.PrimaryPurple,
                                uncheckedColor = if (showError) RecErrorRed else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "I confirm that I have dropped off this device at the selected collection point",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                if (showError) {
                    Text(
                        text = "Please confirm that you have dropped off the device first.",
                        style = MaterialTheme.typography.bodySmall,
                        color = RecErrorRed,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Button(
                onClick = {
                    val chosenDevice = selectedDevice
                    showDeviceError = chosenDevice == null
                    showError = !isConfirmed
                    if (isConfirmed) {
                        onConfirmRecyclingClick(
                            uiState.deviceName,
                            uiState.collectionPoint,
                            uiState.estimatedPoints
                        )
                    }
                    if (chosenDevice != null && isConfirmed) {
                        onConfirmRecyclingClick(
                            chosenDevice,
                            uiState.collectionPoint,
                            uiState.estimatedPoints
                        )
                    }
                    else {
                        showError = true
                    }
                },
                shape = CircleShape,
                contentPadding = PaddingValues(vertical = 14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isConfirmed) {
                        RecordRecyclingPalette.PrimaryPurple
                    } else {
                        Color(0xFF504DCC).copy(alpha = 0.45f)
                    }, contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = "Confirm Recycling",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun SummaryInfoCard(
    label: String,
    value: String,
    icon: ImageVector,
    iconTint: Color,
    iconContainerColor: Color,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, RecordRecyclingPalette.BorderGray),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = iconContainerColor,
                modifier = Modifier.size(44.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = valueColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordRecyclingScreenPreview() {
    MaterialTheme {
        RecordRecyclingScreen()
    }
}