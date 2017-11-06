package com.dleal.moviedb.usecase.base

import com.dleal.moviedb.data.base.Failure
import com.dleal.moviedb.data.base.NoInternet
import com.dleal.moviedb.data.base.ResponseWrapper
import com.dleal.moviedb.data.base.Success
import com.dleal.moviedb.domain.errors.GenericErrorException
import com.dleal.moviedb.domain.errors.NoInternetException
import io.reactivex.Single

/**
 * Created by Daniel Leal on 2/11/17.
 */
abstract class UseCase<T, U> {

    inline fun generateUseCaseResponse(response: ResponseWrapper<T>,
                                       mapper: (payload: T?) -> U) =
            when (response) {
                is Success -> Single.just(mapper(response.payload))
                is Failure -> Single.error(GenericErrorException(response.message))
                is NoInternet -> Single.error(NoInternetException())
            }
}