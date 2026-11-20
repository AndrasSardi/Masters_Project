package com.example.cs958masters.data

data class Game(val gameId : Int, val name : String, val headsChance : Double, val payout : Double,
                val totalRounds : Int, val startingMoney : Double, val maxPayout : Double,
                val informationShown : Boolean)