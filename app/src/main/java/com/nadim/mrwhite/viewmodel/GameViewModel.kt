package com.nadim.mrwhite.viewmodel

import androidx.lifecycle.ViewModel
import com.nadim.mrwhite.data.WordRepository
import com.nadim.mrwhite.model.Category
import com.nadim.mrwhite.model.Difficulty
import com.nadim.mrwhite.model.GamePhase
import com.nadim.mrwhite.model.GameSettings
import com.nadim.mrwhite.model.Player
import com.nadim.mrwhite.model.Role
import com.nadim.mrwhite.model.Winner
import com.nadim.mrwhite.model.WordPair
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameUiState(
    val gamePhase: GamePhase = GamePhase.SETUP,
    val gameSettings: GameSettings = GameSettings(),
    val players: List<Player> = emptyList(),
    val currentWordPair: WordPair? = null,
    val activePlayerIndex: Int = 0,
    val roundNumber: Int = 1,
    val winner: Winner? = null,
    val selectedPlayerToEliminate: Player? = null,
    val mrWhiteGuessingPlayer: Player? = null,
    val mrWhiteGuessInput: String = "",
    val mrWhiteGuessError: String? = null,
    val showEndGameDialog: Boolean = false
)

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun updatePlayerCount(count: Int) {
        val newCount = count.coerceIn(3, 12)
        _uiState.update { state ->
            var uc = state.gameSettings.undercoverCount
            var mw = state.gameSettings.mrWhiteCount
            if (uc + mw >= newCount) {
                uc = 1
                mw = 0
            }
            state.copy(
                gameSettings = state.gameSettings.copy(
                    playerCount = newCount,
                    undercoverCount = uc,
                    mrWhiteCount = mw
                )
            )
        }
    }

    fun updateUndercoverCount(count: Int) {
        val maxUc = (_uiState.value.gameSettings.playerCount - _uiState.value.gameSettings.mrWhiteCount - 1).coerceAtLeast(1)
        val newUc = count.coerceIn(1, maxUc)
        _uiState.update { state ->
            state.copy(
                gameSettings = state.gameSettings.copy(undercoverCount = newUc)
            )
        }
    }

    fun updateMrWhiteCount(count: Int) {
        val maxMw = (_uiState.value.gameSettings.playerCount - _uiState.value.gameSettings.undercoverCount - 1).coerceAtLeast(0)
        val newMw = count.coerceIn(0, maxMw)
        _uiState.update { state ->
            state.copy(
                gameSettings = state.gameSettings.copy(mrWhiteCount = newMw)
            )
        }
    }

    fun updateDifficulty(difficulty: Difficulty) {
        _uiState.update { state ->
            state.copy(
                gameSettings = state.gameSettings.copy(difficulty = difficulty)
            )
        }
    }

    fun updateCategory(category: Category) {
        _uiState.update { state ->
            state.copy(
                gameSettings = state.gameSettings.copy(selectedCategory = category)
            )
        }
    }

    fun updateDiscussionTimer(seconds: Int) {
        _uiState.update { state ->
            state.copy(
                gameSettings = state.gameSettings.copy(discussionTimerSeconds = seconds)
            )
        }
    }

    fun startRound() {
        val settings = _uiState.value.gameSettings
        val wordPair = WordRepository.getRandomWordPair(settings.selectedCategory, settings.difficulty)

        val roles = mutableListOf<Role>()
        repeat(settings.mrWhiteCount) { roles.add(Role.MR_WHITE) }
        repeat(settings.undercoverCount) { roles.add(Role.UNDERCOVER) }
        repeat(settings.civilianCount) { roles.add(Role.CIVILIAN) }
        roles.shuffle()

        val players = roles.mapIndexed { index, role ->
            val pName = settings.playerNames.getOrNull(index) ?: "Player ${index + 1}"
            val word = when (role) {
                Role.CIVILIAN -> wordPair.civilianWord
                Role.UNDERCOVER -> wordPair.undercoverWord
                Role.MR_WHITE -> null
            }
            Player(
                id = index + 1,
                name = pName,
                role = role,
                secretWord = word
            )
        }

        _uiState.update { state ->
            state.copy(
                gamePhase = GamePhase.PASS_ROLE,
                players = players,
                currentWordPair = wordPair,
                activePlayerIndex = 0,
                roundNumber = 1,
                winner = null,
                selectedPlayerToEliminate = null,
                mrWhiteGuessingPlayer = null,
                mrWhiteGuessInput = "",
                mrWhiteGuessError = null,
                showEndGameDialog = false
            )
        }
    }

    fun nextPassPlayer() {
        val nextIndex = _uiState.value.activePlayerIndex + 1
        if (nextIndex < _uiState.value.players.size) {
            _uiState.update { it.copy(activePlayerIndex = nextIndex) }
        } else {
            _uiState.update { it.copy(gamePhase = GamePhase.DISCUSSION) }
        }
    }

    fun togglePlayerSpoken(player: Player) {
        _uiState.update { state ->
            val updatedPlayers = state.players.map { p ->
                if (p.id == player.id) p.copy(hasSpoken = !p.hasSpoken) else p
            }
            state.copy(players = updatedPlayers)
        }
    }

    fun moveToVoting() {
        _uiState.update { it.copy(gamePhase = GamePhase.VOTING) }
    }

    fun backToDiscussion() {
        _uiState.update { it.copy(gamePhase = GamePhase.DISCUSSION) }
    }

    fun selectPlayerToEliminate(player: Player?) {
        _uiState.update { it.copy(selectedPlayerToEliminate = player) }
    }

    fun confirmElimination() {
        val player = _uiState.value.selectedPlayerToEliminate ?: return
        val updatedPlayers = _uiState.value.players.map { p ->
            if (p.id == player.id) p.copy(isEliminated = true) else p
        }

        _uiState.update { state ->
            state.copy(
                players = updatedPlayers,
                selectedPlayerToEliminate = null
            )
        }

        if (player.role == Role.MR_WHITE) {
            _uiState.update { state ->
                state.copy(
                    gamePhase = GamePhase.MR_WHITE_GUESS,
                    mrWhiteGuessingPlayer = player,
                    mrWhiteGuessInput = "",
                    mrWhiteGuessError = null
                )
            }
        } else {
            checkWinConditions(updatedPlayers)
        }
    }

    fun updateMrWhiteGuessInput(input: String) {
        _uiState.update { it.copy(mrWhiteGuessInput = input, mrWhiteGuessError = null) }
    }

    fun submitMrWhiteGuess() {
        val guess = _uiState.value.mrWhiteGuessInput.trim()
        val civilianWord = _uiState.value.currentWordPair?.civilianWord ?: ""

        if (guess.equals(civilianWord, ignoreCase = true)) {
            _uiState.update { state ->
                state.copy(
                    gamePhase = GamePhase.RECAP,
                    winner = Winner.MR_WHITE_GUESS_WIN
                )
            }
        } else {
            _uiState.update { state ->
                state.copy(
                    mrWhiteGuessError = "Incorrect word! Guess was: $guess"
                )
            }
            checkWinConditions(_uiState.value.players)
        }
    }

    fun skipMrWhiteGuess() {
        checkWinConditions(_uiState.value.players)
    }

    private fun checkWinConditions(playersList: List<Player>) {
        val activeCivilians = playersList.filter { !it.isEliminated && it.role == Role.CIVILIAN }
        val activeUndercovers = playersList.filter { !it.isEliminated && it.role == Role.UNDERCOVER }
        val activeMrWhites = playersList.filter { !it.isEliminated && it.role == Role.MR_WHITE }
        val activeInfiltrators = activeUndercovers.size + activeMrWhites.size

        when {
            activeInfiltrators == 0 -> {
                _uiState.update { it.copy(gamePhase = GamePhase.RECAP, winner = Winner.CIVILIANS) }
            }
            activeCivilians.size <= activeInfiltrators -> {
                _uiState.update { it.copy(gamePhase = GamePhase.RECAP, winner = Winner.INFILTRATORS) }
            }
            else -> {
                // Continue game -> advance round
                _uiState.update { it.copy(gamePhase = GamePhase.DISCUSSION, roundNumber = it.roundNumber + 1) }
            }
        }
    }

    fun setShowEndGameDialog(show: Boolean) {
        _uiState.update { it.copy(showEndGameDialog = show) }
    }

    fun endGamePrematurely() {
        _uiState.update { state ->
            state.copy(
                gamePhase = GamePhase.RECAP,
                winner = Winner.INFILTRATORS,
                showEndGameDialog = false
            )
        }
    }

    fun restartSetup() {
        _uiState.update { GameUiState() }
    }
}
