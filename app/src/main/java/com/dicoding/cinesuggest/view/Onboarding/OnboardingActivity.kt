package com.dicoding.cinesuggest.view.Onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.cinesuggest.data.adapter.Onboarding.OnBoardingAdapter
import com.dicoding.cinesuggest.databinding.ActivityOnboardingBinding
import com.dicoding.cinesuggest.view.Main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onboardVp.adapter = OnBoardingAdapter(this.supportFragmentManager, lifecycle)

        auth = FirebaseAuth.getInstance()

        // Check if user is logged in
        if (auth.currentUser != null) {
            // User is logged in, redirect to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
    }
}