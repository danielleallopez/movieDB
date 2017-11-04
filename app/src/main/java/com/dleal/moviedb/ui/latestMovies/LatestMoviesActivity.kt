package com.dleal.moviedb.ui.latestMovies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
                            listAdapter.movieList = data
                        }
                        else -> {
                            progressMain.hide()
                            progressPage.hide()
                            txtEmptyCase.show()
                        }
                    }
                }
            }
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_latest_movies

    override fun injectViewModel(): LatestMoviesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LatestMoviesViewModel::class.java)

    private fun setupMovieList() {
        listMovies?.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CustomItemSeparator(context, R.dimen.listItemSpace))
        }
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
