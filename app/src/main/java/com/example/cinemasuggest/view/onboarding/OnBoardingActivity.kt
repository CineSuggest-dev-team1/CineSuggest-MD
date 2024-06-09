package com.example.cinemasuggest.view.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemasuggest.R
import com.example.cinemasuggest.data.adapter.OnBoardingAdapter
import com.example.cinemasuggest.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onboardVp.adapter = OnBoardingAdapter(this.supportFragmentManager, lifecycle)
    }
}