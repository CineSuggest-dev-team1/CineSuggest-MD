package com.example.cinemasuggest.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.cinemasuggest.data.response.Movie
import com.example.cinemasuggest.databinding.ItemMovie2Binding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: List<Movie> = emptyList()
    private var onMovieItemClickListener: OnMovieItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovie2Binding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    fun setItems(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()
    }

    fun setOnMovieItemClickListener(listener: OnMovieItemClickListener) {
        this.onMovieItemClickListener = listener
    }

    inner class MovieViewHolder(private val binding: ItemMovie2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onMovieItemClickListener?.onMovieItemClick(movieList[position])
                }
            }
        }

        fun bind(movie: Movie) {
            with(binding) {
                Glide.with(itemView)
                    .load(movie.poster)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(movieImage)
                movieTitle.text = movie.title
            }
        }
    }

    interface OnMovieItemClickListener {
        fun onMovieItemClick(movie: Movie)
    }
}
