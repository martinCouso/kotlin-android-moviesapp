package com.utopyk.movieapp.ui.movies.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utopyk.movieapp.core.BaseConcatHolder
import com.utopyk.movieapp.databinding.TopRatedMoviesRowBinding

class TopRatedConcatAdapter(private val moviesAdapter: MoviesAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>() {
    private inner class ConcatViewHolder(val binding: TopRatedMoviesRowBinding):
        BaseConcatHolder<MoviesAdapter>(binding.root){
        override fun bind(adapter: MoviesAdapter){
            binding.rvTopRatedMovies.adapter= adapter;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding = TopRatedMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding);
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder -> holder.bind(moviesAdapter);
        }
    }

    override fun getItemCount(): Int =1
}