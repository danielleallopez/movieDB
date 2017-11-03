package com.dleal.moviedb.util

import android.util.Log

/**
 * Created by Daniel Leal on 3/11/17.
 */
interface Logger {

    private val TAG: String
        get() = javaClass.simpleName

    fun logDebug(message: String) = Log.d(TAG, message)
    fun logWarning(message: String) = Log.w(TAG, message)
    fun logError(message: String) = Log.e(TAG, message)
    fun logInfo(message: String) = Log.i(TAG, message)
}