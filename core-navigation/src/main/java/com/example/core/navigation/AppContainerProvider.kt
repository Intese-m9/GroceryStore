package com.example.core.navigation

/**
 * Единый интерфейс для доступа ко ВСЕМ общим зависимостям приложения.
 * Добавляйте сюда новые зависимости по мере необходимости.
 */
interface AppContainerProvider {
    val navigator: FeatureNavigation
}