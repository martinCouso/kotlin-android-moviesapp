package com.utopyk.movieapp.repository

import com.utopyk.movieapp.data.model.MovieList

interface MoviesRepository {
    suspend fun getUpcomingMovies():MovieList
    suspend fun getTopRatedMovies():MovieList
    suspend fun getPopularMovies():MovieList
}