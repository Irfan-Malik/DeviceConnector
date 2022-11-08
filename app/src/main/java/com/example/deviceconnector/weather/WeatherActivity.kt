package com.example.deviceconnector.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.deviceconnector.databinding.WeatherLayoutBinding

class WeatherActivity : AppCompatActivity() {
    lateinit var binding : WeatherLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeatherLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}