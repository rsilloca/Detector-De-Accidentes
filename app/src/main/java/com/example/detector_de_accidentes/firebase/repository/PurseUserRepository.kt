package com.example.detector_de_accidentes.firebase.repository

import com.example.detector_de_accidentes.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.detector_de_accidentes.firebase.models.PurseUserFirebase
import com.google.firebase.firestore.Query

object PurseUserRepository: FirebaseRepository<PurseUserFirebase>(PurseUserFirebase::class.java){

    fun findPurseUserByUser(idUser: String) : MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "user_id", idUser
        ), entityClass)
    }

    fun findPurseUserByBoth(idPurse: String, idUser: String) : MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "purse_id", idPurse
        ).whereEqualTo(
            "user_id", idUser
        ), entityClass)
    }

    fun findPurseUserByPurse(idPurse: String) : MultipleDocumentReferenceLiveData<PurseUserFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(collectionReference.whereEqualTo(
            "purse_id", idPurse
        ), entityClass)
    }
}