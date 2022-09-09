package com.utopyk.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.utopyk.movieapp.core.Resource
import com.utopyk.movieapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

class MovieViewModel(private val repo: MoviesRepository): ViewModel() {
    fun fetchMainScreenMovies() = liveData(Dispatchers.IO){
            emit(Resource.Loading())
        try {
            emit(Resource.Success(Triple(repo.getUpcomingMovies(), repo.getTopRatedMovies(), repo.getPopularMovies())));
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }


  /* Instead of using separate functions we can use the one above, with the help of the method Triple.
    fun fetchTopRatedMovies() = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getTopRatedMovies()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchPopularMovies() = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getPopularMovies()))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }*/
}

class MoviesViewModelFactory(private val repo: MoviesRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return  modelClass.getConstructor(MoviesRepository::class.java).newInstance(repo)
    }
}