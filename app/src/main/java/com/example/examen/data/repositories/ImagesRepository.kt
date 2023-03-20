package com.example.examen.data.repositories

import com.example.examen.data.model.ImagesModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ImagesRepository @Inject constructor(private val firebaseStorage: FirebaseStorage) {

    fun uploadImages(images: MutableList<ImagesModel>, callback: (ImagesModel) -> Unit) {
        images.forEach { image ->
            val fileUri = image.uri
            val folder: StorageReference = firebaseStorage.reference.child("images")
            val fileName: StorageReference = folder.child("file" + fileUri!!.lastPathSegment)
            fileName.putFile(fileUri).addOnSuccessListener {
                fileName.downloadUrl.addOnSuccessListener {
                    callback(image)
                }
            }
        }
    }

}