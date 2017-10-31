package com.dleal.moviedb.di.splash

import dagger.Module
import dagger.Provides

/**
 * Created by Daniel Leal on 31/10/17.
 */
@Module
class SplashModule {

    @Provides
    fun provideNumber(): String = "MyString"
}