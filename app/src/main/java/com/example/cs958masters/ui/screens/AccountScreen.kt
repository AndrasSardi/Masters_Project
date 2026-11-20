package com.example.cs958masters.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cs958masters.R
import com.example.cs958masters.ui.AppScreen
import com.example.cs958masters.ui.AppViewModel
import com.example.cs958masters.ui.theme.CS958MastersTheme
import com.example.cs958masters.utils.UtilMethods

@Composable
fun AccountScreen (viewModel: AppViewModel, navHostController: NavHostController, modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val outerPadding = UtilMethods.getNarrowestScreen(LocalConfiguration.current) / 20
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = modifier
                .fillMaxWidth()
                .padding(outerPadding)
        ){
            Text(
                text = stringResource(R.string.account_top)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(outerPadding)
        ){
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(R.string.login_username)) }
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(outerPadding)
        ){
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.login_password)) },
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = modifier
                .fillMaxWidth()
        ){
            Button(
                onClick = {
                    val answer = viewModel.makeAccount(username, password)
                    if(answer == null){
                        Toast.makeText(context, "Account already exists", Toast.LENGTH_LONG).show()
                    } else {
                        navHostController.navigate(AppScreen.Main.name)
                    }
                },
                modifier = modifier
                    .padding(outerPadding)
            ){
                Text(stringResource(R.string.account_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    CS958MastersTheme {
        AccountScreen(
            modifier = Modifier,
            navHostController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}