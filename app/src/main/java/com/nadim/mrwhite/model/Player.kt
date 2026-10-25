package com.nadim.mrwhite.model

data class Player(
    val id: Int,
    val name: String,
    val role: Role,
    val secretWord: String?,
    val isEliminated: Boolean = false,
    val hasSpoken: Boolean = false
)
