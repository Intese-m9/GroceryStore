package com.example.core.navigation

import android.content.Context

private val Context.appContainer: AppContainerProvider? get() = applicationContext as? AppContainerProvider

/**
 * Удобный хелпер для навигации.
 * Теперь доступен в любой фиче, которая подключила :core-navigation
 */
fun Context.navigateToUserList() {
    val navigator = this.appContainer?.navigator
    navigator?.navigateToUserList(this)?.let { intent ->
        this.startActivity(intent)
    } ?: run {
        // Тут можно логировать ошибку: "Навигатор не найден"
    }
}