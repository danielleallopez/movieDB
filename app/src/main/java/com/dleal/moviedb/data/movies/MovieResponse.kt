package com.dleal.moviedb.data.movies

import com.google.gson.annotations.SerializedName

/**
 * Created by daniel.leal on 04.11.17.
 */
data class MovieDto(
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("adult") val adult: Boolean?,
        @SerializedName("overview") val overview: String?,
        @SerializedName("release_date") val releaseDate: String?,
        @SerializedName("id") val id: Int?,
        @SerializedName("original_title") val originalTitle: String?,
        @SerializedName("original_language") val originalLanguage: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("popularity") val popularity: Float?,
        @SerializedName("vote_count") val voteCount: Int?,
        @SerializedName("video") val video: Boolean?,
        @SerializedName("vote_average") val voteAverage: Float?
)

class MovieCollectionDto(
        @SerializedName("page") val page: Int,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("dates") val dateRangeRange: DateRangeDto,
        @SerializedName("results") val movieList: List<MovieDto>
)

class DateRangeDto(
        @SerializedName("maximum") val maxDate: String,
        @SerializedName("minimum") val minDate: String
)

class MovieDetailsDto(
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("backdrop_path") val backdropPath: String,
        @SerializedName("budget") val budget: Long,
        @SerializedName("website") val homepage: String,
        @SerializedName("imdb_id") val imdbId: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("popularity") val popularity: Float,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("runtime") val runtime: Int,
        @SerializedName("tagLine") val tagline: String,
        @SerializedName("title") val title: String,
        @SerializedName("vote_average") val voteAverage: Float,
        @SerializedName("vote_count") val voteCount: Int
)