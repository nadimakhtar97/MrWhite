package com.nadim.mrwhite.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadim.mrwhite.model.Player
import com.nadim.mrwhite.model.Role
import com.nadim.mrwhite.ui.theme.BgSurface
import com.nadim.mrwhite.ui.theme.MrWhiteTheme
import com.nadim.mrwhite.ui.theme.RoleCivilian
import com.nadim.mrwhite.ui.theme.RoleMrWhite
import com.nadim.mrwhite.ui.theme.RoleUndercover
import com.nadim.mrwhite.ui.theme.SurfaceContainer
import com.nadim.mrwhite.ui.theme.SurfacePure
import com.nadim.mrwhite.ui.theme.TextMuted
import com.nadim.mrwhite.ui.theme.TextPrimary
import com.nadim.mrwhite.ui.theme.TextSubtle

@Composable
fun PassRoleScreen(
    player: Player,
    playerIndex: Int,
    totalPlayers: Int,
    onNextPlayer: () -> Unit,
    onCancel: () -> Unit
) {
    BackHandler {
        onCancel()
    }

    var isHolding by remember(player.id) { mutableStateOf(false) }
    var hasRevealedOnce by remember(player.id) { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopHeader(
                title = "Secret Roles",
                onCloseClick = onCancel
            )
        },
        containerColor = BgSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                // Player X of N Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainer)
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Player ${playerIndex + 1} of $totalPlayers",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = if (isHolding) "Classified Identity" else "Pass to ${player.name}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isHolding)
                        "Release finger to conceal & pass"
                    else
                        "Ensure other players cannot see the screen",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )
            }

            // Main Hold Card Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentAlignment = Alignment.Center
            ) {
                if (!isHolding) {
                    // Concealed State Card
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(player.id) {
                                detectTapGestures(
                                    onPress = {
                                        isHolding = true
                                        hasRevealedOnce = true
                                        tryAwaitRelease()
                                        isHolding = false
                                    }
                                )
                            },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfacePure),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "👆",
                                    fontSize = 36.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Tap to reveal",
                                style = MaterialTheme.typography.headlineMedium,
                                color = TextPrimary
                            )

                        }
                    }
                } else {
                    // Revealed Secret State Card
                    val roleColor = when (player.role) {
                        Role.CIVILIAN -> RoleCivilian
                        Role.UNDERCOVER -> RoleCivilian
                        Role.MR_WHITE -> RoleCivilian
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(2.dp, roleColor, RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfacePure),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Your Secret Word",
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextSubtle
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = player.secretWord ?: "You are Mr. White!",
                                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                                color = roleColor,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            val instructionText = when (player.role) {
                                Role.CIVILIAN -> "Give brief, subtle clues. Don't reveal exact details"
                                Role.UNDERCOVER -> "Give brief, subtle clues. Don't reveal exact details"
                                Role.MR_WHITE -> "Give brief, subtle clues. Don't reveal exact details"
                            }

                            Text(
                                text = instructionText,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMuted,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Bottom Action (Shown if player has revealed at least once)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                if (hasRevealedOnce) {
                    Button(
                        onClick = onNextPlayer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TextPrimary),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Text(
                            text = if (playerIndex + 1 < totalPlayers) "Next Player →" else "Start Discussion →",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PassRoleScreenPreview() {
    MrWhiteTheme {
        PassRoleScreen(
            player = Player(1, "Player 1", Role.CIVILIAN, "Cappuccino"),
            playerIndex = 0,
            totalPlayers = 5,
            onNextPlayer = {},
            onCancel = {}
        )
    }
}
