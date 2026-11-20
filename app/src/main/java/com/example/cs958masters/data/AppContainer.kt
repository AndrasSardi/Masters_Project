package com.example.cs958masters.data

interface AppContainer {
    val appRepository: AppRepository
}

class DefaultAppContainer : AppContainer {
    override val appRepository: AppRepository = AppRepository()
}