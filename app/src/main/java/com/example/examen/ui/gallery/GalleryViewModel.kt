package com.example.examen.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examen.data.model.ImagesModel
import com.example.examen.data.repositories.ImagesRepository
import com.example.examen.ui.movies.MoviesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val imagesRepository: ImagesRepository): ViewModel() {

    private val _uiGalleryState = MutableStateFlow<GalleryViewState>(GalleryViewState.Start)
    val uiGalleryState: StateFlow<GalleryViewState> = _uiGalleryState

    fun uploadImage(images: MutableList<ImagesModel>) {
        viewModelScope.launch {
            imagesRepository.uploadImages(images) { item ->
                _uiGalleryState.value = GalleryViewState.ImageReady(item)
            }
        }
    }

}