package com.dleal.moviedb.util

import android.util.Log
import com.dleal.moviedb.repository.configuration.DEFAULT_IMAGE_URL

/**
 * Created by daniel.leal on 04.11.17.
 */
fun getImagePath(relativePath: String, size: String = "original"): String {

    val s = "$DEFAULT_IMAGE_URL$size$relativePath"
    Log.e("TEXT", s)
    return s
}
