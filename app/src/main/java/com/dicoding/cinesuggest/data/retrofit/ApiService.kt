package com.dicoding.cinemasuggest.data.retrofit


import com.dicoding.cinemasuggest.data.response.Movie
import com.dicoding.cinemasuggest.data.response.RecommendationResponseItem
import com.dicoding.cinemasuggest.data.response.SearchResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovies(): Call<Movie>

    @GET("movie/recommendations")
    fun getRecommendations(
        @Query("genres") genres: String,
        @Query("mood") mood: String
    ): Call<List<RecommendationResponseItem>>

    @GET("movie/search")
    fun searchMovies(
        @Query("title") title: String
    ): Call<List<SearchResponseItem>>
}