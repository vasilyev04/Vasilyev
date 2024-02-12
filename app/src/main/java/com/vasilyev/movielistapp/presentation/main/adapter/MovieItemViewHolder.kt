package com.vasilyev.movielistapp.presentation.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vasilyev.movielistapp.R

class MovieItemViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.tv_title)
    val genre: TextView = itemView.findViewById(R.id.tv_genre)
    val poster: ImageView = itemView.findViewById(R.id.iv_movie)
    val favorite: ImageView = itemView.findViewById(R.id.iv_favorite)
}