package com.dleal.moviedb.ui.base

import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * Created by daniel.leal on 04.11.17.
 */
class CustomItemSeparator(private val context: Context,
                          private @DimenRes val spaceDimen: Int) : RecyclerView.ItemDecoration() {

    private val space = context.resources.getDimension(spaceDimen).toInt()


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }
    }
}