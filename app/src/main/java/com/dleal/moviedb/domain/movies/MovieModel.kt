package com.dleal.moviedb.domain.movies

import java.util.Date

/**
 * Created by daniel.leal on 04.11.17.
 */
data class MovieModel(
        val posterPath: String?,
        val adult: Boolean?,
        val overview: String?,
        val releaseDate: Date,
        val id: Int,
        val originalTitle: String?,
        val originalLanguage: String?,
        val title: String,
        val backdropPath: String?,
        val popularity: Float?,
        val voteCount: Int?,
        val video: Boolean?,
        val scoreAverage: Float?
)

data class MovieCollection(
        val movieList: List<MovieModel> = emptyList(),
        val movieCollectionPage: MovieCollectionPage? = null,
        val datesRange: DateRange? = null
)

data class DateRange(
        val minDate: Date,
        val maxDate: Date
)

data class MovieCollectionPage(
        val page: Int,
        val totalPages: Int,
        val totalResults: Int
)