package com.utopyk.movieapp.data.remote

import com.utopyk.movieapp.data.model.MovieList
import com.utopyk.movieapp.repository.WebService
import com.utopyk.movieapp.utils.AppConstants

class MovieDataSource(private val webService: WebService) {
    suspend fun getUpcomingMovies(): MovieList {
        return  webService.getUpcomingMovies(AppConstants.API_KEY)
    }

    suspend fun getToRatedMovies(): MovieList {
        return webService.getTopRatedMovies(AppConstants.API_KEY)
    }

    suspend fun getPopularMovies(): MovieList {
        return webService.getPopularMovies(AppConstants.API_KEY)
    }
}