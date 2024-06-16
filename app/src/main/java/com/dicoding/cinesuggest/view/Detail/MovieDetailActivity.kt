package com.dicoding.cinesuggest.view.Detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.cinesuggest.databinding.ActivityMovieDetailBinding
import com.dicoding.cinesuggest.view.Home.HomeActivity

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibBack.setOnClickListener {
            val intent = Intent(this@MovieDetailActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}