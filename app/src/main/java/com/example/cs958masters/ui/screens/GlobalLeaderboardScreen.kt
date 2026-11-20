package com.example.cs958masters.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cs958masters.R
import com.example.cs958masters.data.Game
import com.example.cs958masters.data.PlayedGame
import com.example.cs958masters.ui.AppScreen
import com.example.cs958masters.ui.AppViewModel
import com.example.cs958masters.ui.theme.CS958MastersTheme
import com.example.cs958masters.utils.UtilMethods


@Composable
fun GlobalLeaderboardScreen(viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier) {
    val gameState by viewModel.uiState.collectAsState()

    val outerPadding = UtilMethods.getNarrowestScreen(LocalConfiguration.current) / 20
    val context = LocalContext.current

    var isGlobal by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
        ){
            Button(
                colors = ButtonDefaults.buttonColors(Color.Cyan),
                onClick = { isGlobal = true },
                modifier = modifier
                    .padding(outerPadding)
            ){
                Text(
                    text = "GLOBAL"
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(Color.LightGray),
                onClick = {
                    if(gameState.loggedInPlayer == null){
                        Toast.makeText(context, "Not logged in.", Toast.LENGTH_LONG).show()
                    } else {
                        isGlobal = false
                    }
                },
                modifier = modifier
                    .padding(outerPadding)
            ){
                Text(
                    text = "PERSONAL"
                )
            }
        }
        if(isGlobal){
        GlobalLeaderboard(
            viewModel = viewModel,
            navHostController = navHostController
        )
        } else {
            PersonalLeaderboard(
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
    }
}

@Composable
fun GlobalLeaderboard(viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier){
    val gameState by viewModel.uiState.collectAsState()
    val outerPadding = UtilMethods.getNarrowestScreen(LocalConfiguration.current) / 20
    var listNumber : Int = 0

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = outerPadding)
    ) {
        Text(
            text = stringResource(R.string.leaderboard_global, gameState.leaderboardGame.name)
        )
    }
    LazyColumn(modifier = modifier) {
        items(viewModel.getGlobalLeaderboard(gameState.leaderboardGame.gameId)) { playedGame ->
            listNumber++
            Box(
                modifier
                    .padding(top = outerPadding, start = outerPadding, end = outerPadding)
            ) {
                GlobalLeaderboardCard(
                    playedGame,
                    listNumber,
                    viewModel
                )
            }
        }
    }
}

@Composable
fun PersonalLeaderboard(viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier){
    val gameState by viewModel.uiState.collectAsState()
    val outerPadding = UtilMethods.getNarrowestScreen(LocalConfiguration.current) / 20
    var listNumber : Int = 0

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = outerPadding)
    ) {
        Text(
            text = stringResource(R.string.leaderboard_personal, gameState.leaderboardGame.name)
        )
    }
    LazyColumn(modifier = modifier) {
        items(viewModel.getPersonalLeaderboard(gameState.leaderboardGame.gameId, gameState.loggedInPlayer!!)) { playedGame ->
            listNumber++
            Box(
                modifier
                    .padding(top = outerPadding, start = outerPadding, end = outerPadding)
            ) {
                GlobalLeaderboardCard(
                    playedGame,
                    listNumber,
                    viewModel
                )
            }
        }
    }
}

@Composable
fun GlobalLeaderboardCard(playedGame: PlayedGame, listNumber: Int, viewModel: AppViewModel, modifier: Modifier = Modifier){
    val cardPadding = UtilMethods.getNarrowestScreen(LocalConfiguration.current) / 80

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(cardPadding)
    ){
        Text(text = stringResource(R.string.leaderboard_global_card_start, listNumber, viewModel.getPlayerName(playedGame.playerId)))
        Text(text = stringResource(R.string.leaderboard_global_card_end, playedGame.endingScore))
    }
}

@Preview(showBackground = true)
@Composable
fun GlobalLeaderboardScreenPreview() {
    CS958MastersTheme {
        GlobalLeaderboardScreen(
            viewModel = viewModel(),
            modifier = Modifier,
            navHostController = rememberNavController()
        )
    }
}