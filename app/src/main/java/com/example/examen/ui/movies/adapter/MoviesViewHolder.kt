package com.example.examen.ui.movies.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.core.GlideHelper
import com.example.examen.databinding.ItemMoviesBinding
import com.example.examen.data.model.MoviesModel

class MoviesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val mBinding = ItemMoviesBinding.bind(view)

    fun render(item: MoviesModel) {
        GlideHelper.loadImage(mBinding.root.context, item.poster, mBinding.ivPoster)
        mBinding.tvTitle.text = item.title
        mBinding.tvDate.text = item.date
        mBinding.tvVoteAverage.text = item.voteAverage
    }
}