package com.example.cs958masters.data

import android.content.Context

class AppRepository() {
    val gameList : List<Game> = listOf(
        Game(1, "Game 1", 0.6, 1.0, 30, 25.0, 250.0, true),
        Game(2, "Game 1, no info", 0.6, 1.0, 30, 25.0, 250.0, false),
        Game(3, "Game 2", 0.7, 1.0, 30, 25.0, 250.0, true),
        Game(4, "Game 3", 0.8, 1.0, 30, 25.0, 250.0, true)
    )

    val playedGamesList : List<PlayedGame> = listOf(
        PlayedGame(1, 1, 1, 20.0),
        PlayedGame(2, 1, 1, 100.0),
        PlayedGame(3, 1, 1, 77.0),
        PlayedGame(4, 1, 1, 250.0),
        PlayedGame(5, 2, 1, 45.0),
        PlayedGame(6, 2, 1, 212.0),
        PlayedGame(7, 2, 1, 250.0),
        PlayedGame(8, 1, 2, 40.0),
        PlayedGame(9, 1, 2, 89.0),
        PlayedGame(10, 1, 2, 250.0),
        PlayedGame(11, 2, 2, 112.0),
        PlayedGame(12, 2, 2, 250.0),
        PlayedGame(13, 2, 2, 250.0),
        PlayedGame(14, 3, 2, 63.0)
    )

    val playerList : List<Player> = listOf(
        Player(1, "Andras", "badPassword"),
        Player(2, "Alice", "goodPassword")
    )

    fun getGamesList() : List<Game>{
        return gameList
    }

    fun getGame(id: Int) : Game {
        return gameList.get(id-1)
    }

    fun getGlobalLeaderboard(id: Int) : List<PlayedGame> {
        var list = mutableListOf<PlayedGame>()
        for(game: PlayedGame in playedGamesList){
            if(game.gameId == id){
                list.add(game)
            }
        }
        return list
    }

    fun getPersonalLeaderboard(id: Int, player: Player) : List<PlayedGame> {
        var list = mutableListOf<PlayedGame>()
        for(game: PlayedGame in playedGamesList){
            if(game.gameId == id && game.playerId == player.id){
                list.add(game)
            }
        }
        return list
    }

    fun login(username: String, password: String) : Player?{
        for(player : Player in playerList){
            if(username == player.username && password == player.password){
                return player
            }
        }
        return null
    }

    fun makeAccount(username: String, password: String) : Player?{
        for(player : Player in playerList){
            if(username == player.username && password == player.password){
                return null
            }
        }
        return Player(-1, "Test", "Test")
    }

    fun getPlayerName(id: Int) : String? {
        for(player : Player in playerList){
            if(id == player.id){
                return player.username
            }
        }
        return null
    }

    fun getGameAverageScore(id: Int): Double {
        var output = 0.0
        var numberOfGames = 0
        for(playedGame: PlayedGame in playedGamesList){
            if(playedGame.gameId == id){
                output += playedGame.endingScore
                numberOfGames++
            }
        }
        output /= numberOfGames
        return output
    }
}