package com.dleal.moviedb.ui.latestMovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.dleal.moviedb.R
import com.dleal.moviedb.ui.base.BaseActivity
import com.dleal.moviedb.ui.base.CustomItemSeparator
import com.dleal.moviedb.util.Logger
import com.dleal.moviedb.util.extensions.hide
import com.dleal.moviedb.util.extensions.show
import com.dleal.moviedb.util.extensions.toast
import com.dleal.moviedb.util.initCalendar
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_latest_movies.*
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR


class LatestMoviesActivity : BaseActivity<LatestMoviesViewModel>(), Logger {

    private val latestMoviesViewModel by lazy { injectViewModel() }

    private val listAdapter = LatestMoviesAdapter { latestMoviesViewModel.onItemClick(it) }
    private val linearLayoutManager = LinearLayoutManager(this)

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, LatestMoviesActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupMovieList()

        observeErrorEvents()
        observeNavigationEvents()
        observeShowFilterEvents()

        btnFilter.setOnClickListener { latestMoviesViewModel.onFilterClick() }
        btnClearFilter.setOnClickListener { latestMoviesViewModel.onClearFilter() }
    }

    override fun onStart() {
        super.onStart()
        latestMoviesViewModel.creationLatestMoviesEvents().observe(this, Observer {
            it?.let {
                with(it) {
                    logDebug(it.toString())
                    when {
                        mainLoading -> progressMain.show()
                        pageLoading -> progressPage.show()
                        data.isNotEmpty() -> {
                            progressMain.hide()
                            progressPage.hide()
                            listMovies.show()
                            swipeRefreshLayout.isRefreshing = false
                            listAdapter.movieList = data
                        }
                        else -> {
                            progressMain.hide()
                            progressPage.hide()
                            listMovies.hide()
                            swipeRefreshLayout.isRefreshing = false
                            txtEmptyCase.show()
                        }
                    }

                    btnClearFilter.isEnabled = canClearFilter
                    txtSelectedFilter.text = currentFilter
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putParcelable(KEY_RECYCLER_VIEW_POSITION, linearLayoutManager.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            linearLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_RECYCLER_VIEW_POSITION))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_latest_movies

    override fun injectViewModel(): LatestMoviesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LatestMoviesViewModel::class.java)

    private fun setupMovieList() {
        listMovies?.apply {
            adapter = listAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(CustomItemSeparator(context, R.dimen.listItemSpace))
        }

        swipeRefreshLayout.setOnRefreshListener({ latestMoviesViewModel.refreshList() })
    }

    private fun observeErrorEvents() {
        latestMoviesViewModel.errorEvents.observe(this, Observer {
            it?.let { this.toast(it) }
        })
    }

    private fun observeNavigationEvents() {
        latestMoviesViewModel.navigationEvents.observe(this, Observer {
            it?.let { navigate(it) }
        })
    }

    private fun observeShowFilterEvents() {
        latestMoviesViewModel.filterEvents.observe(this, Observer {
            it?.let { showDatePicker(it) }
        })
    }

    private fun navigate(movieId: Int) {
        toast("Click on $movieId")
    }

    private fun showDatePicker(movieFilterUiModel: MoviesFilterUiModel) {
        val initialDay = initCalendar(movieFilterUiModel.currentDay)
        val datePickerDialog = DatePickerDialog.newInstance(
                onDateSetListener,
                initialDay.get(YEAR),
                initialDay.get(MONTH),
                initialDay.get(DAY_OF_MONTH)
        )
        with(datePickerDialog) {
            vibrate(false)
            setVersion(DatePickerDialog.Version.VERSION_2)
            accentColor = ContextCompat.getColor(this@LatestMoviesActivity, R.color.white)
            this.minDate = initCalendar(movieFilterUiModel.minDay)
            this.maxDate = initCalendar(movieFilterUiModel.maxDay)
            show(this@LatestMoviesActivity.fragmentManager, DatePickerDialog::class.java.simpleName)
        }
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        val calendar = Calendar.getInstance().also {
            it.set(Calendar.YEAR, year)
            it.set(Calendar.MONTH, monthOfYear)
            it.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        latestMoviesViewModel.onFilterSelected(calendar.time)
    }
}

private const val KEY_RECYCLER_VIEW_POSITION = "listPosition"