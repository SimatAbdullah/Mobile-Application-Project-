package com.example.loopit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ChecklistPrimary = Color(0xFF504DCC)
private val ChecklistPrimarySoft = Color(0xFFEDECFA)
private val ChecklistCardBg = Color(0xFFF8F9FC)
private val ChecklistTextPrimary = Color(0xFF1A1C24)
private val ChecklistTextSecondary = Color(0xFF5E6375)

data class ChecklistItemUiModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val isChecked: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreDisposalChecklistScreen(
    initialItems: List<ChecklistItemUiModel> = defaultChecklistItems(),
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    var items by remember { mutableStateOf(initialItems) }

    val completedCount = items.count { it.isChecked }
    val totalCount = items.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
    val percentage = (progress * 100).toInt()
    val allCompleted = completedCount == totalCount && totalCount > 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Before You Recycle",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ChecklistTextPrimary
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
                            color = ChecklistTextPrimary
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
                    onClick = onContinueClick,
                    enabled = allCompleted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ChecklistPrimary,
                        disabledContainerColor = Color(0xFFD5D8E2)
                    )
                ) {
                    Text(
                        text = "Continue",
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
            Text(
                text = "Prepare your device safely before recycling.",
                fontSize = 15.sp,
                color = ChecklistTextSecondary
            )


            ChecklistProgressCard(
                completedCount = completedCount,
                totalCount = totalCount,
                percentage = percentage,
                progress = progress
            )


            items.forEachIndexed { index, item ->
                ChecklistItemRow(
                    item = item,
                    onToggle = {
                        items = items.mapIndexed { i, current ->
                            if (i == index) current.copy(isChecked = !current.isChecked) else current
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ChecklistProgressCard(
    completedCount: Int,
    totalCount: Int,
    percentage: Int,
    progress: Float
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ChecklistPrimarySoft)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$completedCount of $totalCount completed",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ChecklistTextPrimary
                )
                Text(
                    text = "$percentage%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = ChecklistPrimary
                )
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = ChecklistPrimary,
                trackColor = Color.White
            )
        }
    }
}

@Composable
private fun ChecklistItemRow(
    item: ChecklistItemUiModel,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isChecked) ChecklistPrimarySoft else ChecklistCardBg
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(checkedColor = ChecklistPrimary)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ChecklistTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.subtitle,
                    fontSize = 13.sp,
                    color = ChecklistTextSecondary
                )
            }
        }
    }
}

private fun defaultChecklistItems() = listOf(
    ChecklistItemUiModel(
        id = "delete_data",
        title = "Delete personal data",
        subtitle = "Use \"Factory reset\" in Settings."
    ),
    ChecklistItemUiModel(
        id = "sign_out",
        title = "Sign out of accounts",
        subtitle = "Google, Apple, email & social apps."
    ),
    ChecklistItemUiModel(
        id = "sim_card",
        title = "Remove SIM card",
        subtitle = "Use the SIM tool or a paperclip."
    ),
    ChecklistItemUiModel(
        id = "sd_card",
        title = "Remove memory/SD card (if any)",
        subtitle = "Skip if your device has none."
    )
)

@Preview(showBackground = true)
@Composable
fun PreDisposalChecklistScreenPreview() {
    MaterialTheme {
        PreDisposalChecklistScreen()
    }
}