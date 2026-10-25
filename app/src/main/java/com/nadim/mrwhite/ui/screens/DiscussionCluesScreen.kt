package com.nadim.mrwhite.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadim.mrwhite.model.Player
import com.nadim.mrwhite.model.Role
import com.nadim.mrwhite.ui.theme.BgSurface
import com.nadim.mrwhite.ui.theme.MrWhiteTheme
import com.nadim.mrwhite.ui.theme.RoleUndercover
import com.nadim.mrwhite.ui.theme.SurfaceContainer
import com.nadim.mrwhite.ui.theme.SurfaceContainerLow
import com.nadim.mrwhite.ui.theme.SurfacePure
import com.nadim.mrwhite.ui.theme.TextMuted
import com.nadim.mrwhite.ui.theme.TextPrimary
import com.nadim.mrwhite.ui.theme.TextSubtle

@Composable
fun DiscussionCluesScreen(
    roundNumber: Int,
    players: List<Player>,
    onToggleSpoken: (Player) -> Unit,
    onMoveToVote: () -> Unit,
    onEndGame: () -> Unit
) {
    BackHandler {
        onEndGame()
    }

    val activePlayers = players.filter { !it.isEliminated }
    val spokenCount = activePlayers.count { it.hasSpoken }

    Scaffold(
        topBar = {
            TopHeader(
                title = "Discussion",
                onCloseClick = onEndGame
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgSurface)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onMoveToVote,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TextPrimary),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text(
                        text = "Move to vote  →",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedButton(
                    onClick = onEndGame,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Text(
                        text = "End game prematurely",
                        fontSize = 14.sp,
                        color = TextMuted
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

            // Subtitle status row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(RoleUndercover)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "CLUE PHASE · IN ORDER",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )
                }

                Text(
                    text = "Round 0$roundNumber",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    color = TextSubtle
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Describe your word in one sentence.",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Go round the table in order. Each player gives one subtle hint without giving away the exact word.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Still in (${activePlayers.size} players)",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary
                )
                Text(
                    text = "$spokenCount/${activePlayers.size} spoken",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Player Cards
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                activePlayers.forEach { player ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onToggleSpoken(player) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (player.hasSpoken) SurfaceContainerLow else SurfacePure
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(SurfaceContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "%02d".format(player.id),
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = player.name,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp),
                                    color = TextPrimary
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (player.hasSpoken) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(TextPrimary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "✓",
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "Tap to check",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        color = TextSubtle
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Guidance Tip Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "💡",
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Listen attentively",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Undercovers share similar words. Mr White has no word at all and must bluff cautiously based on preceding statements.",
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiscussionCluesScreenPreview() {
    MrWhiteTheme {
        DiscussionCluesScreen(
            roundNumber = 1,
            players = listOf(
                Player(1, "Player 1", Role.CIVILIAN, "Cappuccino"),
                Player(2, "Player 2", Role.CIVILIAN, "Cappuccino"),
                Player(3, "Player 3", Role.UNDERCOVER, "Espresso"),
                Player(4, "Player 4", Role.CIVILIAN, "Cappuccino"),
                Player(5, "Player 5", Role.MR_WHITE, null)
            ),
            onToggleSpoken = {},
            onMoveToVote = {},
            onEndGame = {}
        )
    }
}
