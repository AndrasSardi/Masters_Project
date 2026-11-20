package com.example.cs958masters.data

import com.example.cs958masters.ui.AppViewModel

data class GameState(
    var game: Game,
    var gameRounds : MutableList<Round> = mutableListOf(),
    var currentRound : Int = 0,
    var currentMoney : Double = -1.0,

    var loggedInPlayer : Player? = null,

    var leaderboardGame: Game
)
