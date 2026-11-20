package com.example.cs958masters.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.yml.charts.axis.AxisConfig
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.axis.Gravity
import co.yml.charts.common.model.Point
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.GroupBarChartData
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
fun GameScreen(viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier) {
    val gameState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var headsSelected by remember { mutableStateOf(true) }
    var sliderPosition by remember { mutableFloatStateOf(0f) }

    val smallPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 60
    val bigPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 20
    val coinSize = UtilMethods.getWidestScreen(LocalConfiguration.current) / 8

    val graphHeight = UtilMethods.getScreenHeight(LocalConfiguration.current) / 3
    val graphBarWidth = UtilMethods.getScreenWidth(LocalConfiguration.current) / 8

    val barData : MutableList<BarData> = mutableListOf(
        BarData(
            Point(0f, gameState.game.startingMoney.toFloat()), Color.Blue,
                    stringResource(R.string.game_stats_graph_index, gameState.game.startingMoney)
        )
    )
    for(round : Round in gameState.gameRounds){
        barData.add(BarData(
            Point(round.roundNumber.toFloat(), round.moneyAfterRound.toFloat()),
            Color.Blue, stringResource(R.string.game_stats_graph_index, round.moneyAfterRound))
        )
    }

    val xAxisData = AxisData.Builder()
        .steps(barData.size - 1)
        .backgroundColor(Color.Red)
        .bottomPadding(smallPadding)
        .startDrawPadding(graphBarWidth)
        .labelData { index -> barData[index].label }
        .build()

    val barChartData = BarChartData(
        chartData = barData,
        xAxisData = xAxisData,
        horizontalExtraSpace = graphBarWidth,
        barStyle = BarStyle(
            paddingBetweenBars = smallPadding,
            barWidth = graphBarWidth
        ),
    )

    Column(
        modifier = modifier
            .verticalScroll(state = ScrollState(0))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(smallPadding)
        ) {
            Text(
                text = gameState.game.name
            )
            Text(
                text = stringResource(
                    R.string.game_stats_rounds,
                    gameState.currentRound,
                    gameState.game.totalRounds
                )
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.game_stats_current_money, gameState.currentMoney)
            )
        }
        if (gameState.game.informationShown) {
            Row(
                modifier = modifier
                    .padding(smallPadding)
            ) {
                BarChart(
                    modifier = Modifier
                        .height(graphHeight),
                    barChartData = barChartData
                )
            }
            LazyRow(
                modifier = modifier
                    .border(width = 1.dp, color = Color.Black, shape = RectangleShape)
            ) {
                items(gameState.gameRounds) { round ->
                    Box(
                        modifier = modifier
                            .width(coinSize)
                            .height(coinSize)
                    ) {
                        CoinCard(round, coinSize)
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .padding(smallPadding)
        ){
            Button(
                onClick = {
                    headsSelected = true
                },
                colors = ButtonDefaults.buttonColors(
                    if(headsSelected){
                        Color.Cyan
                    }
                    else{
                        Color.LightGray
                    }
                ),
                modifier = modifier.padding(start = bigPadding, end = bigPadding)
            ){
                Text(
                    text = stringResource(R.string.game_stats_heads)
                )
            }
            Button(
                onClick = {
                    headsSelected = false
                },
                colors = ButtonDefaults.buttonColors(
                    if(!headsSelected){
                        Color.Cyan
                    }
                    else{
                        Color.LightGray
                    }
                ),
                modifier = modifier.padding(start = bigPadding, end = bigPadding)
            ){
                Text(
                    text = stringResource(R.string.game_stats_tails)
                )
            }
        }
        Row(
            modifier = modifier
        ){
            Text(
                text = stringResource(R.string.game_stats_money_to_bet, sliderPosition * gameState.currentMoney)
            )
        }
        Row(
            modifier = modifier
                .padding(smallPadding)
        ){
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    val headsResult = gameState.game.headsChance > Math.random()

                    val moneyEarned = if (headsSelected == headsResult) {
                        sliderPosition * gameState.currentMoney * gameState.game.payout
                    } else {
                        sliderPosition * gameState.currentMoney * -1.0
                    }

                    val moneyAfterRound = if((gameState.currentMoney + moneyEarned) > gameState.game.maxPayout){
                        gameState.game.maxPayout
                    } else {
                        gameState.currentMoney + moneyEarned
                    }

                    viewModel.addGameRound(
                        Round(
                            gameState.currentRound,
                            headsSelected,
                            headsResult,
                            moneyEarned,
                            moneyAfterRound
                        )
                    )
                    viewModel.updateCurrentMoney(moneyAfterRound)
                    viewModel.updateCurrentRound(gameState.currentRound + 1)

                    if(gameState.currentRound == gameState.game.totalRounds){
                        navHostController.navigate(AppScreen.GameOver.name)
                        /*TODO insert game into database if logged in*/
                    }
                }

            ) {
                Text(
                    text = stringResource(R.string.game_stats_bet)
                )
            }
        }
    }
}

@Composable
fun CoinCard(round: Round, coinSize : Dp, modifier: Modifier = Modifier) {
    val innerPadding = UtilMethods.getScreenWidth(LocalConfiguration.current) / 60

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxSize()
                .background(
                    if(round.headsOutcome == round.HeadsBetOn){
                        Color.Green
                    } else {
                        Color.Red
                    },
                    RoundedCornerShape(8)
                )
        ) {
            Text(
                text = if (round.headsOutcome) {
                    stringResource(R.string.game_heads)
                } else {
                    stringResource(R.string.game_tails)
                },
                fontSize = (coinSize.value/1.5).toInt().sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    CS958MastersTheme {
        GameScreen(
            modifier = Modifier,
            navHostController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}