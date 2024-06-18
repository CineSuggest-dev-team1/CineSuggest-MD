package com.dicoding.cinemasuggest.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.cinemasuggest.data.response.SearchResponseItem
import com.dicoding.cinesuggest.databinding.ItemMovie2Binding
import com.dicoding.cinesuggest.view.Detail.MovieDetailActivity

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MovieViewHolder>() {

    private val searchList = ArrayList<SearchResponseItem>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun  setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun interface OnItemClickCallback{
        fun onItemClicked(item: SearchResponseItem)
    }


    class MovieViewHolder(private val binding: ItemMovie2Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: SearchResponseItem) {


            val posterUrl1 = "https://image.tmdb.org/t/p/w500${movie.poster}"

            with(binding) {
                Glide.with(itemView)
                    .load(posterUrl1)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(movieImage)
                movieTitle.text = movie.title

                binding.root.setOnClickListener {
                    val intent = Intent(binding.root.context, MovieDetailActivity::class.java)
                    intent.putExtra(MovieDetailActivity.EXTRA_ID, movie.id)
                    intent.putExtra(MovieDetailActivity.EXTRA_TITLE, movie.title)
                    intent.putExtra(MovieDetailActivity.EXTRA_POSTER, movie.poster)
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }


    fun setData(movies: List<SearchResponseItem>) {
        val diffCallback = DiffUtilCallback(searchList, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        searchList.clear()
        searchList.addAll(movies)
        notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = ItemMovie2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = searchList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(
                searchList[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    companion object {
        class DiffUtilCallback(private val oldItem: List<SearchResponseItem>, private val newItem: List<SearchResponseItem>) : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItem.size
            }

            override fun getNewListSize(): Int {
                return newItem.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItem[oldItemPosition]
                val newItem = newItem[newItemPosition]

                // Periksa apakah ID item sama, pastikan tidak null
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItem[oldItemPosition]
                val newItem = newItem[newItemPosition]

                // Periksa apakah semua konten item sama, pastikan tidak null
                return oldItem.title == newItem.title &&
                        oldItem.poster == newItem.poster
            }

            @Override
            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                return super.getChangePayload(oldItemPosition, newItemPosition)
            }
        }
    }

}