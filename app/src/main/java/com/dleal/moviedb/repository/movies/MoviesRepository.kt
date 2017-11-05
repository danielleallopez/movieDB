package com.dleal.moviedb.repository.movies

import com.dleal.moviedb.data.base.ResponseWrapper
import com.dleal.moviedb.data.movies.MovieCollectionDto
import com.dleal.moviedb.data.movies.MoviesService
import com.dleal.moviedb.repository.base.BaseRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by daniel.leal on 04.11.17.
 */
class MoviesRepository @Inject constructor(
        private val remoteDataSource: RemoteDataSource
) {

    fun fetchLatestMovies(page: Int, language: String) = remoteDataSource.fetchLatestMovies(page, language)

    //Datasources
    class RemoteDataSource @Inject constructor() : BaseRemoteDataSource() {
        fun fetchLatestMovies(page: Int, language: String)
                : Single<ResponseWrapper<MovieCollectionDto>> =
                processApiStream(createService(MoviesService::class.java).getNowPlaying(page, language))
    }
}
