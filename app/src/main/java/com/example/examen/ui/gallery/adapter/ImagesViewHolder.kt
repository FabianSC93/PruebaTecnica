package com.example.examen.ui.gallery.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.core.GlideHelper
import com.example.examen.data.model.ImagesModel
import com.example.examen.databinding.ItemImageBinding

class ImagesViewHolder( view: View): RecyclerView.ViewHolder(view) {

    private val mBinding = ItemImageBinding.bind(view)

    fun render(item: ImagesModel) {
        GlideHelper.loadImage(mBinding.root.context, item.uri.toString(), mBinding.ivImages)
    }
}