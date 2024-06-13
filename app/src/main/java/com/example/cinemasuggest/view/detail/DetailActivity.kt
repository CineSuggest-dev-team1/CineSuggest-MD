package com.example.cinemasuggest.view.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cinemasuggest.databinding.ActivityDetailBinding
import com.example.cinemasuggest.view.home.HomeActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back
        binding.ibBack.setOnClickListener {
            val intent = Intent(this@DetailActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}