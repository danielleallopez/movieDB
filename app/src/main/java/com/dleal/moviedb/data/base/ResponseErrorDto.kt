package com.dleal.moviedb.data.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel Leal on 2/11/17.
 */
data class ResponseErrorDto(
        @SerializedName("status_message") val message: String?,
        @SerializedName("status_code") val code: Int?
)