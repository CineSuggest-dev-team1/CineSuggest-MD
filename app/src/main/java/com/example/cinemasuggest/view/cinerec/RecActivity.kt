package com.example.cinemasuggest.view.cinerec

import com.example.cinemasuggest.utils.OnSwipeTouchListener
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemasuggest.R
import com.example.cinemasuggest.databinding.ActivityRecBinding
import com.example.cinemasuggest.view.home.HomeActivity
import com.example.cinemasuggest.view.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the selected item in the bottom navigation
        binding.bottomNavigationView.selectedItemId = R.id.bottom_recommendation

        // Set up bottom navigation listener
        setupBottomNavigation()

        // Set up swipe gestures
        binding.root.setOnTouchListener(object : OnSwipeTouchListener(this@RecActivity) {
            override fun onSwipeRight() {
                val intent = Intent(this@RecActivity, HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
        })
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@RecActivity, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_recommendation -> {
                    // Stay on the RecActivity
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_search -> {
                    val intent = Intent(this@RecActivity, SearchActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_settings -> {
                    // Add intent for settings activity if exists
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }
}
