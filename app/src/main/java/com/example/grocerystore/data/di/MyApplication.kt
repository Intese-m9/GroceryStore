package com.example.grocerystore.data.di

import android.app.Application

class MyApplication: Application() {
    val appContainer = AppContainer()
}