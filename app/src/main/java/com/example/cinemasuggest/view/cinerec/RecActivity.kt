package com.example.cinemasuggest.view.cinerec

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinemasuggest.R
import com.example.cinemasuggest.data.adapter.RecommendationsAdapter
import com.example.cinemasuggest.data.response.RecommendationResponseItem
import com.example.cinemasuggest.data.retrofit.ApiConfig
import com.example.cinemasuggest.databinding.ActivityRecBinding
import com.example.cinemasuggest.view.home.HomeActivity
import com.example.cinemasuggest.view.search.SearchActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedGenres = intent.getStringExtra("selectedGenres")
        val selectedMood = intent.getStringExtra("selectedMood")

        // Log the genres and mood to verify they are passed correctly
        Log.d("RecActivity", "Selected Genres: $selectedGenres")
        Log.d("RecActivity", "Selected Mood: $selectedMood")

        binding.rvRecommended.layoutManager = LinearLayoutManager(this)
        getRecommendations(selectedGenres, selectedMood)

        // Set the selected item in the bottom navigation
        binding.bottomNavigationView.selectedItemId = R.id.bottom_recommendation

        // Set up bottom navigation listener
        setupBottomNavigation()
    }

    private fun getRecommendations(genres: String?, mood: String?) {
        showLoading(true)
        val call = ApiConfig.apiService.getRecommendations(genres ?: "", mood ?: "")
        call.enqueue(object : Callback<List<RecommendationResponseItem>> {
            override fun onResponse(
                call: Call<List<RecommendationResponseItem>>,
                response: Response<List<RecommendationResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val recommendations = response.body()
                    Log.d("RecActivity", "Recommendations received: ${recommendations?.size} items")
                    binding.rvRecommended.adapter = recommendations?.let { RecommendationsAdapter(it) }
                } else {
                    Toast.makeText(this@RecActivity, "Failed to get recommendations.", Toast.LENGTH_SHORT).show()
                    Log.e("RecActivity", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<RecommendationResponseItem>>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@RecActivity, "An error occurred.", Toast.LENGTH_SHORT).show()
                Log.e("RecActivity", "onFailure: ", t)
            }
        })
    }

    private fun showLoading(show: Boolean) {
        binding.loadingOverlay.visibility = if (show) View.VISIBLE else View.GONE
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    val intent = Intent(this@RecActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_recommendation -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bottom_search -> {
                    val intent = Intent(this@RecActivity, SearchActivity::class.java)
                    startActivity(intent)
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
