package com.dleal.moviedb.ui.latestMovies

import android.support.v7.util.DiffUtil
import com.dleal.moviedb.domain.movies.MovieModel


/**
 * Created by daniel.leal on 04.11.17.
 */
class DiffCallback(private var oldList: List<MovieModel>,
                   private var newList: List<MovieModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
