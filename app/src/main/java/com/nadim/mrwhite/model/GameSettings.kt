package com.nadim.mrwhite.model

enum class Difficulty {
    EASY, MEDIUM, HARD
}

data class GameSettings(
    val playerCount: Int = 5,
    val undercoverCount: Int = 1,
    val mrWhiteCount: Int = 0,
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val selectedCategory: Category = Category.ALL_CATEGORIES,
    val playerNames: List<String> = emptyList(),
    val discussionTimerSeconds: Int = 0 // 0 means Off
) {
    val civilianCount: Int
        get() = (playerCount - undercoverCount - mrWhiteCount).coerceAtLeast(0)
}
