package com.utopyk.movieapp.ui.movies.adapters.concat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utopyk.movieapp.core.BaseViewHolder
import com.utopyk.movieapp.data.model.Movie
import com.utopyk.movieapp.databinding.MovieItemBinding

/**
 * The adapter is the piece that will connect our data to our RecyclerView and determine the ViewHolder(s)
 * which will need to be used to display that data.
 */
class MoviesAdapter(private val moviesList: List<Movie>, private val itemClickListener: OnMovieClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieClick(movie:Movie)
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private inner class MoviesViewHolder(val binding:MovieItemBinding, val context: Context): BaseViewHolder<Movie>(binding.root){
        override fun bind(item: Movie) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.poster_path}").centerCrop().into(binding.imgMovie)
        }

    }
open
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        val holder = MoviesViewHolder(itemBinding, parent.context)
        itemBinding.root.setOnClickListener{
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION } ?: return@setOnClickListener
            itemClickListener.onMovieClick(moviesList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MoviesViewHolder -> holder.bind(moviesList[position])
        }
    }

    override fun getItemCount(): Int = moviesList.size;

}