package com.dleal.moviedb.ui.latestMovies

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.dleal.moviedb.domain.movies.MovieCollection
import com.dleal.moviedb.ui.base.BaseViewModel
import com.dleal.moviedb.ui.base.SingleLiveEvent
import com.dleal.moviedb.usecase.GetLatestMoviesUseCase
import com.dleal.moviedb.util.Logger
import com.dleal.moviedb.util.RxTransformer

/**
 * Created by Daniel Leal on 31/10/17.
 */
class LatestMoviesViewModel(
        private val rxTransformer: RxTransformer,
        private val getLatestMoviesUseCase: GetLatestMoviesUseCase
) : BaseViewModel(), Logger {

    val navigationEvents: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val errorEvents: SingleLiveEvent<String> = SingleLiveEvent()

    private var latestMoviesUiModel: MutableLiveData<LatestMoviesUiModel> = MutableLiveData()
    private lateinit var moviesCollection: MovieCollection

    override fun start() {
        loadPage()
    }

    fun creationLatestMoviesEvents(): LiveData<LatestMoviesUiModel> = latestMoviesUiModel.apply {
        if (this.value == null) {
            loadPage()
        }
    }

    fun refreshList(){
        loadPage(1)
    }

    private fun loadPage(page: Int = 1) {
        addDisposable(
                getLatestMoviesUseCase.fetchLatestMovies(page)
                        .compose(rxTransformer.applyIoScheduler())
                        .map {
                            moviesCollection = it
                            LatestMoviesUiModel(data = it.movieList)
                        }
                        .toFlowable()
                        .startWith(LatestMoviesUiModel(mainLoading = true))
                        .subscribe({
                            logDebug(it.toString())
                            latestMoviesUiModel.value = it
                        }, {
                            errorEvents.value = it.message
                        }))
    }
}