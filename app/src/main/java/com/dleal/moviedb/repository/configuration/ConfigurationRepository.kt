package com.dleal.moviedb.repository.configuration

import com.dleal.moviedb.data.base.ResponseWrapper
import com.dleal.moviedb.data.base.Success
import com.dleal.moviedb.data.configuration.ConfigurationDto
import com.dleal.moviedb.data.configuration.ConfigurationService
import com.dleal.moviedb.data.configuration.ImagesConfigurationDto
import com.dleal.moviedb.domain.errors.GenericErrorException
import com.dleal.moviedb.repository.base.BaseLocalDataSource
import com.dleal.moviedb.repository.base.BaseRemoteDataSource
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Daniel Leal on 2/11/17.
 */
class ConfigurationRepository @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
) {
    fun getConfiguration(maxTimeout: Long): Single<ResponseWrapper<ImagesConfigurationDto>> =
            remoteDataSource.getConfiguration(maxTimeout)
                    .map { response -> validate(response) }
                    .onErrorResumeNext { localDataSource.getDefaultConfiguration() }

    private fun validate(responseWrapper: ResponseWrapper<ConfigurationDto>): ResponseWrapper<ImagesConfigurationDto> {
        if (responseWrapper is Success) {
            responseWrapper.payload?.let {
                //Persist configuration
                localDataSource.imagesConfigurationDto = it.imagesConfiguration
                return Success(it.imagesConfiguration)
            }
        }
        throw GenericErrorException("Downloaded configuration is not valid")
    }

    //Datasources
    class RemoteDataSource @Inject constructor() : BaseRemoteDataSource() {

        fun getConfiguration(maxTimeout: Long): Single<ResponseWrapper<ConfigurationDto>> =
                processApiStream(createService(ConfigurationService::class.java).getConfiguration())
                        .timeout(maxTimeout, TimeUnit.SECONDS)
    }

    class LocalDataSource @Inject constructor() : BaseLocalDataSource() {
        fun getDefaultConfiguration(): Single<ResponseWrapper<ImagesConfigurationDto>> =
                Single.just(
                        Success(
                                ImagesConfigurationDto(
                                        DEFAULT_IMAGE_URL, DEFAULT_BACKDROP_SIZES, DEFAULT_POSTER_SIZES
                                )
                        )
                )
    }
}

const val DEFAULT_IMAGE_URL = "http://image.tmdb.org/t/p/"

val DEFAULT_BACKDROP_SIZES = listOf(
        "w300",
        "w780",
        "w1280",
        "original"
)

val DEFAULT_POSTER_SIZES = listOf(
        "w92",
        "w154",
        "w185",
        "w342",
        "w500",
        "w780",
        "original"
)