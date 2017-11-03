package com.dleal.moviedb.repository

import com.dleal.moviedb.RxSchedulersRule
import com.dleal.moviedb.data.base.ResponseWrapper
import com.dleal.moviedb.data.base.Success
import com.dleal.moviedb.data.configuration.ConfigurationDto
import com.dleal.moviedb.data.configuration.ImagesConfigurationDto
import com.dleal.moviedb.repository.configuration.ConfigurationRepository
import com.dleal.moviedb.repository.configuration.DEFAULT_BACKDROP_SIZES
import com.dleal.moviedb.repository.configuration.DEFAULT_IMAGE_URL
import com.dleal.moviedb.repository.configuration.DEFAULT_POSTER_SIZES
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Daniel Leal on 2/11/17.
 */
class ConfigRepositoryTest {

    private lateinit var repository: ConfigurationRepository

    private val remoteDataSource: ConfigurationRepository.RemoteDataSource = mock()
    private val localDataSource: ConfigurationRepository.LocalDataSource = mock()

    @Rule
    @JvmField
    var schedulersExecutorRule = RxSchedulersRule()

    @Before
    fun setupConfigurationRepository() {
        repository = ConfigurationRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun givenRemoteFailureVerifyLocalContent() {
        givenRemoteConfigurationFailure()
        givenLocalConfiguration()

        repository.getConfiguration(any())
                .test()
                .assertResult(defaultConfigurationResponse)
    }

    @Test
    fun givenRemoteSuccessVerifyLocalContent() {
        givenRemoteConfigurationSuccess()

        repository.getConfiguration(any())
                .test()
                .assertResult(Success(remoteImageConfigurationResponse))
    }

    private fun givenRemoteConfigurationFailure() {
        whenever(remoteDataSource.getConfiguration(any()))
                .thenReturn(invalidRemoteConfiguration)
    }

    private fun givenRemoteConfigurationSuccess() {
        whenever(remoteDataSource.getConfiguration(any()))
                .thenReturn(validRemoteConfiguration)
    }

    private fun givenLocalConfiguration() {
        whenever(localDataSource.getDefaultConfiguration())
                .thenReturn(validLocalConfiguration)
    }

    private val remoteImageConfigurationResponse =
            ImagesConfigurationDto(
                    "remoteUrl",
                    (1..5).map { "$it" },
                    (6..10).map { "$it" }
            )

    private val remoteConfigurationResponse = ConfigurationDto(remoteImageConfigurationResponse)

    private val defaultConfigurationResponse =
            Success(
                    ImagesConfigurationDto(DEFAULT_IMAGE_URL, DEFAULT_BACKDROP_SIZES, DEFAULT_POSTER_SIZES)
            )

    private val validRemoteConfiguration: Single<ResponseWrapper<ConfigurationDto>> =
            Single.just(
                    Success(
                            remoteConfigurationResponse
                    )
            )

    private val invalidRemoteConfiguration: Single<ResponseWrapper<ConfigurationDto>>
            = Single.just(Success<ConfigurationDto>(null))

    private val validLocalConfiguration: Single<ResponseWrapper<ImagesConfigurationDto>> =
            Single.just(defaultConfigurationResponse)
}