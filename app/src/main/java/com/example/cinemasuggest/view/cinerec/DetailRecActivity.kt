package com.example.cinemasuggest.view.cinerec

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cinemasuggest.databinding.ActivityDetailrecBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class DetailRecActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailrecBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailrecBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the data from the intent
        val poster = intent.getStringExtra("EXTRA_POSTER")
        val title = intent.getStringExtra("EXTRA_TITLE")

        // Set the data to views
        val posterUrl = "https://image.tmdb.org/t/p/w500$poster"
        binding.category.text = title
        Glide.with(this)
            .load(posterUrl)
            .into(binding.imageView)

        // Back button click listener
        binding.ibBack.setOnClickListener {
            finish()
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
