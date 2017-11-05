package com.dleal.moviedb.ui.latestMovies

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.dleal.moviedb.domain.movies.MovieCollection
import com.dleal.moviedb.domain.movies.MovieModel
import com.dleal.moviedb.ui.base.BaseViewModel
import com.dleal.moviedb.ui.base.SingleLiveEvent
import com.dleal.moviedb.usecase.GetLatestMoviesUseCase
import com.dleal.moviedb.util.Logger
import com.dleal.moviedb.util.RxTransformer
import com.dleal.moviedb.util.dateToString
import com.dleal.moviedb.util.initCalendar
import java.util.Calendar
import java.util.Date

/**
 * Created by Daniel Leal on 31/10/17.
 */
class LatestMoviesViewModel(
        private val rxTransformer: RxTransformer,
        private val getLatestMoviesUseCase: GetLatestMoviesUseCase
) : BaseViewModel(), Logger {

    val navigationEvents: SingleLiveEvent<Int> = SingleLiveEvent()
    val filterEvents: SingleLiveEvent<MoviesFilterUiModel> = SingleLiveEvent()
    val errorEvents: SingleLiveEvent<String> = SingleLiveEvent()

    private var latestMoviesUiModel: MutableLiveData<LatestMoviesUiModel> = MutableLiveData()
    private lateinit var moviesCollection: MovieCollection
    private var filterDate: Date? = null

    override fun start() {
        loadPage()
    }

    fun creationLatestMoviesEvents(): LiveData<LatestMoviesUiModel> = latestMoviesUiModel.apply {
        if (this.value == null) {
            loadPage()
        }
    }

    fun refreshList() {
        loadPage(1)
    }

    fun onFilterClick() {
        moviesCollection.datesRange?.let {
            val initialDate = filterDate ?: Calendar.getInstance().time
            filterEvents.value = MoviesFilterUiModel(initialDate, it.minDate, it.maxDate)
        }
    }

    fun onItemClick(movieItem: MovieModel) {
        navigationEvents.value = movieItem.id
    }

    private fun loadPage(page: Int = 1) {
        addDisposable(
                getLatestMoviesUseCase.fetchLatestMovies(page)
                        .compose(rxTransformer.applyIoScheduler())
                        .map {
                            moviesCollection = it
                            LatestMoviesUiModel(
                                    data = it.movieList.filter { listFilter(it.releaseDate) },
                                    currentFilter = getDateRangeMessage(),
                                    canClearFilter = filterDate != null)
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

    fun onFilterSelected(time: Date) {
        filterDate = time
        latestMoviesUiModel.value = LatestMoviesUiModel(
                data = moviesCollection.movieList
                        .filter { listFilter(it.releaseDate) },
                currentFilter = getDateRangeMessage(),
                canClearFilter = true)
    }

    fun onClearFilter() {
        filterDate = null
        latestMoviesUiModel.value = LatestMoviesUiModel(
                data = moviesCollection.movieList,
                currentFilter = getDateRangeMessage())
    }

    private val listFilter = { releaseDate: Date ->
        filterDate?.let {
            val filterDateCal = initCalendar(it)
            val releaseDateCal = initCalendar(releaseDate)
            filterDateCal.get(Calendar.DAY_OF_MONTH) == releaseDateCal.get(Calendar.DAY_OF_MONTH)
                    && filterDateCal.get(Calendar.MONTH) == releaseDateCal.get(Calendar.MONTH)
                    && filterDateCal.get(Calendar.YEAR) == releaseDateCal.get(Calendar.YEAR)
        } ?: true
    }

    private fun getDateRangeMessage(): String {
        return filterDate?.let {
            "Movies from ${dateToString(it)}"
        } ?: moviesCollection.datesRange?.let {
            val minDate = it.minDate
            val maxDate = it.maxDate
            "Movies from ${dateToString(minDate)} to ${dateToString(maxDate)}"
        } ?: "-"
    }
}