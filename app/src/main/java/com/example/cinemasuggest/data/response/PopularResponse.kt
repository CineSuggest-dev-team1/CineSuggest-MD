package com.example.cinemasuggest.data.response

import com.google.gson.annotations.SerializedName

data class PopularResponse(

	@field:SerializedName("results")
	val results: List<Movie>
)

data class Movie(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("poster")
	val poster: String
)
