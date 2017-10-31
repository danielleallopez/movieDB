package com.dleal.moviedb.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.dleal.moviedb.ui.splash.SplashViewModel
import javax.inject.Inject

/**
 * Created by Daniel Leal on 31/10/17.
 */
class ViewModelFactory @Inject constructor() : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(SplashViewModel::class.java) -> {
                        SplashViewModel()
                    }
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}