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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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

private val PurplePrimary = Color(0xFF6B3CE9)
private val PurpleLightBg = Color(0xFFF3EFFE)
private val TextDark = Color(0xFF1A1A2E)
private val TextMuted = Color(0xFF7A7A8C)
private val BackgroundGray = Color(0xFFFAFAFC)

@Composable
fun AccessibilitySettingsRoute(
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    AccessibilitySettingsScreen(
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun AccessibilitySettingsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textScale by remember { mutableFloatStateOf(0.5f) }
    var highContrast by remember { mutableStateOf(true) }
    var reduceMotion by remember { mutableStateOf(false) }
    var screenReaderHints by remember { mutableStateOf(true) }
    var largerTouchTargets by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("English") }

    val previewFontSize = (12 + (textScale * 6)).sp

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
            verticalArrangement = Arrangement.spacedBy(14.dp)
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
                    text = "Accessibility",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PurpleLightBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "T",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurplePrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Text size",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "Preview: Recycle responsibly",
                                fontSize = previewFontSize,
                                color = TextMuted
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("A", fontSize = 12.sp, color = TextMuted)
                        Slider(
                            value = textScale,
                            onValueChange = { textScale = it },
                            colors = SliderDefaults.colors(
                                thumbColor = PurplePrimary,
                                activeTrackColor = PurplePrimary,
                                inactiveTrackColor = PurpleLightBg
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text("A", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    AccessibilityOptionRow(
                        badge = "HC",
                        title = "High contrast",
                        subtitle = "Stronger colors and borders",
                        checked = highContrast,
                        onCheckedChange = { highContrast = it }
                    )
                    AccessibilityOptionRow(
                        badge = "RM",
                        title = "Reduce motion",
                        subtitle = "Minimize animations",
                        checked = reduceMotion,
                        onCheckedChange = { reduceMotion = it }
                    )
                    AccessibilityOptionRow(
                        badge = "SR",
                        title = "Screen reader hints",
                        subtitle = "Extra accessible descriptions",
                        checked = screenReaderHints,
                        onCheckedChange = { screenReaderHints = it }
                    )
                    AccessibilityOptionRow(
                        badge = "LT",
                        title = "Larger touch targets",
                        subtitle = "Bigger buttons and spacing",
                        checked = largerTouchTargets,
                        onCheckedChange = { largerTouchTargets = it }
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        language = if (language == "English") "العربية" else "English"
                    },
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(PurpleLightBg),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "i",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurplePrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Language",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )
                            Text(
                                text = "Navigation labels preview",
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }

                    Text(
                        text = "$language v",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDark
                    )
                }
            }
        }
    }
}

@Composable
private fun AccessibilityOptionRow(
    badge: String,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(PurpleLightBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badge,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = PurplePrimary,
                checkmarkColor = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccessibilitySettingsScreenPreview() {
    AccessibilitySettingsScreen(onBackClick = {})
}