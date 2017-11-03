package com.dleal.moviedb.ui.base

import android.arch.lifecycle.ViewModel
import com.dleal.moviedb.util.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Daniel Leal on 31/10/17.
 */
abstract class BaseViewModel : ViewModel(), Logger {

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    abstract fun start()

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(disposable)
    }
}