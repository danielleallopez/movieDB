package com.dleal.moviedb.mappers

import com.dleal.moviedb.data.movies.DateRangeDto
import com.dleal.moviedb.data.movies.MovieCollectionDto
import com.dleal.moviedb.data.movies.MovieDetailsDto
import com.dleal.moviedb.data.movies.MovieDto
import com.dleal.moviedb.domain.movies.DateRange
import com.dleal.moviedb.domain.movies.MovieCollection
import com.dleal.moviedb.domain.movies.MovieCollectionPage
import com.dleal.moviedb.domain.movies.MovieDetailsModel
import com.dleal.moviedb.domain.movies.MovieModel
import com.dleal.moviedb.util.parseStringToDate

/**
 * Created by Daniel Leal on 2/11/17.
 */
val movieCollectionMapper = { movieCollectionDto: MovieCollectionDto? ->
    movieCollectionDto?.let {
        val movieList = it.movieList
                .filter { validateMovieDto(it) }
                .map { movieMapper(it) }
        val dateRange = dateRangeMapper(it.dateRangeRange)
        val movieCollectionPage = movieCollectionPageMapper(it.page, it.totalPages, it.totalResults)

        MovieCollection(movieList, movieCollectionPage, dateRange)
    } ?: MovieCollection()
}

val movieMapper = { movieDto: MovieDto ->
    movieDto.let {
        MovieModel(
                it.posterPath ?: "",
                it.adult ?: false,
                it.overview ?: "",
                parseStringToDate(it.releaseDate!!),
                it.id!!,
                it.originalTitle ?: "",
                it.originalLanguage ?: "",
                it.title!!,
                it.backdropPath ?: "",
                it.popularity ?: 0F,
                it.voteCount ?: -1,
                it.video ?: false,
                it.voteAverage ?: 0F
        )
    }
}

val dateRangeMapper = { dateRangeDto: DateRangeDto ->
    dateRangeDto.let {
        DateRange(
                minDate = parseStringToDate(it.minDate),
                maxDate = parseStringToDate(it.maxDate))
    }
}

val movieCollectionPageMapper = { page: Int, totalPages: Int, totalResults: Int ->
    MovieCollectionPage(page, totalPages, totalResults)
}

fun validateMovieDto(movieDto: MovieDto) =
        movieDto.let {
            it.id != null && it.title != null && it.releaseDate != null
        }

val movieDetailsMapper = { movieDetailsDto: MovieDetailsDto? ->
    movieDetailsDto?.let {
        MovieDetailsModel(
                it.adult,
                it.backdropPath,
                it.budget,
                it.homepage,
                it.imdbId,
                it.overview,
                it.popularity,
                it.posterPath,
                it.releaseDate,
                it.runtime,
                it.tagline,
                it.title,
                it.voteAverage,
                it.voteCount
        )
    } ?: MovieDetailsModel()
}