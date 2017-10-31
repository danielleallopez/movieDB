package com.dleal.moviedb.di

import android.app.Application
import com.dleal.moviedb.ui.MovieDbApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Daniel Leal on 31/10/17.
 */
@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class
))
interface AppComponent {

    fun inject(application: MovieDbApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}