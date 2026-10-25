package com.nadim.mrwhite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nadim.mrwhite.model.GamePhase
import com.nadim.mrwhite.ui.screens.ConfirmationModal
import com.nadim.mrwhite.ui.screens.DiscussionCluesScreen
import com.nadim.mrwhite.ui.screens.GameSetupScreen
import com.nadim.mrwhite.ui.screens.MrWhiteGuessModal
import com.nadim.mrwhite.ui.screens.PassRoleScreen
import com.nadim.mrwhite.ui.screens.RoundRecapVictoryScreen
import com.nadim.mrwhite.ui.screens.VotingPhaseScreen
import com.nadim.mrwhite.ui.theme.BgSurface
import com.nadim.mrwhite.ui.theme.MrWhiteTheme
import com.nadim.mrwhite.viewmodel.GameViewModel

object Routes {
    const val SETUP = "setup"
    const val PASS_ROLE = "pass_role"
    const val DISCUSSION = "discussion"
    const val VOTING = "voting"
    const val RECAP = "recap"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MrWhiteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BgSurface
                ) {
                    MrWhiteApp()
                }
            }
        }
    }
}

@Composable
fun MrWhiteApp(
    viewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Synchronize GamePhase state with Jetpack Navigation Controller
    LaunchedEffect(uiState.gamePhase) {
        val targetRoute = when (uiState.gamePhase) {
            GamePhase.SETUP -> Routes.SETUP
            GamePhase.PASS_ROLE -> Routes.PASS_ROLE
            GamePhase.DISCUSSION -> Routes.DISCUSSION
            GamePhase.VOTING -> Routes.VOTING
            GamePhase.MR_WHITE_GUESS -> Routes.VOTING
            GamePhase.RECAP -> Routes.RECAP
        }

        if (currentRoute != targetRoute) {
            if (targetRoute == Routes.SETUP) {
                navController.navigate(Routes.SETUP) {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                navController.navigate(targetRoute) {
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.SETUP,
        enterTransition = { fadeIn(tween(300)) + slideInHorizontally(tween(300)) { it / 3 } },
        exitTransition = { fadeOut(tween(300)) + slideOutHorizontally(tween(300)) { -it / 3 } },
        popEnterTransition = { fadeIn(tween(300)) + slideInHorizontally(tween(300)) { -it / 3 } },
        popExitTransition = { fadeOut(tween(300)) + slideOutHorizontally(tween(300)) { it / 3 } }
    ) {
        composable(Routes.SETUP) {
            GameSetupScreen(
                settings = uiState.gameSettings,
                onPlayerCountChange = viewModel::updatePlayerCount,
                onUndercoverCountChange = viewModel::updateUndercoverCount,
                onMrWhiteCountChange = viewModel::updateMrWhiteCount,
                onDifficultyChange = viewModel::updateDifficulty,
                onCategoryChange = viewModel::updateCategory,
                onTimerChange = viewModel::updateDiscussionTimer,
                onStartRound = viewModel::startRound
            )
        }

        composable(Routes.PASS_ROLE) {
            val currentPlayer = uiState.players.getOrNull(uiState.activePlayerIndex)
            if (currentPlayer != null) {
                PassRoleScreen(
                    player = currentPlayer,
                    playerIndex = uiState.activePlayerIndex,
                    totalPlayers = uiState.players.size,
                    onNextPlayer = viewModel::nextPassPlayer,
                    onCancel = { viewModel.setShowEndGameDialog(true) }
                )
            }
        }

        composable(Routes.DISCUSSION) {
            DiscussionCluesScreen(
                roundNumber = uiState.roundNumber,
                players = uiState.players,
                onToggleSpoken = viewModel::togglePlayerSpoken,
                onMoveToVote = viewModel::moveToVoting,
                onEndGame = { viewModel.setShowEndGameDialog(true) }
            )
        }

        composable(Routes.VOTING) {
            VotingPhaseScreen(
                roundNumber = uiState.roundNumber,
                players = uiState.players,
                selectedPlayerToEliminate = uiState.selectedPlayerToEliminate,
                onSelectPlayerToEliminate = viewModel::selectPlayerToEliminate,
                onConfirmElimination = viewModel::confirmElimination,
                onBackToDiscussion = viewModel::backToDiscussion
            )

            if (uiState.gamePhase == GamePhase.MR_WHITE_GUESS) {
                val guessingPlayer = uiState.mrWhiteGuessingPlayer
                if (guessingPlayer != null) {
                    MrWhiteGuessModal(
                        playerName = guessingPlayer.name,
                        guessInput = uiState.mrWhiteGuessInput,
                        onGuessInputChange = viewModel::updateMrWhiteGuessInput,
                        onSubmitGuess = viewModel::submitMrWhiteGuess,
                        onSkip = viewModel::skipMrWhiteGuess,
                        errorMessage = uiState.mrWhiteGuessError
                    )
                }
            }
        }

        composable(Routes.RECAP) {
            RoundRecapVictoryScreen(
                winner = uiState.winner,
                players = uiState.players,
                wordPair = uiState.currentWordPair,
                roundNumber = uiState.roundNumber,
                onPlayAgain = viewModel::restartSetup
            )
        }
    }

    if (uiState.showEndGameDialog) {
        ConfirmationModal(
            title = "Cancel Match?",
            message = "All active roles will be exposed and current progress reset.",
            confirmText = "Confirm End Game",
            onConfirm = viewModel::endGamePrematurely,
            onDismiss = { viewModel.setShowEndGameDialog(false) }
        )
    }
}
