package com.example.detector_de_accidentes.firebase.models
import com.google.firebase.firestore.Exclude

abstract class FirebaseEntity(
    @get:Exclude
    var documentId: String ?
)