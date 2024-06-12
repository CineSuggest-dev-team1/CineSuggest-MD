package com.dicoding.cinesuggest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cinesuggest.R
import com.dicoding.cinesuggest.data.Movie
import com.dicoding.cinesuggest.view.recomendation.RecommendationActivity

class RecommendedAdapter constructor(private val activity: RecommendationActivity,private val movieList: List<Movie>) :
    RecyclerView.Adapter<RecommendedAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommended_list,parent, false)
        return MyViewHolder(view)
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvMovieTitle : TextView = itemView.findViewById(R.id.tvMovieTitle)
        val ivMovie : ImageView = itemView.findViewById(R.id.iv_movie)
        val cardView : CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvMovieTitle.text = movieList[position].title
        holder.ivMovie.setImageResource(movieList[position].image)
        holder.cardView.setOnClickListener{
            Toast.makeText(activity, movieList[position].title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}