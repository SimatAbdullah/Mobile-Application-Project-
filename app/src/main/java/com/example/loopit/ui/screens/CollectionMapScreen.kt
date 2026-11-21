package com.example.loopit.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.loopit.domain.model.CollectionPointWithDistance
import com.example.loopit.ui.viewmodel.CollectionPointViewModel
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview
import com.example.loopit.ui.viewmodel.CollectionMapUiState
import com.example.loopit.domain.model.AcceptedDeviceModel

private val PrimaryColor = Color(0xFF6B3CE9)
private val MapBackground = Color(0xFFFAFAFC)
private val MapTextDark = Color(0xFF1A1A2E)
private val MapTextMuted = Color(0xFF7A7A8C)
private val MapPurpleLight = Color(0xFFF3EFFE)

@Composable
fun CollectionMapScreen(
    viewModel: CollectionPointViewModel,
    onPointClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onRewardsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val uiState by viewModel.mapUiState.collectAsStateWithLifecycle()

    CollectionMapContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::updateSearchQuery,
        onRetry = viewModel::loadCollectionPoints,
        onPointClick = onPointClick,
        onHomeClick = onHomeClick,
        onRewardsClick = onRewardsClick,
        onProfileClick = onProfileClick
    )
}

@Composable
private fun CollectionMapContent(
    uiState: CollectionMapUiState,
    onSearchQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
    onPointClick: (Int) -> Unit,
    onHomeClick: () -> Unit,
    onRewardsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        containerColor = MapBackground,
        bottomBar = {
            CollectionMapBottomBar(
                onHomeClick = onHomeClick,
                onRewardsClick = onRewardsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = onHomeClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "←",
                        fontSize = 24.sp,
                        color = MapTextDark
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Collection Points",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MapTextDark
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        text = "Search a location",
                        color = MapTextMuted,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Canvas(modifier = Modifier.size(20.dp)) {
                        val radius = size.width * 0.30f

                        drawCircle(
                            color = MapTextMuted,
                            radius = radius,
                            center = Offset(
                                size.width * 0.40f,
                                size.height * 0.40f
                            ),
                            style = Stroke(width = 1.8.dp.toPx())
                        )

                        drawLine(
                            color = MapTextMuted,
                            start = Offset(
                                size.width * 0.62f,
                                size.height * 0.62f
                            ),
                            end = Offset(
                                size.width * 0.88f,
                                size.height * 0.88f
                            ),
                            strokeWidth = 1.8.dp.toPx()
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(50),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = PrimaryColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            CollectionMapPlaceholder()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "${uiState.filteredPoints.size} points nearby",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MapTextDark
            )

            Spacer(modifier = Modifier.height(12.dp))

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = PrimaryColor
                        )
                    }
                }

                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.errorMessage.orEmpty(),
                            color = MaterialTheme.colorScheme.error
                        )

                        TextButton(
                            onClick = onRetry
                        ) {
                            Text("Try Again")
                        }
                    }
                }

                uiState.filteredPoints.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No collection points found.")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(
                            items = uiState.filteredPoints,
                            key = { it.id }
                        ) { point ->
                            CollectionPointCard(
                                point = point,
                                onClick = {
                                    onPointClick(point.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CollectionMapPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = MapPurpleLight,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Interactive Map",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Map preview",
                fontSize = 12.sp,
                color = MapTextMuted
            )
        }
    }
}

@Composable
private fun CollectionPointCard(
    point: CollectionPointWithDistance,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = PrimaryColor.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "♻",
                    fontSize = 24.sp,
                    color = PrimaryColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = point.name,
                    fontSize = 16.sp,
                    color = MapTextDark,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = point.address,
                    fontSize = 12.sp,
                    color = MapTextMuted
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = point.distanceKm?.let {
                        String.format(
                            Locale.US,
                            "%.1f km away",
                            it
                        )
                    } ?: "Distance unavailable",
                    fontSize = 12.sp,
                    color = PrimaryColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = point.acceptedDevices
                        .take(2)
                        .joinToString(", ") { it.name }
                        .ifEmpty { "Accepted devices unavailable" },
                    fontSize = 12.sp,
                    color = MapTextMuted
                )

                if (point.acceptedDevices.size > 2) {
                    Text(
                        text = "+${point.acceptedDevices.size - 2}",
                        fontSize = 12.sp,
                        color = MapTextMuted
                    )
                }
            }

            Text(
                text = "›",
                fontSize = 26.sp,
                color = PrimaryColor
            )
        }
    }
}

@Composable
private fun CollectionMapBottomBar(
    onHomeClick: () -> Unit,
    onRewardsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onHomeClick,
            icon = { MapNavIcon("Home", MapTextMuted) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { MapNavIcon("Map", PrimaryColor) },
            label = { Text("Map") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryColor,
                selectedTextColor = PrimaryColor,
                indicatorColor = MapPurpleLight
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = onRewardsClick,
            icon = { MapNavIcon("Rewards", MapTextMuted) },
            label = { Text("Rewards") }
        )

        NavigationBarItem(
            selected = false,
            onClick = onProfileClick,
            icon = { MapNavIcon("Profile", MapTextMuted) },
            label = { Text("Profile") }
        )
    }
}

@Composable
private fun MapNavIcon(
    name: String,
    color: Color
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val u = size.width / 24f
        val stroke = Stroke(width = 1.7f * u)

        fun line(vararg points: Pair<Int, Int>) {
            val path = Path().apply {
                moveTo(
                    points.first().first * u,
                    points.first().second * u
                )

                for (point in points.drop(1)) {
                    lineTo(
                        point.first * u,
                        point.second * u
                    )
                }
            }

            drawPath(
                path,
                color,
                style = stroke
            )
        }

        fun ring(
            x: Float,
            y: Float,
            radius: Float
        ) {
            drawCircle(
                color,
                radius * u,
                Offset(x * u, y * u),
                style = stroke
            )
        }

        when (name) {

            "Home" -> {
                line(
                    3 to 10,
                    12 to 3,
                    21 to 10
                )

                line(
                    5 to 9,
                    5 to 21,
                    10 to 21,
                    10 to 15,
                    14 to 15,
                    14 to 21,
                    19 to 21,
                    19 to 9
                )
            }

            "Map" -> {
                val path = Path().apply {
                    moveTo(12 * u, 22 * u)

                    cubicTo(
                        9 * u, 18 * u,
                        4 * u, 13 * u,
                        4 * u, 10 * u
                    )

                    cubicTo(
                        4 * u, 0f,
                        20 * u, 0f,
                        20 * u, 10 * u
                    )

                    cubicTo(
                        20 * u, 13 * u,
                        15 * u, 18 * u,
                        12 * u, 22 * u
                    )

                    close()
                }

                drawPath(
                    path,
                    color,
                    style = stroke
                )

                ring(12f, 9f, 3f)
            }

            "Rewards" -> {
                ring(12f, 8f, 5f)

                line(
                    8 to 12,
                    5 to 21,
                    10 to 19,
                    12 to 22,
                    14 to 13
                )

                line(
                    16 to 12,
                    19 to 21,
                    14 to 19
                )
            }

            "Profile" -> {
                ring(12f, 7f, 4f)

                val path = Path().apply {
                    moveTo(4 * u, 21 * u)

                    cubicTo(
                        4 * u, 11 * u,
                        20 * u, 11 * u,
                        20 * u, 21 * u
                    )
                }

                drawPath(
                    path,
                    color,
                    style = stroke
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CollectionMapPreview() {
    MaterialTheme {
        CollectionMapContent(
            uiState = CollectionMapUiState(
                isLoading = false,
                collectionPoints = listOf(
                    CollectionPointWithDistance(
                        id = 1,
                        name = "Dammam Recycling Center (Demo)",
                        address = "Dammam, Saudi Arabia",
                        latitude = 26.4207,
                        longitude = 50.0888,
                        distanceKm = null,
                        acceptedDevices = listOf(
                            AcceptedDeviceModel(1, "Smartphone", "smartphone"),
                            AcceptedDeviceModel(2, "Charger", "charger"),
                            AcceptedDeviceModel(3, "Tablet", "tablet"),
                            AcceptedDeviceModel(4, "Laptop", "laptop"),
                            AcceptedDeviceModel(5, "Accessories", "accessories")
                        )
                    ),
                    CollectionPointWithDistance(
                        id = 2,
                        name = "Khobar E-Waste Center (Demo)",
                        address = "Al Khobar, Saudi Arabia",
                        latitude = 26.2172,
                        longitude = 50.1971,
                        distanceKm = null,
                        acceptedDevices = emptyList()
                    ),
                    CollectionPointWithDistance(
                        id = 3,
                        name = "Dhahran Collection Point (Demo)",
                        address = "Dhahran, Saudi Arabia",
                        latitude = 26.2361,
                        longitude = 50.0393,
                        distanceKm = null,
                        acceptedDevices = emptyList()
                    )
                )
            ),
            onSearchQueryChange = {},
            onRetry = {},
            onPointClick = {},
            onHomeClick = {},
            onRewardsClick = {},
            onProfileClick = {}
        )
    }
}