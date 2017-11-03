package com.dleal.moviedb.usecase

import com.dleal.moviedb.data.configuration.ImagesConfigurationDto
import com.dleal.moviedb.domain.configuration.ImageServiceConfigModel
import com.dleal.moviedb.mappers.configurationMapper
import com.dleal.moviedb.repository.configuration.ConfigurationRepository
import com.dleal.moviedb.usecase.base.UseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Daniel Leal on 2/11/17.
 */
class GetConfigurationUseCase @Inject constructor(
        private val repository: ConfigurationRepository
) : UseCase<ImagesConfigurationDto, ImageServiceConfigModel>() {

    fun fetchImagesConfiguration(): Single<ImageServiceConfigModel> =
            repository
                    .getConfiguration(MAX_TIMEOUT)
                    .flatMap { response -> generateUseCaseResponse(response, configurationMapper) }
}

private const val MAX_TIMEOUT: Long = 2   //Seconds