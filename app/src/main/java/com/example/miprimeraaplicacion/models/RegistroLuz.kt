package com.example.miprimeraaplicacion.models

import com.google.firebase.firestore.DocumentId

data class RegistroLuz(
    @DocumentId
    var id: String = "",
    var ubicacion: String = "",
    var nivelLuz: Int = 0,
    var estado: String = ""
)