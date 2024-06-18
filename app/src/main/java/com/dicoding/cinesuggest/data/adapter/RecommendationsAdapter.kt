package com.dicoding.cinemasuggest.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.cinemasuggest.data.response.RecommendationResponseItem
import com.dicoding.cinesuggest.R

class RecommendationsAdapter(private val recommendations: List<RecommendationResponseItem>) :
    RecyclerView.Adapter<RecommendationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_rec, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendation = recommendations[position]
        holder.bind(recommendation)
    }

    override fun getItemCount(): Int {
        return recommendations.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        private val posterImageView: ImageView = itemView.findViewById(R.id.movieImage)

        fun bind(recommendation: RecommendationResponseItem) {
            titleTextView.text = recommendation.title
            val posterUrl = "https://image.tmdb.org/t/p/w500${recommendation.poster}"
            Glide.with(itemView.context)
                .load(posterUrl)
                .into(posterImageView)
        }
    }
}
