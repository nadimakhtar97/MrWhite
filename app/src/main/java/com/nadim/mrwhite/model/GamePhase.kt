package com.nadim.mrwhite.model

enum class GamePhase {
    SETUP,
    PASS_ROLE,
    DISCUSSION,
    VOTING,
    MR_WHITE_GUESS,
    RECAP
}

enum class Winner {
    CIVILIANS,
    INFILTRATORS,
    MR_WHITE_GUESS_WIN
}
