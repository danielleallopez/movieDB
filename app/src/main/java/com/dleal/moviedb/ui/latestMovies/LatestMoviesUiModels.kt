package com.dleal.moviedb.ui.latestMovies

import com.dleal.moviedb.domain.movies.MovieModel
import java.util.Date

/**
 * Created by daniel.leal on 04.11.17.
 */
data class LatestMoviesUiModel(val data: List<MovieModel> = emptyList(),
                               var mainLoading: Boolean = false,
                               var pageLoading: Boolean = false,
                               val currentFilter: String = "-",
                               val canClearFilter: Boolean = false)

class MoviesFilterUiModel(
        val currentDay: Date,
        val minDay: Date,
        val maxDay: Date)