package com.dicoding.cinesuggest.view.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.dicoding.cinesuggest.R
import com.dicoding.cinesuggest.databinding.ActivitySplashBinding
import com.dicoding.cinesuggest.view.Onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_up)

        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Do Nothing
            }

            override fun onAnimationEnd(animation: Animation?) {
                val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Do Nothing
            }
        })

        binding.applogo.startAnimation(scaleAnimation)
        binding.tvVersion.startAnimation(scaleAnimation)
    }
}