package com.example.grocerystore.di

import com.example.core.navigation.FeatureNavigation
import com.example.grocerystore.navigation.AppNavigator

class AppContainer() {
    val navigator: FeatureNavigation by lazy {
        AppNavigator()
    }
}