package com.example.cs958masters.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cs958masters.ui.AppScreen
import com.example.cs958masters.R
import com.example.cs958masters.data.Game
import com.example.cs958masters.ui.AppViewModel
import com.example.cs958masters.ui.theme.CS958MastersTheme
import com.example.cs958masters.utils.UtilMethods

@Composable
fun StartScreen(viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier) {
    val outerPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 20

    LazyColumn(modifier = modifier) {
        items(viewModel.getGamesList()) {game ->
            Box (
                modifier.padding(top = outerPadding, start = outerPadding, end = outerPadding)
            ){
                GameCard(
                    game = game,
                    viewModel = viewModel,
                    navHostController = navHostController,
                )
            }
        }
    }
}


@Composable
fun GameCard(game: Game, viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier){
    val innerPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 40
    val smallPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 80

    Card(
        modifier = modifier
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8))
    ){
        Column(
            modifier = modifier
        ){
            Text(
                text = game.name,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(smallPadding)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxHeight(0.2f)
                    .padding(innerPadding)
            ){
                Box(
                    modifier = modifier
                        .background(Color.Magenta)
                        .weight(10f * game.headsChance.toFloat())
                        .fillMaxHeight()
                ){
                    Row(
                        modifier = modifier
                            .align(Alignment.CenterStart)
                            .padding(start = smallPadding)
                    ) {
                        Text(
                            textAlign = TextAlign.Left,
                            text = stringResource(R.string.game_collumn_heads)
                        )
                    }
                }
                Box(
                    modifier = modifier
                        .background(Color.Blue)
                        .weight(10f - (10f * game.headsChance.toFloat()))
                        .fillMaxHeight()
                ){
                    Row(
                        modifier = modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = smallPadding)
                    ) {
                        Text(
                            textAlign = TextAlign.Right,
                            text = stringResource(R.string.game_collumn_tails)
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                Button(
                    onClick = {
                        viewModel.updateLeaderboardGame(game.gameId)
                        navHostController.navigate(AppScreen.GlobalLeaderboard.name)
                    },
                    modifier = modifier
                ){
                    Text(text = stringResource(R.string.game_collumn_leaderboard))
                }
                Button(
                    onClick = {
                        viewModel.updateCurrentMoney(game.startingMoney)
                        viewModel.updateCurrentRound(1)
                        viewModel.updateSelectGame(game.gameId)
                        viewModel.clearGameRounds()

                        navHostController.navigate(AppScreen.Game.name)
                    },
                    modifier = modifier
                ){
                    Text(text = stringResource(R.string.game_collumn_play))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    CS958MastersTheme {
        GameScreen(
            modifier = Modifier,
            navHostController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}