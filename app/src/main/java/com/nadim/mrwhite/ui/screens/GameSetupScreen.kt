package com.nadim.mrwhite.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadim.mrwhite.data.WordRepository
import com.nadim.mrwhite.model.Category
import com.nadim.mrwhite.model.Difficulty
import com.nadim.mrwhite.model.GameSettings
import com.nadim.mrwhite.ui.theme.BgSurface
import com.nadim.mrwhite.ui.theme.BorderSubtle
import com.nadim.mrwhite.ui.theme.MrWhiteTheme
import com.nadim.mrwhite.ui.theme.SurfaceContainerLow
import com.nadim.mrwhite.ui.theme.SurfacePure
import com.nadim.mrwhite.ui.theme.TextMuted
import com.nadim.mrwhite.ui.theme.TextPrimary
import com.nadim.mrwhite.ui.theme.TextSubtle

@Composable
fun GameSetupScreen(
    settings: GameSettings,
    onPlayerCountChange: (Int) -> Unit,
    onUndercoverCountChange: (Int) -> Unit,
    onMrWhiteCountChange: (Int) -> Unit,
    onDifficultyChange: (Difficulty) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onTimerChange: (Int) -> Unit,
    onStartRound: () -> Unit
) {
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgSurface)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = onStartRound,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TextPrimary),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Start round  →",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        },
        containerColor = BgSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "New game.",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
            Text(
                text = "Configure the table, then start the round.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Card 1: Players
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePure)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Players",
                            style = MaterialTheme.typography.headlineSmall,
                            color = TextPrimary
                        )
                        StepperControl(
                            value = settings.playerCount,
                            onValueChange = onPlayerCountChange,
                            minValue = 3,
                            maxValue = 12
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Recommended: 5–8 for the sharpest deduction.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSubtle
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Card 2: Roles
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePure)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Roles",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary
                    )
                    Text(
                        text = "The rest will be civilians. (${settings.civilianCount} civilians).",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSubtle
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Undercovers",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        StepperControl(
                            value = settings.undercoverCount,
                            onValueChange = onUndercoverCountChange,
                            minValue = 1,
                            maxValue = (settings.playerCount - settings.mrWhiteCount - 1).coerceAtLeast(1)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mr Whites",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        StepperControl(
                            value = settings.mrWhiteCount,
                            onValueChange = onMrWhiteCountChange,
                            minValue = 0,
                            maxValue = (settings.playerCount - settings.undercoverCount - 1).coerceAtLeast(0)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${settings.mrWhiteCount} Mr White · ${settings.undercoverCount} Undercover · ${settings.civilianCount} Civilians",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Card 3: Difficulty
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePure)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Difficulty",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary
                    )
                    Text(
                        text = "How close the Undercover's word is to the Civilian's.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSubtle
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Difficulty.entries.forEach { diff ->
                            val isSelected = settings.difficulty == diff
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .clip(RoundedCornerShape(22.dp))
                                    .background(if (isSelected) TextPrimary else SurfaceContainerLow)
                                    .clickable { onDifficultyChange(diff) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = diff.name.lowercase().replaceFirstChar { it.uppercase() },
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = if (isSelected) Color.White else TextPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Card 4: Categories
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePure)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Category",
                                style = MaterialTheme.typography.headlineSmall,
                                color = TextPrimary
                            )
                            Text(
                                text = "Selected: ${settings.selectedCategory.displayName}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSubtle
                            )
                        }

                        Box {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                                    .clickable { categoryDropdownExpanded = true }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = settings.selectedCategory.displayName,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            DropdownMenu(
                                expanded = categoryDropdownExpanded,
                                onDismissRequest = { categoryDropdownExpanded = false }
                            ) {
                                WordRepository.getCategories().forEach { cat ->
                                    DropdownMenuItem(
                                        text = { Text(cat.displayName) },
                                        onClick = {
                                            onCategoryChange(cat)
                                            categoryDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Card 5: Discussion Timer
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePure)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Discussion timer",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextPrimary
                    )
                    Text(
                        text = "Optional. The table can also debate untimed.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSubtle
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(0 to "Off", 30 to "30s", 60 to "60s", 120 to "2m").forEach { (sec, label) ->
                            val isSelected = settings.discussionTimerSeconds == sec
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) TextPrimary else SurfaceContainerLow)
                                    .clickable { onTimerChange(sec) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) Color.White else TextPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameSetupScreenPreview() {
    MrWhiteTheme {
        GameSetupScreen(
            settings = GameSettings(),
            onPlayerCountChange = {},
            onUndercoverCountChange = {},
            onMrWhiteCountChange = {},
            onDifficultyChange = {},
            onCategoryChange = {},
            onTimerChange = {},
            onStartRound = {}
        )
    }
}
