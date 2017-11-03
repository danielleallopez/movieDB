package com.dleal.moviedb.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel Leal on 1/11/17.
 */
class MovieDto(
        @SerializedName("poster_path") val posterUrl: String?,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("id") val id: Int,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("title") val title: String,
        @SerializedName("vote_average") val score: Float
)

class PopularMovieResponse(
        @SerializedName("name") val page: Int,
        //
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("total_pages") val totalPages: Int
)