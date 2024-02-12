package com.vasilyev.movielistapp.presentation.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.vasilyev.movielistapp.domain.model.MovieItem

class MovieListDiffUtilCallBack : DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }
}