package com.dleal.moviedb.ui.movieDetails

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.dleal.moviedb.R
import com.dleal.moviedb.ui.base.BaseActivity
import com.dleal.moviedb.util.extensions.hide
import com.dleal.moviedb.util.extensions.show
import com.dleal.moviedb.util.getImagePath
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*

class MovieDetailsActivity : BaseActivity<MovieDetailsViewModel>() {

    private val movieDetailsViewModel by lazy { injectViewModel() }

    companion object {
        @JvmStatic
        fun start(context: Context, movieId: Int) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(KEY_MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(mainToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }


        val movieId = intent.getIntExtra(KEY_MOVIE_ID, -1)
        movieDetailsViewModel.creationMovieDetailsEvents(movieId).observe(this, Observer {
            it?.let {
                when {
                    it.loading -> {
                        progressBar.show()
                        containerMovieDetails.hide()
                    }
                    it.data != null -> {
                        progressBar.hide()
                        containerMovieDetails.show()

                        with(it.data) {
                            bindPoster(posterPath)
                            bindBackPoster(backdropPath)
                            bindTitle(title)
                            bindTagLine(tagLine)
                            bindScore(voteAverage)
                            bindPopularity(popularity)
                            bindOverview(overview)
                        }
                    }
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun getLayoutId(): Int = R.layout.activity_movie_details

    override fun injectViewModel(): MovieDetailsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

    private fun bindPoster(posterPath: String?) {
        posterPath?.let {
            imgPoster.show(getImagePath(it, "w500"), R.drawable.clapper_board)
        } ?: imgPoster.setImageResource(R.drawable.clapper_board)
    }

    private fun bindBackPoster(backPosterPath: String?) {
        backPosterPath?.let {
            imgBack.show(getImagePath(it, "w500"), R.drawable.clapper_board)
        } ?: imgBack.setImageResource(R.drawable.clapper_board)
    }

    private fun bindTitle(title: String) {
        txtTitle.text = title
    }

    private fun bindTagLine(tagLine: String?) {
        if (tagLine.isNullOrEmpty()) {
            txtTagLine.hide()
        } else {
            txtTagLine.text = tagLine
        }
    }

    private fun bindScore(voteAverage: Float?) {
        voteAverage?.let {
            val scoreText = "Average score: $it"
            txtScore.text = scoreText
        } ?: txtScore.hide()
    }

    private fun bindPopularity(popularity: Float?) {
        popularity?.let {
            val popularityText = "Popularity: ${"%.2f".format(it)}"
            txtPopularity.text = popularityText
        } ?: txtPopularity.hide()
    }

    private fun bindOverview(overview: String?) {
        overview?.let {
            txtOverview.text = overview
        } ?: txtOverview.hide()
    }
}

const val KEY_MOVIE_ID = "movieId"