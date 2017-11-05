package com.dleal.moviedb.ui.latestMovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.dleal.moviedb.R
import com.dleal.moviedb.domain.movies.MovieModel
import com.dleal.moviedb.ui.base.BaseActivity
import com.dleal.moviedb.ui.base.CustomItemSeparator
import com.dleal.moviedb.util.extensions.hide
import com.dleal.moviedb.util.extensions.show
import com.dleal.moviedb.util.extensions.toast
import kotlinx.android.synthetic.main.activity_latest_movies.*


class LatestMoviesActivity : BaseActivity<LatestMoviesViewModel>() {

    private val latestMoviesViewModel by lazy { injectViewModel() }

    private val listAdapter = LatestMoviesAdapter { navigate(it) }
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
        observeErrors()
    }

    override fun onStart() {
        super.onStart()

        latestMoviesViewModel.creationLatestMoviesEvents().observe(this, Observer {
            it?.let {
                with(it) {
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
                            swipeRefreshLayout.isRefreshing = false
                            txtEmptyCase.show()
                        }
                    }
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

        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            latestMoviesViewModel.refreshList()
        })
    }

    private fun observeErrors() {
        latestMoviesViewModel.errorEvents.observe(this, Observer {
            it?.let { this.toast(it) }
        })
    }

    private fun navigate(movieModel: MovieModel) {
        toast("Click on ${movieModel.title}")
    }
}

private const val KEY_RECYCLER_VIEW_POSITION = "listPosition"