package com.dleal.moviedb.ui.splash

import com.dleal.moviedb.domain.configuration.ImageServiceConfigModel
import com.dleal.moviedb.ui.base.BaseViewModel
import com.dleal.moviedb.ui.base.SingleLiveEvent
import com.dleal.moviedb.usecase.GetConfigurationUseCase
import com.dleal.moviedb.util.RxTransformer
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit

/**
 * Created by Daniel Leal on 31/10/17.
 */
class SplashViewModel(
        private val rxTransformer: RxTransformer,
        private val getConfigurationUseCase: GetConfigurationUseCase
) : BaseViewModel() {

    val navigationEvents: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun start() {
        addDisposable(Single.zip(
                getConfigurationUseCase.fetchImagesConfiguration(), delay(EXPECTED_SPLASH_DELAY),
                BiFunction { response: ImageServiceConfigModel, _: Boolean -> response })
                .compose(rxTransformer.applyIoScheduler())
                .subscribe(
                        { navigationEvents.postValue(true) },
                        { navigationEvents.postValue(true) })
        )
    }

    private fun delay(delay: Long) = Single
            .just(true)
            .delay(delay, TimeUnit.SECONDS)
}

private const val EXPECTED_SPLASH_DELAY: Long = 1   //Seconds