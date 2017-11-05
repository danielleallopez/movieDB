package com.dleal.moviedb.ui.latestMovies

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dleal.moviedb.R
import com.dleal.moviedb.domain.movies.MovieModel
import com.dleal.moviedb.util.dateToString
import com.dleal.moviedb.util.extensions.hide
import com.dleal.moviedb.util.extensions.inflate
import com.dleal.moviedb.util.extensions.show
import com.dleal.moviedb.util.getImagePath
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlin.properties.Delegates


/**
 * Created by daniel.leal on 04.11.17.
 */
class LatestMoviesAdapter(private val clickListener: (MovieModel) -> Unit)
    : RecyclerView.Adapter<MovieViewHolder>() {

    var movieList: List<MovieModel> by Delegates.observable(emptyList()) { _, oldList, newList ->
        val diffResult = DiffUtil.calculateDiff(DiffCallback(oldList, newList))
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
            MovieViewHolder(parent.inflate(R.layout.item_movie), clickListener)
}

class MovieViewHolder(itemView: View,
                      private val clickListener: (item: MovieModel) -> Unit)
    : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: MovieModel) {
        with(itemView) {

            bindTitle(txtTitle, movie)
            bindReleaseDate(txtReleaseDate, movie)
            bindPopularity(txtPopularity, movie)
            bindScore(txtScoreAverage, movie)
            bindImage(imgThumbnail, movie)

            setOnClickListener { _ -> clickListener(movie) }
        }
    }

    private fun bindTitle(txtTitle: TextView, movie: MovieModel) {
        txtTitle.text = movie.title
    }

    private fun bindReleaseDate(txtReleaseDate: TextView, movie: MovieModel) {
        val dateText = "Release date: ${dateToString(movie.releaseDate)}"
        txtReleaseDate.text = dateText
    }

    private fun bindPopularity(txtPopularity: TextView, movie: MovieModel) {
        movie.popularity?.let {
            val popularityText = "Popularity: ${"%.2f".format(it)}"
            txtPopularity.text = popularityText
        } ?: txtPopularity.hide()
    }

    private fun bindScore(txtScoreAverage: TextView, movie: MovieModel) {
        movie.scoreAverage?.let {
            val scoreText = "Average score: $it"
            txtScoreAverage.text = scoreText
        } ?: txtScoreAverage.hide()
    }

    private fun bindImage(imgThumbnail: ImageView, movie: MovieModel) {
        movie.posterPath?.let {
            imgThumbnail.show(getImagePath(movie.posterPath, "w154"), R.drawable.clapper_board)
        } ?: imgThumbnail.setImageResource(R.drawable.film_reel)
    }
}