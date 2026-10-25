package com.nadim.mrwhite.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadim.mrwhite.model.Player
import com.nadim.mrwhite.model.Category
import com.nadim.mrwhite.model.Role
import com.nadim.mrwhite.model.Winner
import com.nadim.mrwhite.model.WordPair
import com.nadim.mrwhite.ui.theme.BgSurface
import com.nadim.mrwhite.ui.theme.MrWhiteTheme
import com.nadim.mrwhite.ui.theme.RoleCivilian
import com.nadim.mrwhite.ui.theme.RoleMrWhite
import com.nadim.mrwhite.ui.theme.RoleUndercover
import com.nadim.mrwhite.ui.theme.SurfaceContainer
import com.nadim.mrwhite.ui.theme.SurfaceContainerLow
import com.nadim.mrwhite.ui.theme.SurfacePure
import com.nadim.mrwhite.ui.theme.TextMuted
import com.nadim.mrwhite.ui.theme.TextPrimary
import com.nadim.mrwhite.ui.theme.TextSubtle

@Composable
fun RoundRecapVictoryScreen(
    winner: Winner?,
    players: List<Player>,
    wordPair: WordPair?,
    roundNumber: Int,
    onPlayAgain: () -> Unit
) {
    BackHandler {
        onPlayAgain()
    }

    val survivedCount = players.count { !it.isEliminated }

    val (winTitle, winSubtitle, winColor) = when (winner) {
        Winner.CIVILIANS -> Triple("Civilians Win!", "All infiltrators have been eliminated.", RoleCivilian)
        Winner.INFILTRATORS -> Triple("Infiltrators Win!", "The infiltrators outmaneuvered the civilians.", RoleUndercover)
        Winner.MR_WHITE_GUESS_WIN -> Triple("Mr White Wins!", "Mr White correctly guessed the secret civilian word!", RoleMrWhite)
        null -> Triple("Round Ended", "Game was completed.", TextPrimary)
    }

    Scaffold(
        topBar = {
            TopHeader(
                title = "Round Recap",
                showUserIcon = false
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgSurface)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onPlayAgain,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TextPrimary),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Play another round  →",
                        fontSize = 17.sp,
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

            // Victory Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = winColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = winTitle,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = winSubtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Stats Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePure)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$survivedCount / ${players.size}",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Survived",
                            fontSize = 12.sp,
                            color = TextSubtle
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(32.dp)
                            .background(SurfaceContainer)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = roundNumber.toString(),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Rounds",
                            fontSize = 12.sp,
                            color = TextSubtle
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Player Roles Roster
            Text(
                text = "Round recap",
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePure)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    players.forEach { player ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(SurfaceContainerLow),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "%02d".format(player.id),
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = player.name,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 15.sp),
                                    color = TextPrimary
                                )
                            }

                            RoleBadge(role = player.role, isEliminated = player.isEliminated)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Revealed Secret Words
            if (wordPair != null) {
                Text(
                    text = "Secret Words Revealed",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfacePure)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Civilian Word",
                                fontSize = 13.sp,
                                color = TextMuted
                            )
                            Text(
                                text = wordPair.civilianWord,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoleCivilian
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Undercover Word",
                                fontSize = 13.sp,
                                color = TextMuted
                            )
                            Text(
                                text = wordPair.undercoverWord,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoleUndercover
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Mr White",
                                fontSize = 13.sp,
                                color = TextMuted
                            )
                            Text(
                                text = "No Secret Word",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = RoleMrWhite
                            )
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
fun RoundRecapVictoryScreenPreview() {
    MrWhiteTheme {
        RoundRecapVictoryScreen(
            winner = Winner.CIVILIANS,
            players = listOf(
                Player(1, "Player 1", Role.CIVILIAN, "Cappuccino", isEliminated = true),
                Player(2, "Player 2", Role.CIVILIAN, "Cappuccino"),
                Player(3, "Player 3", Role.UNDERCOVER, "Espresso", isEliminated = true),
                Player(4, "Player 4", Role.CIVILIAN, "Cappuccino"),
                Player(5, "Player 5", Role.MR_WHITE, null, isEliminated = true)
            ),
            wordPair = WordPair("Cappuccino", "Espresso", Category.FOOD_AND_DRINK),
            roundNumber = 4,
            onPlayAgain = {}
        )
    }
}
