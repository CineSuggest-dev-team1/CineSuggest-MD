package com.dicoding.cinesuggest.view.recomendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.cinesuggest.R
import com.dicoding.cinesuggest.adapter.RecommendationPagerAdapter
import com.dicoding.cinesuggest.adapter.RecommendedAdapter
import com.dicoding.cinesuggest.data.Movie

class RecommendationActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var recommendedAdapter: RecommendedAdapter? =null
    private var movieList = mutableListOf<Movie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        movieList = ArrayList()

        recyclerView = findViewById<View>(R.id.rv_recommended) as RecyclerView
        recommendedAdapter = RecommendedAdapter(this@RecommendationActivity, movieList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recommendedAdapter

        supportActionBar?.hide()

        movieList()

    }

    private fun movieList(){
//        var movie = Movie("Spiderman", R.drawable.image1)
//        movieList.add(movie)
//
//        movie = Movie("Batman", R.drawable.image2)
//        movieList.add(movie)

        var movie = Movie("Avatar", R.drawable.movie3)
        movieList.add(movie)

        movie = Movie("Dora and The Lost City of Gold", R.drawable.movie4)
        movieList.add(movie)

        movie = Movie("Watcher", R.drawable.movie5)
        movieList.add(movie)

        movie = Movie("Enola Holmes2", R.drawable.movie6)
        movieList.add(movie)

        recommendedAdapter!!.notifyDataSetChanged()
    }
}