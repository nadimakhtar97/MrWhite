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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadim.mrwhite.model.Player
import com.nadim.mrwhite.model.Role
import com.nadim.mrwhite.ui.theme.BgSurface
import com.nadim.mrwhite.ui.theme.MrWhiteTheme
import com.nadim.mrwhite.ui.theme.StateElimination
import com.nadim.mrwhite.ui.theme.SurfaceContainer
import com.nadim.mrwhite.ui.theme.SurfacePure
import com.nadim.mrwhite.ui.theme.TextMuted
import com.nadim.mrwhite.ui.theme.TextPrimary
import com.nadim.mrwhite.ui.theme.TextSubtle

@Composable
fun VotingPhaseScreen(
    roundNumber: Int,
    players: List<Player>,
    selectedPlayerToEliminate: Player?,
    onSelectPlayerToEliminate: (Player?) -> Unit,
    onConfirmElimination: () -> Unit,
    onBackToDiscussion: () -> Unit
) {
    BackHandler {
        onBackToDiscussion()
    }

    val activePlayers = players.filter { !it.isEliminated }

    Scaffold(
        topBar = {
            TopHeader(
                title = "Voting",
                onBackClick = onBackToDiscussion
            )
        },
        containerColor = BgSurface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

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
                            .background(StateElimination)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "VOTING PHASE · ACCUSATION",
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
                text = "Who's the infiltrator?",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Debate, point fingers, and choose who to eliminate.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextMuted,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Player List for Elimination
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                activePlayers.forEach { player ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectPlayerToEliminate(player) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfacePure)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp),
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
                                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 17.sp),
                                    color = TextPrimary
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Eliminate →",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StateElimination
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = onBackToDiscussion,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "← Back to discussion",
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    // Modal Confirmation Dialog
    if (selectedPlayerToEliminate != null) {
        ConfirmationModal(
            title = "Eliminate ${selectedPlayerToEliminate.name}?",
            message = "Their role will be revealed to the whole table. If they're Mr White, they get one last guess.",
            confirmText = "Eliminate",
            confirmButtonColor = StateElimination,
            onConfirm = onConfirmElimination,
            onDismiss = { onSelectPlayerToEliminate(null) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VotingPhaseScreenPreview() {
    MrWhiteTheme {
        VotingPhaseScreen(
            roundNumber = 1,
            players = listOf(
                Player(1, "Player 1", Role.CIVILIAN, "Cappuccino"),
                Player(2, "Player 2", Role.CIVILIAN, "Cappuccino"),
                Player(3, "Player 3", Role.UNDERCOVER, "Espresso"),
                Player(4, "Player 4", Role.CIVILIAN, "Cappuccino"),
                Player(5, "Player 5", Role.MR_WHITE, null)
            ),
            selectedPlayerToEliminate = null,
            onSelectPlayerToEliminate = {},
            onConfirmElimination = {},
            onBackToDiscussion = {}
        )
    }
}
