package com.example.loopit.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val identifyPurple = Color(0xFF5148C8)
private val identifyLavender = Color(0xFFEFEDFC)
private val identifyBackground = Color(0xFFF8FAFD)

private val deviceChoices = listOf(
    "Smartphone" to "📱",
    "Charger" to "🔌",
    "Tablet" to "▣",
    "Laptop" to "💻",
    "Accessories" to "🎧"
)

@Composable
fun IdentifyDeviceScreen(
    onBackClick: () -> Unit,
    onContinueToGuidance: (String) -> Unit
) {
    var selected by rememberSaveable { mutableStateOf<String?>(null) }
    var showPhotoDemo by rememberSaveable { mutableStateOf(false) }
    val device = selected

    BackHandler(enabled = device != null && !showPhotoDemo) {
        selected = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(identifyBackground)
            .safeDrawingPadding()
            .padding(horizontal = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                if (device == null) onBackClick() else selected = null
            }) {
                Text("←", fontSize = 24.sp)
            }
            Text(
                if (device == null) "Identify a Device" else "Device Result",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        if (device == null) {
            Column(
                Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Tell us what you're recycling so we can show safe disposal steps.")

                Surface(
                    onClick = { showPhotoDemo = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = identifyLavender
                ) {
                    Column(
                        modifier = Modifier
                            .drawBehind {
                                drawRoundRect(
                                    color = identifyPurple,
                                    cornerRadius = CornerRadius(20.dp.toPx()),
                                    style = Stroke(
                                        width = 2.dp.toPx(),
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(6.dp.toPx(), 5.dp.toPx())
                                        )
                                    )
                                )
                            }
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            Modifier.size(56.dp).background(identifyPurple, CircleShape),
                            contentAlignment = Alignment.Center
                        ) { Text("📷", fontSize = 24.sp) }
                        Spacer(Modifier.height(10.dp))
                        Text("Take a Photo", fontWeight = FontWeight.Bold)
                        Text("Point your camera at the device")
                    }
                }

                Text("or", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Text("Select Manually", fontWeight = FontWeight.Bold)
                Text("Use this if the photo doesn't work.")

                deviceChoices.forEach { (name, symbol) ->
                    DeviceChoiceRow(name, symbol) { selected = name }
                }
                Spacer(Modifier.height(12.dp))
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(Modifier.height(12.dp))
                Box(
                    Modifier.size(140.dp)
                        .background(identifyLavender, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(deviceChoices.first { it.first == device }.second, fontSize = 54.sp)
                }
                Surface(shape = CircleShape, color = identifyLavender) {
                    Text(
                        "✓ Device identified",
                        Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        color = identifyPurple
                    )
                }
                Text(device, style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold)
                Text(
                    "Next, we'll show you how to dispose of it safely.",
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { onContinueToGuidance(device) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = identifyPurple)
                ) { Text("Continue to Disposal Guidance") }

                TextButton(onClick = { selected = null }) {
                    Text("Not your device? Select manually", color = identifyPurple)
                }
            }
        }
    }

    if (showPhotoDemo) {
        AlertDialog(
            onDismissRequest = { showPhotoDemo = false },
            title = { Text("Try a sample photo") },
            text = { Text("This preview uses a sample smartphone to show the result.") },
            confirmButton = {
                TextButton(onClick = {
                    showPhotoDemo = false
                    selected = "Smartphone"
                }) { Text("Show sample result") }
            },
            dismissButton = {
                TextButton(onClick = { showPhotoDemo = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun DeviceChoiceRow(name: String, symbol: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            Modifier.clickable(onClick = onClick).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(36.dp).background(identifyLavender, CircleShape),
                contentAlignment = Alignment.Center
            ) { Text(symbol, color = identifyPurple) }
            Spacer(Modifier.width(12.dp))
            Text(name, Modifier.weight(1f), fontWeight = FontWeight.Medium)
            Text("›", fontSize = 24.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IdentifyDevicePreview() {
    MaterialTheme {
        IdentifyDeviceScreen(onBackClick = {}, onContinueToGuidance = {})
    }
}