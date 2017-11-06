package com.dleal.moviedb.ui.movieDetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.dleal.moviedb.domain.movies.MovieDetailsModel
import com.dleal.moviedb.ui.base.BaseViewModel
import com.dleal.moviedb.ui.base.SingleLiveEvent
import com.dleal.moviedb.usecase.GetMovieDetailsUseCase
import com.dleal.moviedb.util.Logger
import com.dleal.moviedb.util.RxTransformer

/**
 * Created by Daniel Leal on 31/10/17.
 */
class MovieDetailsViewModel(
        private val rxTransformer: RxTransformer,
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel(), Logger {

    val errorEvents: SingleLiveEvent<String> = SingleLiveEvent()

    private var movieDetailsUiModel: MutableLiveData<MovieDetailsUiModel> = MutableLiveData()
    private lateinit var movieDetails: MovieDetailsModel

    fun creationMovieDetailsEvents(movieId: Int): LiveData<MovieDetailsUiModel> = movieDetailsUiModel.apply {
        if (this.value == null) {
            loadMovieDetails(movieId)
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        addDisposable(
                getMovieDetailsUseCase.fetchMovieDetails(movieId)
                        .compose(rxTransformer.applyIoScheduler())
                        .map {
                            movieDetails = it
                            MovieDetailsUiModel(data = it)
                        }
                        .toFlowable()
                        .startWith(MovieDetailsUiModel(loading = true))
                        .subscribe({
                            movieDetailsUiModel.value = it
                        }, {
                            errorEvents.value = it.message
                            movieDetailsUiModel.value = MovieDetailsUiModel(MovieDetailsModel())
                        }))
    }
}