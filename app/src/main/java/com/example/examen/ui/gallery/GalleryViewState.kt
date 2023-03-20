package com.example.examen.ui.gallery

import com.example.examen.data.model.ImagesModel

sealed class GalleryViewState {
    object Start : GalleryViewState()
    data class ImageReady(val item: ImagesModel) : GalleryViewState()
}