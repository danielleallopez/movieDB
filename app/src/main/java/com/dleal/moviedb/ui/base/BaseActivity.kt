package com.dleal.moviedb.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Daniel Leal on 31/10/17.
 */
abstract class BaseActivity<out T : BaseViewModel>: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())
    }

    protected abstract fun injectViewModel(): T
}