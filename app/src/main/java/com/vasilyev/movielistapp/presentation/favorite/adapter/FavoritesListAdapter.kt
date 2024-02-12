package com.vasilyev.movielistapp.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.vasilyev.movielistapp.R
import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.presentation.main.adapter.MovieItemViewHolder
import com.vasilyev.movielistapp.presentation.main.adapter.MovieListDiffUtilCallBack

class FavoritesListAdapter: ListAdapter<MovieItem, MovieItemViewHolder>(MovieListDiffUtilCallBack()) {
    var onItemClickCallBack: ((id: Int) -> Unit)? = null
    var onItemLongClickCallBack: ((MovieItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)

        return MovieItemViewHolder(view)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val currentItem = currentList[position]

        with(holder){
            if(currentItem.title.length > LIMIT_TEXT_SIZE){
                title.text = "${currentItem.title.substring(0, LIMIT_TEXT_SIZE)}..."
            }else{
                title.text = currentItem.title
            }

            val genreText = currentItem.genres[0].replaceFirstChar { it.uppercaseChar() }
            genre.text = "$genreText (${currentItem.year})"
        }

        Glide
            .with(holder.itemView.context)
            .load(currentItem.previewPosterUrl)
            .into(holder.poster)


        if (position == itemCount - 1) {
            addMarginForLastElement(holder)
        } else {
            clearMarginFromCurrentElement(holder)
        }

        setClickCallBacks(holder, currentItem)
    }

    private fun setClickCallBacks(holder: MovieItemViewHolder, currentItem: MovieItem){
        holder.itemView.setOnClickListener {
            onItemClickCallBack?.invoke(currentItem.id)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClickCallBack?.invoke(currentItem)
            true
        }
    }

    private fun addMarginForLastElement(holder: MovieItemViewHolder){
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.bottomMargin = holder.itemView.context.resources.getDimension(R.dimen.margin_bottom_last_item).toInt()
        holder.itemView.layoutParams = layoutParams
    }

    private fun clearMarginFromCurrentElement(holder: MovieItemViewHolder){
        val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.bottomMargin = 0
        holder.itemView.layoutParams = layoutParams
    }

    companion object{
        private const val LIMIT_TEXT_SIZE = 20
    }
}