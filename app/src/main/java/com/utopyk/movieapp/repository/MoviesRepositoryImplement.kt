package com.utopyk.movieapp.repository

import com.utopyk.movieapp.data.model.MovieList
import com.utopyk.movieapp.data.remote.MovieDataSource

class MoviesRepositoryImplement(private val dataSource: MovieDataSource) : MoviesRepository {
    override suspend fun getUpcomingMovies(): MovieList {
       return dataSource.getUpcomingMovies();
    }

    override suspend fun getTopRatedMovies(): MovieList {
        return dataSource.getToRatedMovies();
    }

    override suspend fun getPopularMovies(): MovieList {
        return dataSource.getPopularMovies();
    }

}