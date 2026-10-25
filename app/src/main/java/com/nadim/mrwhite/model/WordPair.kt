package com.nadim.mrwhite.model

data class WordPair(
    val civilianWord: String,
    val undercoverWord: String,
    val category: Category,
    val difficulty: Difficulty = Difficulty.MEDIUM
)
