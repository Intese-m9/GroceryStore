package com.example.feature_xml_userlist.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.feature_xml_userlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textTittle.text = "bla_bla_bla"
    }
}