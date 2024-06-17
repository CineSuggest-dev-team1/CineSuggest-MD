package com.example.cinemasuggest.view.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.cinemasuggest.databinding.ActivityDetailBinding
import com.example.cinemasuggest.view.home.HomeActivity
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_POSTER = "extra_poster"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val poster = intent.getStringExtra(EXTRA_POSTER)
        val title = intent.getStringExtra(EXTRA_TITLE)

        // Set data to views
        val posterUrl = "https://image.tmdb.org/t/p/w500$poster"
        binding.category.text = title
        Glide.with(this)
            .load(posterUrl)
            .into(binding.imageView)

        // Back button click listener
        binding.ibBack.setOnClickListener {
            val intent = Intent(this@DetailActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        // See movie button click listener
        binding.button.setOnClickListener {
            showConfirmationDialog(title)
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
