package com.example.cinemasuggest.view.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.cinemasuggest.data.room.AppDatabase
import com.example.cinemasuggest.data.room.recommendation.UserMovie
import com.example.cinemasuggest.databinding.ActivityDetailSearchBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class DetailSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSearchBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "cinema-suggest-db")
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()

        val title = intent.getStringExtra("EXTRA_TITLE") ?: "No Title"
        val poster = intent.getStringExtra("EXTRA_POSTER")

        binding.category.text = title
        if (poster != null) {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500$poster").into(binding.imageView)
        }

        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.ibSave.setOnClickListener {
            saveMovie(title, poster)
        }

        binding.button.setOnClickListener {
            showConfirmationDialog(title)
        }
    }

    private fun saveMovie(title: String?, poster: String?) {
        if (title == null || poster == null) {
            Snackbar.make(binding.root, "Failed to save movie", Snackbar.LENGTH_SHORT).show()
            return
        }

        val userMovie = UserMovie(title = title, poster = poster)
        lifecycleScope.launch {
            val existingMovie = withContext(Dispatchers.IO) {
                db.userMovieDao().getMovieByTitle(title)
            }
            if (existingMovie != null) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root, "Movie is already saved", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                withContext(Dispatchers.IO) {
                    db.userMovieDao().insert(userMovie)
                }
                withContext(Dispatchers.Main) {
                    Snackbar.make(binding.root, "Movie saved", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showConfirmationDialog(title: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to see more details about this movie on IMDb?")
            .setPositiveButton("Yes") { dialog, id ->
                val imdbUrl = createImdbUrl(title)
                val imdbIntent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))
                startActivity(imdbIntent)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun createImdbUrl(title: String?): String {
        val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
        return "https://www.imdb.com/find?q=$encodedTitle"
    }
}
