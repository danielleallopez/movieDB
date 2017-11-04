package com.dleal.moviedb.ui.latestMovies

import com.dleal.moviedb.domain.movies.MovieModel

/**
 * Created by daniel.leal on 04.11.17.
 */
data class LatestMoviesUiModel(val data: List<MovieModel> = emptyList(),
                               var mainLoading: Boolean = false,
                               var pageLoading: Boolean = false)