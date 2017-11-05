package com.dleal.moviedb.util

import com.dleal.moviedb.repository.configuration.DEFAULT_IMAGE_URL

/**
 * Created by daniel.leal on 04.11.17.
 */
fun getImagePath(relativePath: String, size: String = "original"): String =
        "$DEFAULT_IMAGE_URL$size$relativePath"
