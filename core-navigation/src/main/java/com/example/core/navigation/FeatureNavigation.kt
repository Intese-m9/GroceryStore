package com.example.core.navigation

import android.content.Context
import android.content.Intent

/**
 * Контракты для перехода на разные экраны
 */
interface FeatureNavigation {
    fun navigateToGroceryStore(context: Context): Intent
    fun navigateToUserList(context:Context): Intent
}
