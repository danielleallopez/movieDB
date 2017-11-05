package com.dleal.moviedb.util.extensions

import android.app.Activity
import android.support.annotation.DrawableRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.centerInsideTransform

/**
 * Created by daniel.leal on 04.11.17.
 */
fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.show(imageUrl: String) {
    Glide.with(this.context)
            .load(imageUrl)
            .into(this)
}

fun ImageView.show(imageUrl: String, @DrawableRes placeholderId: Int) {
    Glide.with(this.context)
            .load(imageUrl)
            .apply(centerInsideTransform()
                    .placeholder(placeholderId))
            .into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Activity.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}