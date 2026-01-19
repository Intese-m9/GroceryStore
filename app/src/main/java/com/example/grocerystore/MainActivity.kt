package com.example.grocerystore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.navigation.GlobalNavigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startIntent = GlobalNavigator.navigator.navigateToGroceryStore(this)
        startActivity(startIntent)
        finish()
    }
}