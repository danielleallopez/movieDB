package com.dleal.moviedb.usecase

import com.dleal.moviedb.data.movies.MovieDetailsDto
import com.dleal.moviedb.domain.movies.MovieDetailsModel
import com.dleal.moviedb.mappers.movieDetailsMapper
import com.dleal.moviedb.repository.movies.MoviesRepository
import com.dleal.moviedb.usecase.base.UseCase
import com.dleal.moviedb.util.DEFAULT_LANGUAGE
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by daniel.leal on 04.11.17.
 */
class GetMovieDetailsUseCase @Inject constructor(
        private val repository: MoviesRepository
) : UseCase<MovieDetailsDto, MovieDetailsModel>() {

    fun fetchMovieDetails(movieId: Int, language: String = DEFAULT_LANGUAGE): Single<MovieDetailsModel> =
            repository
                    .fetchMovieDetails(movieId, language)
                    .flatMap { response -> generateUseCaseResponse(response, movieDetailsMapper) }
}