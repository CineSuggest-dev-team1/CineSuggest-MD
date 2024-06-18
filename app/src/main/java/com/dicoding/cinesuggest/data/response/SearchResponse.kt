package com.dicoding.cinemasuggest.data.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("SearchResponse")
	val searchResponse: List<SearchResponseItem>
)

data class SearchResponseItem(

	@field:SerializedName("id")
	val id: Int?,

	@field:SerializedName("title")
	val title: String?,

	@field:SerializedName("poster")
	val poster: String?
)
