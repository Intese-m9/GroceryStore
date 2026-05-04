package com.example.grocerystore.app

import android.app.Application
import com.example.core.navigation.AppContainerProvider
import com.example.core.navigation.FeatureNavigation
import com.example.grocerystore.di.AppContainer

class MyApplication : Application(), AppContainerProvider {
    private val appContainer = AppContainer()

    override val navigator: FeatureNavigation
        get() = appContainer.navigator
}