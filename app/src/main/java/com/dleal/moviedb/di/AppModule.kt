package com.dleal.moviedb.di

import com.dleal.moviedb.util.RxTransformer
import dagger.Module
import dagger.Provides


/**
 * Created by Daniel Leal on 31/10/17.
 */
@Module
class AppModule {
    @Provides
    fun provideRxTransformer(): RxTransformer = RxTransformer()
}