package com.dleal.moviedb.di

import com.dleal.moviedb.BuildConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Daniel Leal on 2/11/17.
 */
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
                        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
            @Named(KEY_CONNECT_TIMEOUT) connectTimeout: Long,
            @Named(KEY_READ_TIMEOUT) readTimeout: Long,
            interceptor: Interceptor
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder().apply {
            connectTimeout(connectTimeout, TimeUnit.SECONDS)
            readTimeout(readTimeout, TimeUnit.SECONDS)
            addInterceptor(interceptor)
            if (BuildConfig.DEBUG) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logInterceptor)
            }
        }
        return okHttpBuilder.build()
    }

    @Provides
    @Named(KEY_RETRY_SEC_COEFFICIENT)
    fun provideRetrySecondsCoefficient(): Long = 1

    @Provides
    @Named(KEY_CONNECT_TIMEOUT)
    fun provideConnectTimeout(): Long = 30  //Seconds

    @Provides
    @Named(KEY_READ_TIMEOUT)
    fun provideReadTimeout(): Long = 30     //Seconds

    @Provides
    fun provideRxJavaAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideInterceptor(): Interceptor =
            Interceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                //API key
                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter(REQUEST_API_KEY, BuildConfig.API_KEY)
                        .build()

                val requestBuilder = original.newBuilder().url(url)

                val request = requestBuilder.build()
                chain.proceed(request)
            }

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)
}

const val REQUEST_API_KEY = "api_key"
const val KEY_RETRY_SEC_COEFFICIENT = "RETRY_SEC_COEFFICIENT"
const val KEY_CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
const val KEY_READ_TIMEOUT = "READ_TIMEOUT"