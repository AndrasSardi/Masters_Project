package com.example.cs958masters.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cs958masters.ui.theme.CS958MastersTheme

@Composable
fun HelpScreen(navHostController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ){
        Text(text = "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, " +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. \n")

        Text(text = "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
                "Excepteur sint occaecat cupidatat non proident, " +
                "sunt in culpa qui officia deserunt mollit anim id est laborum.")
    }
}


@Preview(showBackground = true)
@Composable
fun HelpScreenPreview() {
    CS958MastersTheme {
        HelpScreen(
            modifier = Modifier,
            navHostController = rememberNavController()
        )
    }
}