package com.example.cs958masters.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cs958masters.CoinFlipApplication
import com.example.cs958masters.data.AppRepository
import com.example.cs958masters.data.Game
import com.example.cs958masters.data.GameState
import com.example.cs958masters.data.PlayedGame
import com.example.cs958masters.data.Player
import com.example.cs958masters.data.Round
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel(private val repository: AppRepository) : ViewModel(){
    private val _uiState = MutableStateFlow(GameState(game = getGame(1), leaderboardGame = getGame(1)))
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    fun getGamesList() : List<Game>{
        return repository.getGamesList()
    }

    fun getGame(id: Int) : Game {
        return repository.getGame(id)
    }

    fun getGlobalLeaderboard(id: Int) : List<PlayedGame> {
        return repository.getGlobalLeaderboard(id)
    }

    fun getPersonalLeaderboard(id: Int, player: Player) : List<PlayedGame> {
        return repository.getPersonalLeaderboard(id, player)
    }

    fun login(username: String, password: String) : Player? {
        val answer = repository.login(username, password)
        if(answer == null){
            return null
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    loggedInPlayer = answer
                )
            }
            return answer
        }
    }

    fun logout(){
        _uiState.update { currentState ->
            currentState.copy(
                loggedInPlayer = null
            )
        }
    }

    fun makeAccount(username: String, password: String) : Player?{
        val answer = repository.makeAccount(username, password)
        if(answer == null){
            return null
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    loggedInPlayer = answer
                )
            }
            return answer
        }
    }

    fun getPlayerName(id: Int) : String {
        val answer = repository.getPlayerName(id)
        if(answer == null){
            Log.e("ERROR", "GAME WITH NO PLAYER")
            return "Unknown Player"
        } else {
            return answer
        }
    }

    fun getGameAverageScore(id: Int) : Double{
        return repository.getGameAverageScore(id)
    }

    fun updateSelectGame(selected: Int){
        _uiState.update { currentState ->
            currentState.copy(
                game = getGame(selected)
            )
        }
    }

    fun updateLeaderboardGame(selected: Int){
        _uiState.update { currentState ->
            currentState.copy(
                leaderboardGame = getGame(selected)
            )
        }
    }

    fun updateCurrentRound(round: Int){
        _uiState.update { currentState ->
            currentState.copy(
                currentRound = round
            )
        }
    }

    fun updateCurrentMoney(money: Double){
        _uiState.update { currentState ->
            currentState.copy(
                currentMoney = money
            )
        }
    }

    fun addGameRound(round : Round){
        var list = _uiState.value.gameRounds
        list.add(round)
        _uiState.update { currentState ->
            currentState.copy(
                gameRounds = list
            )
        }
    }

    fun clearGameRounds(){
        uiState.value.gameRounds.clear()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as CoinFlipApplication)
                val appRepository = application.container.appRepository
                AppViewModel(repository = appRepository)
            }
        }
    }
}