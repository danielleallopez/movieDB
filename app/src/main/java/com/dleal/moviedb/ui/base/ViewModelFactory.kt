package com.dleal.moviedb.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dleal.moviedb.ui.latestMovies.LatestMoviesViewModel
import com.dleal.moviedb.ui.movieDetails.MovieDetailsViewModel
import com.dleal.moviedb.ui.splash.SplashViewModel
import com.dleal.moviedb.usecase.GetConfigurationUseCase
import com.dleal.moviedb.usecase.GetLatestMoviesUseCase
import com.dleal.moviedb.usecase.GetMovieDetailsUseCase
import com.dleal.moviedb.util.RxTransformer
import javax.inject.Inject

/**
 * Created by Daniel Leal on 31/10/17.
 */
class ViewModelFactory @Inject constructor(
        private val rxTransformer: RxTransformer,
        private val getConfigurationUseCase: GetConfigurationUseCase,
        private val getLatestMoviesUseCase: GetLatestMoviesUseCase,
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(SplashViewModel::class.java) -> {
                        SplashViewModel(rxTransformer, getConfigurationUseCase)
                    }
                    isAssignableFrom(LatestMoviesViewModel::class.java) -> {
                        LatestMoviesViewModel(rxTransformer, getLatestMoviesUseCase)
                    }
                    isAssignableFrom(MovieDetailsViewModel::class.java) -> {
                        MovieDetailsViewModel(rxTransformer, getMovieDetailsUseCase)
                    }
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}