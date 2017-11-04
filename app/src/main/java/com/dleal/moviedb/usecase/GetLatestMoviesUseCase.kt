package com.dleal.moviedb.usecase

import com.dleal.moviedb.data.movies.MovieCollectionDto
import com.dleal.moviedb.domain.movies.MovieCollection
import com.dleal.moviedb.mappers.movieCollectionMapper
import com.dleal.moviedb.repository.movies.MoviesRepository
import com.dleal.moviedb.usecase.base.UseCase
import com.dleal.moviedb.util.DEFAULT_LANGUAGE
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by daniel.leal on 04.11.17.
 */
class GetLatestMoviesUseCase @Inject constructor(
        private val repository: MoviesRepository
) : UseCase<MovieCollectionDto, MovieCollection>() {

    fun fetchLatestMovies(page: Int, language: String = DEFAULT_LANGUAGE): Single<MovieCollection> =
            repository
                    .fetchLatestMovies(page, language)
                    .flatMap { response -> generateUseCaseResponse(response, movieCollectionMapper) }
}