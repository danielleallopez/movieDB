package com.dleal.moviedb.data.base

/**
 * Created by Daniel Leal on 2/11/17.
 */
sealed class ResponseWrapper<T>

data class Success<T>(val payload: T?) : ResponseWrapper<T>()
data class Failure<T>(val code: Int = INVALID_CODE,
                      val message: String = "") : ResponseWrapper<T>()

class NoInternet<T> : ResponseWrapper<T>()

const val INVALID_CODE = -1