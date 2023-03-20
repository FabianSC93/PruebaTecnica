package com.example.examen.core

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

object Utils {

    fun getImageUri(bitmap: Bitmap, context: Context): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val rnds = (1000..100000).random()
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            rnds.toString(),
            null
        )
        return Uri.parse(path)
    }
}