package com.example.grocerystore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grocerystore.di.AppContainer


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startIntent = AppContainer().navigator.navigateToGroceryStore(this)
        startActivity(startIntent)
        finish()
    }
}