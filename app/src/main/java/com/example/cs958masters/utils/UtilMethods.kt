package com.example.cs958masters.utils

import android.content.res.Configuration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

abstract class UtilMethods {
    companion object utils{
        fun getScreenWidth(configuration: Configuration): Dp {
            return configuration.screenWidthDp.dp
        }

        fun getScreenHeight(configuration: Configuration): Dp {
            return configuration.screenHeightDp.dp
        }

        fun getNarrowestScreen(configuration: Configuration): Dp {
            if(configuration.screenHeightDp.dp > configuration.screenWidthDp.dp){
                return configuration.screenWidthDp.dp
            } else {
                return configuration.screenHeightDp.dp
            }
        }

        fun getWidestScreen(configuration: Configuration): Dp {
            if(configuration.screenHeightDp.dp < configuration.screenWidthDp.dp){
                return configuration.screenWidthDp.dp
            } else {
                return configuration.screenHeightDp.dp
            }
        }
    }
}