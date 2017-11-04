package com.dleal.moviedb.data.configuration

import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel Leal on 1/11/17.
 */
data class ImagesConfigurationDto(
        @SerializedName("base_url") val imageBaseUrl: String,
        @SerializedName("backdrop_sizes") val backdropSizes: List<String>,
        @SerializedName("poster_sizes") val posterSizes: List<String>
)

class ConfigurationDto(
        @SerializedName("images") val imagesConfiguration: ImagesConfigurationDto
)