package com.dleal.moviedb.di

import com.dleal.moviedb.ui.latestMovies.LatestMoviesActivity
import com.dleal.moviedb.ui.movieDetails.MovieDetailsActivity
import com.dleal.moviedb.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Daniel Leal on 31/10/17.
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun bindLatestMoviesActivity(): LatestMoviesActivity

    @ContributesAndroidInjector
    abstract fun bindMovieDetailsActivity(): MovieDetailsActivity
}