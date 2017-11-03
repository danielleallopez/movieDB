package com.dleal.moviedb.domain.configuration

/**
 * Created by Daniel Leal on 2/11/17.
 */
data class ImageServiceConfigModel(
        val imageBaseUrl: String,
        val backdropSizes: List<String>,
        val posterSizes: List<String>
)