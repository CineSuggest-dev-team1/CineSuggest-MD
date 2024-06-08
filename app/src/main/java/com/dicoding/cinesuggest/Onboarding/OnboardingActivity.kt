package com.dicoding.cinesuggest.Onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.cinesuggest.R
import com.dicoding.cinesuggest.adapter.CarouselAdapter
import com.dicoding.cinesuggest.adapter.ViewPagerAdapter

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager)

        viewPager2.adapter = ViewPagerAdapter(this.supportFragmentManager, lifecycle)
    }
}