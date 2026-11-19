package com.example.loopit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.loopit.domain.model.AcceptedDeviceModel
import com.example.loopit.domain.model.CollectionPointWithDistance
import com.example.loopit.ui.viewmodel.CollectionPointViewModel
import java.util.Locale

private val DetailsPrimary = Color(0xFF504DCC)

@Composable
fun CollectionPointDetailsScreen(
    collectionPointId: Int,
    viewModel: CollectionPointViewModel,
    onBackClick: () -> Unit,
    onRecycleClick: (Int, Int) -> Unit,
    onDirectionsClick: (Double, Double) -> Unit
) {
    val uiState by viewModel.detailsUiState
        .collectAsStateWithLifecycle()

    LaunchedEffect(collectionPointId) {
        viewModel.loadCollectionPointDetails(collectionPointId)
    }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            CollectionDetailsHeader(onBackClick)

            when {
                uiState.isLoading ||
                        (uiState.collectionPoint != null &&
                                uiState.collectionPoint?.id != collectionPointId) -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = DetailsPrimary
                        )
                    }
                }

                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.errorMessage.orEmpty(),
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )

                        TextButton(
                            onClick = {
                                viewModel.loadCollectionPointDetails(
                                    collectionPointId
                                )
                            }
                        ) {
                            Text("Try Again")
                        }
                    }
                }

                else -> {
                    val point = uiState.collectionPoint

                    if (point != null) {
                        CollectionDetailsContent(
                            point = point,
                            onRecycleClick = onRecycleClick,
                            onDirectionsClick = onDirectionsClick
                        )
                    } else {
                        Text("Collection point not found.")
                    }
                }
            }
        }
    }
}

@Composable
private fun CollectionDetailsHeader(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = "←",
                fontSize = 26.sp,
                color = DetailsPrimary
            )
        }

        Text(
            text = "Collection Point Details",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 48.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CollectionDetailsContent(
    point: CollectionPointWithDistance,
    onRecycleClick: (Int, Int) -> Unit,
    onDirectionsClick: (Double, Double) -> Unit
) {
    var showDeviceDialog by remember(point.id) {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {

        CollectionDetailsMapPlaceholder()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = point.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("📍", fontSize = 18.sp)

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = point.address,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = point.distanceKm?.let {
                String.format(Locale.US, "%.1f km away", it)
            } ?: "Distance unavailable",
            color = DetailsPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Accepted Devices",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (point.acceptedDevices.isEmpty()) {
            Text("No accepted devices available.")
        } else {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                point.acceptedDevices.forEach { device ->
                    AcceptedDeviceChip(device)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                showDeviceDialog = true
            },
            enabled = point.acceptedDevices.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DetailsPrimary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Recycle Here",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {
                onDirectionsClick(
                    point.latitude,
                    point.longitude
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(30.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                DetailsPrimary
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = DetailsPrimary
            )
        ) {
            Text(
                text = "Get Directions",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    if (showDeviceDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeviceDialog = false
            },
            title = {
                Text("Select a Device")
            },
            text = {
                Column {
                    point.acceptedDevices.forEach { device ->
                        TextButton(
                            onClick = {
                                showDeviceDialog = false
                                onRecycleClick(
                                    point.id,
                                    device.id
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = device.name,
                                color = DetailsPrimary
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeviceDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun CollectionDetailsMapPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = DetailsPrimary.copy(alpha = 0.08f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "📍",
                fontSize = 32.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Interactive Map",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DetailsPrimary
            )

            Text(
                text = "Map preview",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AcceptedDeviceChip(
    device: AcceptedDeviceModel
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = DetailsPrimary.copy(alpha = 0.10f)
    ) {
        Text(
            text = device.name,
            color = DetailsPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CollectionPointDetailsPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                CollectionDetailsHeader(
                    onBackClick = {}
                )

                CollectionDetailsContent(
                    point = CollectionPointWithDistance(
                        id = 1,
                        name = "Dammam Recycling Center (Demo)",
                        address = "Dammam, Saudi Arabia",
                        latitude = 26.4207,
                        longitude = 50.0888,
                        distanceKm = null,
                        acceptedDevices = listOf(
                            AcceptedDeviceModel(
                                1, "Smartphone", "smartphone"
                            ),
                            AcceptedDeviceModel(
                                2, "Charger", "charger"
                            ),
                            AcceptedDeviceModel(
                                3, "Tablet", "tablet"
                            ),
                            AcceptedDeviceModel(
                                4, "Laptop", "laptop"
                            ),
                            AcceptedDeviceModel(
                                5, "Accessories", "accessories"
                            )
                        )
                    ),
                    onRecycleClick = { _, _ -> },
                    onDirectionsClick = { _, _ -> }
                )
            }
        }
    }
}