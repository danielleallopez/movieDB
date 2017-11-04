package com.dleal.moviedb.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.dleal.moviedb.R
import com.dleal.moviedb.ui.base.BaseActivity
import com.dleal.moviedb.ui.latestMovies.LatestMoviesActivity

class SplashActivity : BaseActivity<SplashViewModel>() {

    private val splashViewModel by lazy { injectViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()

    }

    override fun onStart() {
        super.onStart()

        splashViewModel.start()
    }

    private fun observeViewModel() {
        splashViewModel.navigationEvents.observe(this, Observer {
            it?.let {
                LatestMoviesActivity.start(this)
                finish()
            }
        })
    }

    override fun injectViewModel(): SplashViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)

    override fun getLayoutId(): Int = R.layout.activity_splash
}
