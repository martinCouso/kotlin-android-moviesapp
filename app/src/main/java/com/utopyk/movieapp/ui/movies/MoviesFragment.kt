package com.utopyk.movieapp.ui.movies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.utopyk.movieapp.R
import com.utopyk.movieapp.core.Resource
import com.utopyk.movieapp.data.model.Movie
import com.utopyk.movieapp.data.remote.MovieDataSource
import com.utopyk.movieapp.databinding.FragmentMoviesBinding
import com.utopyk.movieapp.presentation.MovieViewModel
import com.utopyk.movieapp.presentation.MoviesViewModelFactory
import com.utopyk.movieapp.repository.MoviesRepositoryImplement
import com.utopyk.movieapp.repository.RetroFitClient
import com.utopyk.movieapp.ui.movies.adapters.concat.MoviesAdapter
import com.utopyk.movieapp.ui.movies.adapters.concat.PopularConcatAdapter
import com.utopyk.movieapp.ui.movies.adapters.concat.TopRatedConcatAdapter
import com.utopyk.movieapp.ui.movies.adapters.concat.UpcomingConcatAdapter


class MoviesFragment : Fragment(R.layout.fragment_movies), MoviesAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMoviesBinding;
    private lateinit var concatAdapter: ConcatAdapter;

    /**
     * The ViewModel instance is created by a delegate that has been imported through the view
     * model package. As argument it requires the ViewModel that we want to instance it (MovieViewModel)
     * and a factory (MoviesViewModelFactory)
     */
    private val viewModel by viewModels<MovieViewModel> {
        MoviesViewModelFactory(
            MoviesRepositoryImplement(
                MovieDataSource(RetroFitClient.webService)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMoviesBinding.bind(view);
        concatAdapter = ConcatAdapter();

        /**
         * Flow:
         * 1) This fragment will execute fetchUpcomingMovies
         * 2) Resource.Loading will be emitted from the MovieViewModel
         * 3) The fist block of the code below will be executed
         * 4) Then MovieViewModel will try to get the upcoming movies from the Repository Interface
         * 5) The Repository Interface will call the Repository Implementation
         * 6) MoviesRepositoryImplement will ask the MovieDataSource for the information
         * 7) MovieDataSource will call the webService.getUpcomingMovies method
         * 8) The WebService will use retrofit to fetch the information from the API
         * 9) Once the WebService has the response, the data will travel back to MovieViewModel where
         * either Resource.Success or Resource.Failure will be emitted
         * 10) The corresponding "when statement" in the bellow will be executed
         */
        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("LiveDAta fetchMainScreenMovies", "Loading...")
                    binding.progressBar.visibility = View.VISIBLE;
                }
                is Resource.Success -> {
                    Log.d(
                        "LiveDAta fetchMainScreenMovies",
                        "${result.data.first} \n \n ${result.data.second} \n \n ${result.data.third} "
                    )
                    binding.progressBar.visibility = View.GONE;
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MoviesAdapter(
                                    result.data.first.results,
                                    this@MoviesFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MoviesAdapter(
                                    result.data.second.results,
                                    this@MoviesFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MoviesAdapter(
                                    result.data.third.results,
                                    this@MoviesFragment
                                )
                            )
                        )
                    }

                    binding.rvMovies.adapter = concatAdapter;
                }
                is Resource.Failure -> {
                    Log.d("Error fetchMainScreenMovies", "${result.exception}")
                }
            }
        }


    }

    override fun onMovieClick(movie: Movie) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.original_language,
            movie.release_date,
            movie.title,
            )
        findNavController().navigate(action)
    }


}