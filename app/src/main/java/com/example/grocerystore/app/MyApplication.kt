package com.example.grocerystore.app

import android.app.Application
import com.example.core.navigation.GlobalNavigator
import com.example.grocerystore.navigation.AppNavigator

class MyApplication: Application() {
    override fun onCreate() {
        GlobalNavigator.navigator = AppNavigator()
        super.onCreate()
    }
}