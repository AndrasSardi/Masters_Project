package com.example.cs958masters

import android.app.Application
import com.example.cs958masters.data.AppContainer
import com.example.cs958masters.data.DefaultAppContainer

class CoinFlipApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}