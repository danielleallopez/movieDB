package com.dleal.moviedb.domain.errors

/**
 * Created by Daniel Leal on 2/11/17.
 */
class NoInternetException : Exception()

data class GenericErrorException(val errorMessage: String = "") : Exception()