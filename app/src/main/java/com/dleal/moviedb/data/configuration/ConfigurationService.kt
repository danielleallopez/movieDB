package com.dleal.moviedb.data.configuration

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Daniel Leal on 2/11/17.
 */
interface ConfigurationService {

    @GET("configuration")
    fun getConfiguration(): Single<Response<ConfigurationDto>>
}