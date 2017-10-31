package com.dleal.moviedb.ui.base

import android.arch.lifecycle.ViewModel

/**
 * Created by Daniel Leal on 31/10/17.
 */
abstract class BaseViewModel : ViewModel() {
    abstract fun start()
}