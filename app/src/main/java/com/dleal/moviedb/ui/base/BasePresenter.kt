package com.dleal.moviedb.ui.base

/**
 * Created by Daniel Leal on 31/10/17.
 */
abstract class BasePresenter<V : View> {

    protected var view: View? = null
        private set

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun isViewAttached() = view != null

    abstract fun start()
    abstract fun stop()
}