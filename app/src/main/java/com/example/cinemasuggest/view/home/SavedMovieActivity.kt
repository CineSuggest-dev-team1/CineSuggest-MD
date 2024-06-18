package com.example.cinemasuggest.view.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cinemasuggest.data.room.AppDatabase
import com.example.cinemasuggest.databinding.ActivitySavedMovieBinding
import com.example.cinemasuggest.data.adapter.SavedMoviesAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedMovieBinding
    private lateinit var db: AppDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "cinema-suggest-db")
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()

        auth = FirebaseAuth.getInstance()

        binding.rvSavedMovies.layoutManager = LinearLayoutManager(this)

        loadSavedMovies()

        binding.tvBack.setOnClickListener {
            finish()
        }

        binding.deleteButton.setOnClickListener {
            deleteAllMovies()
        }
    }

    private fun loadSavedMovies() {
        showProgressBar()
        val userId = auth.currentUser?.uid ?: return
        lifecycleScope.launch {
            val savedMovies = withContext(Dispatchers.IO) {
                db.userMovieDao().getAllMovies(userId)
            }
            binding.rvSavedMovies.adapter = SavedMoviesAdapter(savedMovies)
            hideProgressBar()
        }
    }

    private fun deleteAllMovies() {
        showProgressBar()
        val userId = auth.currentUser?.uid ?: return
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.userMovieDao().deleteAllMovies(userId)
            }
            withContext(Dispatchers.Main) {
                Snackbar.make(binding.root, "All movies deleted", Snackbar.LENGTH_SHORT).show()
                loadSavedMovies()
                hideProgressBar()
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}
