package com.dicoding.cinesuggest.view.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.dicoding.cinesuggest.data.adapter.SavedMoviesAdapter
import com.dicoding.cinesuggest.data.room.recommendation.UserMovie
import com.dicoding.cinesuggest.databinding.ActivitySavedMovieBinding
import com.dicoding.cinesuggest.data.room.AppDatabase
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
            binding.rvSavedMovies.adapter = SavedMoviesAdapter(savedMovies) { movie ->
                openMovieDetail(movie)
            }
            hideProgressBar()
        }
    }

    private fun deleteAllMovies() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete all saved movies?")
            .setPositiveButton("Yes") { _, _ ->
                performDeleteAllMovies()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun performDeleteAllMovies() {
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

    private fun openMovieDetail(movie: UserMovie) {
        val intent = Intent(this, SavedDetailActivity::class.java).apply {
            putExtra(SavedDetailActivity.EXTRA_POSTER, movie.posterPath)
            putExtra(SavedDetailActivity.EXTRA_TITLE, movie.title)
            putExtra(SavedDetailActivity.EXTRA_ID, movie.movieId)
        }
        startActivity(intent)
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}