package com.example.cs958masters.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cs958masters.R
import com.example.cs958masters.ui.screens.AccountScreen
import com.example.cs958masters.ui.screens.GameOverScreen
import com.example.cs958masters.ui.screens.StartScreen
import com.example.cs958masters.ui.screens.GameScreen
import com.example.cs958masters.ui.screens.GlobalLeaderboardScreen
import com.example.cs958masters.ui.screens.HelpScreen
import com.example.cs958masters.ui.screens.LeaderboardScreen
import com.example.cs958masters.ui.screens.LoginScreen
import com.example.cs958masters.ui.screens.SettingScreen

enum class AppScreen(@StringRes val title: Int) {
    Main(title = R.string.main_screen),
    Game(title = R.string.game_screen),
    GameOver(title = R.string.game_over_screen),
    Login(title = R.string.login_screen),
    Account(title = R.string.account_screen),
    Leaderboard(title = R.string.leaderboard_screen),
    GlobalLeaderboard(title = R.string.global_leaderboard_screen),
    Setting(title = R.string.setting_screen),
    Help(title = R.string.help_screen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenBar(
    viewModel: AppViewModel,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val gameState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu_icon_description)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if(gameState.loggedInPlayer == null){
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.menu_login)) },
                        onClick = { navHostController.navigate(AppScreen.Login.name) }
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.menu_logout)) },
                        onClick = {
                            viewModel.logout()
                            navHostController.navigate(AppScreen.Main.name)
                        }
                    )
                }
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.menu_games)) },
                    onClick = { navHostController.navigate(AppScreen.Main.name) }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.menu_leaderboards)) },
                    onClick = { navHostController.navigate(AppScreen.Leaderboard.name) }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.menu_settings)) },
                    onClick = { navHostController.navigate(AppScreen.Setting.name) }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.menu_help)) },
                    onClick = { navHostController.navigate(AppScreen.Help.name) }
                )
            }
        },
        title = { Text(stringResource(R.string.app_name), color = Color.White) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: AppViewModel = viewModel(factory = AppViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        topBar = {
            MainScreenBar(
                viewModel,
                navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Main.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = AppScreen.Main.name) {
                StartScreen(
                    viewModel = viewModel,
                    modifier = Modifier,
                    navHostController = navController
                )
            }
            composable(route = AppScreen.Game.name) {
                GameScreen(
                    viewModel = viewModel,
                    navHostController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.GameOver.name) {
                GameOverScreen(
                    viewModel = viewModel,
                    navHostController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.Login.name) {
                LoginScreen(
                    viewModel = viewModel,
                    navHostController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.Account.name) {
                AccountScreen(
                    viewModel = viewModel,
                    navHostController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.Leaderboard.name) {
                LeaderboardScreen(
                    viewModel = viewModel,
                    navHostController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.GlobalLeaderboard.name) {
                GlobalLeaderboardScreen(
                    viewModel = viewModel,
                    navHostController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.Setting.name) {
                SettingScreen(
                    navHostController = navController,
                    modifier = Modifier
                )
            }
            composable(route = AppScreen.Help.name) {
                HelpScreen(
                    modifier = Modifier,
                    navHostController = navController
                )
            }
        }
    }
}