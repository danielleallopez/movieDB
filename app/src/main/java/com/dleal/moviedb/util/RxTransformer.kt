package com.dleal.moviedb.util

import com.dleal.moviedb.BuildConfig
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Daniel Leal on 2/11/17.
 */
class RxTransformer {

    fun <T> applyIoScheduler(): SingleTransformer<T, T> = apply(Schedulers.io())

    fun <T> applyComputationScheduler(): SingleTransformer<T, T> = apply(Schedulers.computation())

    private fun <T> apply(scheduler: Scheduler): SingleTransformer<T, T> = SingleTransformer { it ->
        it
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { logError(it) }
    }

    //Generic error treatment
    private fun logError(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
        //Could log error here to Crashlytics or something similar
    }
}