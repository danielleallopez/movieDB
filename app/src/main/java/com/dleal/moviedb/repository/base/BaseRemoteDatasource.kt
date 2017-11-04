package com.dleal.moviedb.repository.base

import com.dleal.moviedb.BuildConfig
import com.dleal.moviedb.data.base.Failure
import com.dleal.moviedb.data.base.INVALID_CODE
import com.dleal.moviedb.data.base.NoInternet
import com.dleal.moviedb.data.base.ResponseErrorDto
import com.dleal.moviedb.data.base.ResponseWrapper
import com.dleal.moviedb.data.base.Success
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Daniel Leal on 2/11/17.
 */
abstract class BaseRemoteDataSource {

    @Inject
    lateinit var retrofit: Retrofit

    internal fun <T> processApiStream(apiStream: Single<Response<T>>): Single<ResponseWrapper<T>> =
            apiStream.flatMap { processResponse(response = it) }

    private fun <T> processResponse(response: Response<T>?): Single<ResponseWrapper<T>> =
            response?.let {
                Single.just<ResponseWrapper<T>>(
                        if (response.isSuccessful) {
                            Success(payload = response.body())
                        } else {
                            val failureResponse = try {
                                val annotation = object : Annotation {}
                                val converter: Converter<ResponseBody, ResponseErrorDto> =
                                        retrofit.responseBodyConverter(ResponseErrorDto::class.java, arrayOf(annotation))
                                val responseErrorDto: ResponseErrorDto = converter.convert(response.errorBody())
                                responseErrorDto.let {
                                    Failure<T>(
                                            responseErrorDto.code ?: INVALID_CODE,
                                            responseErrorDto.message ?: ""
                                    )
                                }
                            } catch (e: Exception) {
                                e.apply { if (BuildConfig.DEBUG) printStackTrace() }
                                Failure<T>(response.code())
                            }
                            failureResponse
                        }
                )
            } ?: Single.just(NoInternet())

    internal fun <S> createService(clazz: Class<S>) = retrofit.create(clazz)
}