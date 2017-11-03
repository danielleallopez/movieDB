package com.dleal.moviedb.mappers

import com.dleal.moviedb.data.configuration.ImagesConfigurationDto
import com.dleal.moviedb.domain.configuration.ImageServiceConfigModel

/**
 * Created by Daniel Leal on 2/11/17.
 */
val configurationMapper = { configDto: ImagesConfigurationDto? ->
    configDto!!.let {
        ImageServiceConfigModel(it.imageBaseUrl, it.backdropSizes, it.posterSizes)
    }
}