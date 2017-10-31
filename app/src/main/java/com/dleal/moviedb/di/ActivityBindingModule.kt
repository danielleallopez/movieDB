package com.dleal.moviedb.di

import com.dleal.moviedb.di.splash.SplashModule
import com.dleal.moviedb.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Daniel Leal on 31/10/17.
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = arrayOf(SplashModule::class))
    abstract fun bindSplashActivity(): SplashActivity
}