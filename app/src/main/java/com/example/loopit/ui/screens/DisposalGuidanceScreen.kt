package com.example.loopit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val LoopitPrimary = Color(0xFF504DCC)
private val LoopitPrimarySoft = Color(0xFFEDECFA)
private val LoopitSurfaceCard = Color(0xFFF8F9FC)
private val LoopitTextPrimary = Color(0xFF1A1C24)
private val LoopitTextSecondary = Color(0xFF5E6375)

data class GuidanceStepUiModel(
    val title: String,
    val description: String,
    val badgeText: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisposalGuidanceScreen(
    deviceName: String = "Smartphone",
    guidanceSteps: List<GuidanceStepUiModel> = defaultSmartphoneGuidance(),
    onBackClick: () -> Unit = {},
    onStartChecklistClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Safe Disposal Guidance",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = LoopitTextPrimary
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "←",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = LoopitTextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = onStartChecklistClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = LoopitPrimary)
                ) {
                    Text(
                        text = "Start Pre-Disposal Checklist",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))


            SelectedDeviceBanner(deviceName = deviceName)

            Text(
                text = "How to dispose safely",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = LoopitTextPrimary
            )


            guidanceSteps.forEach { step ->
                GuidanceStepCard(step = step)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SelectedDeviceBanner(deviceName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LoopitPrimarySoft)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📱",
                    fontSize = 22.sp
                )
            }
            Column {
                Text(
                    text = "Your device",
                    fontSize = 12.sp,
                    color = LoopitTextSecondary
                )
                Text(
                    text = deviceName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LoopitTextPrimary
                )
            }
        }
    }
}

@Composable
private fun GuidanceStepCard(step: GuidanceStepUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LoopitSurfaceCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LoopitPrimarySoft),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = step.badgeText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LoopitPrimary
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = step.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LoopitTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = step.description,
                    fontSize = 14.sp,
                    color = LoopitTextSecondary,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

private fun defaultSmartphoneGuidance() = listOf(
    GuidanceStepUiModel(
        title = "Back up your data",
        description = "Back up anything you want to keep before preparing the device.",
        badgeText = "1"
    ),
    GuidanceStepUiModel(
        title = "Battery safety",
        description = "Remove removable batteries if safe to do so. Never puncture or crush a battery.",
        badgeText = "2"
    ),
    GuidanceStepUiModel(
        title = "No household trash",
        description = "Never put electronics in household trash due to hazardous materials.",
        badgeText = "3"
    ),
    GuidanceStepUiModel(
        title = "Use an e-waste collection point",
        description = "Drop it at a certified e-waste collection point shown on the Loopit map.",
        badgeText = "4"
    )
)

@Preview(showBackground = true)
@Composable
fun DisposalGuidanceScreenPreview() {
    MaterialTheme {
        DisposalGuidanceScreen()
    }
}