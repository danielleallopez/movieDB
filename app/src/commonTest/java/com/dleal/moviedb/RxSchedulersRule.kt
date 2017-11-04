package com.dleal.moviedb

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor

/**
 * Created by Daniel Leal on 2/11/17.
 */
class RxSchedulersRule : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker = ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }

        val testScheduler = TestScheduler()

        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
                RxJavaPlugins.setInitComputationSchedulerHandler { testScheduler }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { testScheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

                try {
                    base?.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}