package com.dleal.moviedb.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Daniel Leal on 31/10/17.
 */
abstract class BaseActivity : AppCompatActivity(), View {

    lateinit var presenter: BasePresenter<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.attachView(this)
    }

    override fun onStart() {
        super.onStart()

        presenter.start()
    }

    override fun onStop() {
        super.onStop()

        presenter.stop()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detachView()
    }
}