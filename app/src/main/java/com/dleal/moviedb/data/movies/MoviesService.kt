package com.dleal.moviedb.data.movies

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by daniel.leal on 04.11.17.
 */
interface MoviesService {

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int, @Query("language") language: String)
            : Single<Response<MovieCollectionDto>>
}