package com.example.cs958masters.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.cs958masters.R
import com.example.cs958masters.data.Round
import com.example.cs958masters.ui.AppScreen
import com.example.cs958masters.ui.AppViewModel
import com.example.cs958masters.ui.theme.CS958MastersTheme
import com.example.cs958masters.utils.UtilMethods

@Composable
fun GameOverScreen (viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier) {
    val gameState by viewModel.uiState.collectAsState()

    val smallPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 60
    val mediumPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 30
    val bigPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 20
    val stepSize = UtilMethods.getWidestScreen(LocalConfiguration.current) / 18

    val graphHeight = UtilMethods.getScreenHeight(LocalConfiguration.current) / 3
    val graphTopPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 8
    val graphYPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 10

    val steps = 5

    val pointsData: MutableList<Point> = mutableListOf(Point(0f, gameState.game.startingMoney.toFloat()))

    var totalHeads: Double = 0.0
    var totalTails: Double = 0.0

    for(round : Round in gameState.gameRounds){
        pointsData.add(Point(round.roundNumber.toFloat(), round.moneyAfterRound.toFloat()))
        if(round.headsOutcome){
            totalHeads++
        } else {
            totalTails++
        }
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(stepSize)
        .topPadding(graphTopPadding)
        .steps(pointsData.size - 1)
        .labelData { i -> pointsData[i].x.toInt().toString() }
        .labelAndAxisLinePadding(mediumPadding)
        .build()
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.White)
        .labelAndAxisLinePadding(graphYPadding)
        .labelData { i ->
            // Add yMin to get the negative axis values to the scale
            val yMin = pointsData.minOf { it.y }
            val yMax = pointsData.maxOf { it.y }
            val yScale = (yMax - yMin) / steps
            ((i * yScale) + yMin).formatToSinglePrecision()
        }.build()

    val data = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines()
    )

    Column(
        modifier = modifier
            .verticalScroll(state = ScrollState(0))
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(bigPadding)
        ){
            Text(text = stringResource(R.string.game_over))
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(graphHeight),
                lineChartData = data
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(bigPadding)
        ){
            Text(
                text = stringResource(R.string.game_over_heads, (100.0 / gameState.game.totalRounds * totalHeads))
            )
            Text(
                text = stringResource(R.string.game_over_tails, (100.0 / gameState.game.totalRounds * totalTails))
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(bigPadding)
        ){
            Text(
                text = stringResource(R.string.game_over_final_score, gameState.currentMoney)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(bigPadding)
        ){
            Text(
                text = stringResource(R.string.game_over_global_average, viewModel.getGameAverageScore(gameState.game.gameId))
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bigPadding)
        ){
            Text(text = stringResource(R.string.leaderboard_global, gameState.game.name))
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bigPadding)
        ){
            GlobalLeaderboardCard(
                playedGame = viewModel.getGlobalLeaderboard(gameState.game.gameId)[0],
                listNumber = 1,
                viewModel = viewModel
            )
        }
        if(gameState.loggedInPlayer != null) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bigPadding)
            ) {
                Text(text = stringResource(R.string.leaderboard_personal, gameState.game.name))
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bigPadding)
            ) {
                GlobalLeaderboardCard(
                    playedGame = viewModel.getPersonalLeaderboard(
                        gameState.game.gameId,
                        gameState.loggedInPlayer!!
                    )[0],
                    listNumber = 1 ,
                    viewModel = viewModel
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(bigPadding)
        ){
            Button(
                onClick = {
                    viewModel.updateCurrentRound(1)
                    viewModel.updateCurrentMoney(gameState.game.startingMoney)
                    viewModel.clearGameRounds()

                    navHostController.navigate(AppScreen.Game.name)
                }
            ){
                Text(
                    text = stringResource(R.string.game_over_play_again)
                )
            }
            Button(
                onClick = {
                    navHostController.navigate(AppScreen.Main.name)
                }
            ){
                Text(
                    text = stringResource(R.string.game_over_play_other)
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GameOverScreenPreview() {
    CS958MastersTheme {
        GameOverScreen(
            modifier = Modifier,
            navHostController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}