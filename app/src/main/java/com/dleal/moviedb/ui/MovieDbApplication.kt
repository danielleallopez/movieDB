package com.dleal.moviedb.ui

import android.app.Activity
import android.app.Application
import com.dleal.moviedb.di.AppComponent
import com.dleal.moviedb.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


/**
 * Created by Daniel Leal on 31/10/17.
 */
class MovieDbApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    private val movieAppComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        movieAppComponent.inject(this)
    }
}