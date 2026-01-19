package com.example.grocery_store.presentation.utils.events

import android.content.Context
import com.example.core.navigation.AppContainerProvider
import com.example.core.navigation.FeatureNavigation

/**
 * Единый хелпер для ВСЕХ зависимостей. Прослойка для получения общих зависимостей
 *
 */
object AppDependencies {
    private fun getNavigator(context: Context): FeatureNavigation? {
        return try {
            (context.applicationContext as? AppContainerProvider)?.navigator
        } catch (_: ClassCastException) {
            null
        }
    }

    fun navigateToUserList(context: Context) {
        val navigation = getNavigator(context)
        context.startActivity(navigation?.navigateToUserList(context))
    }

}