package com.example.cs958masters.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cs958masters.R
import com.example.cs958masters.data.Game
import com.example.cs958masters.ui.AppScreen
import com.example.cs958masters.ui.AppViewModel
import com.example.cs958masters.ui.theme.CS958MastersTheme
import com.example.cs958masters.utils.UtilMethods

@Composable
fun LeaderboardScreen(viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier) {
    val outerPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 20

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.menu_leaderboards)
            )
        }
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
        ) {
            items(viewModel.getGamesList()) { game ->
                Box(
                    modifier
                        .padding(top = outerPadding, start = outerPadding, end = outerPadding)
                ) {
                    LeaderboardCard(
                        game = game,
                        viewModel = viewModel,
                        navHostController = navHostController,
                    )
                }
            }
        }
    }
}

@Composable
fun LeaderboardCard(game: Game, viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier){
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        TextButton(
            onClick = {
                viewModel.updateLeaderboardGame(game.gameId)
                navHostController.navigate(AppScreen.GlobalLeaderboard.name)
            },
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = game.name
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardScreenPreview() {
    CS958MastersTheme {
        LeaderboardScreen(
            viewModel = viewModel(),
            modifier = Modifier,
            navHostController = rememberNavController()
        )
    }
}