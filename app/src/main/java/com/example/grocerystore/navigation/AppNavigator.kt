package com.example.grocerystore.navigation

import android.content.Context
import android.content.Intent
import com.example.core.navigation.FeatureNavigation


class AppNavigator : FeatureNavigation {
    override fun navigateToGroceryStore(context: Context): Intent {
        return Intent(
            context,
            com.example.grocery_store.presentation.screens.MainActivity::class.java
        )
    }

    override fun navigateToUserList(context: Context): Intent {
        return Intent(
            context,
            com.example.feature_xml_userlist.presentation.screens.MainActivity::class.java
        )
    }

}
