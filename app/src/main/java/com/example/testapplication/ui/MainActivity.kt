package com.example.testapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapplication.R
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.utils.extensions.marginUpdate
import com.example.testapplication.utils.extensions.setTransparentStatusBar

class MainActivity : AppCompatActivity() {

      private var _binding: ActivityMainBinding? = null
        private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_main)
        setContentView(binding.root)
        updateUI()
    }

    private fun updateUI(){
        setTransparentStatusBar()
        binding.master.marginUpdate()

    }
}