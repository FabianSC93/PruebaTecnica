package com.example.examen.data.model

import android.net.Uri

//Se genera una data class para las imagenes, indicando que tipo de valor deberá recibir, en este caso de tipo Uri, indicando que puede ser vacío
data class ImagesModel(val uri: Uri?)
