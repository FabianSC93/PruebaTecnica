package com.example.examen.core

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideHelper {
    fun loadImage(context: Context, image: String, iv: ImageView) {
        Glide.with(context).load(image).into(iv)
    }
}