package com.example.cs958masters.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cs958masters.ui.theme.CS958MastersTheme

@Composable
fun SettingScreen (navHostController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ){
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Setting 1",
                modifier = modifier.weight(1f)
            )
            Switch(
                checked = false,
                onCheckedChange = {
                    Log.d("TESTING", "SETTING 1 TOGGLED")
                },
                modifier = modifier
            )
        }
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Setting 2",
                modifier = modifier.weight(1f)
            )
            Switch(
                checked = false,
                onCheckedChange = {
                    Log.d("TESTING", "SETTING 2 TOGGLED")
                }
            )
        }
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Setting 3",
                modifier = modifier.weight(1f)
            )
            Switch(
                checked = false,
                onCheckedChange = {
                    Log.d("TESTING", "SETTING 3 TOGGLED")
                },
                modifier = modifier
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    CS958MastersTheme {
        SettingScreen(
            modifier = Modifier,
            navHostController = rememberNavController()
        )
    }
}