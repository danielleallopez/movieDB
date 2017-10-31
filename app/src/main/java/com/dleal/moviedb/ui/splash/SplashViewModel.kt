package com.dleal.moviedb.ui.splash

import com.dleal.moviedb.ui.base.BaseViewModel
import com.dleal.moviedb.ui.base.SingleLiveEvent
import com.dleal.moviedb.util.SECOND
import java.util.Timer
import kotlin.concurrent.timerTask

/**
 * Created by Daniel Leal on 31/10/17.
 */
class SplashViewModel : BaseViewModel() {

    val navigationEvents: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun start() {
        val timer = Timer()
        timer.schedule(timerTask { navigationEvents.postValue(true) }, 3 * SECOND)
    }
}