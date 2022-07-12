package com.example.detector_de_accidentes.firebase.repository

import com.example.detector_de_accidentes.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.detector_de_accidentes.firebase.models.DepositFirebase
import com.google.firebase.firestore.Query

object DepositRepository : FirebaseRepository<DepositFirebase>(DepositFirebase::class.java) {

    fun saveDeposit(depositFirebase: DepositFirebase, documentId: String){
        this.collectionReference.document(documentId).set(depositFirebase)
    }

    fun findByPurseId(idPurse: String): MultipleDocumentReferenceLiveData<DepositFirebase, out Query> {
        return MultipleDocumentReferenceLiveData(
            collectionReference.whereEqualTo(
                "purse_id",
                idPurse
            ), entityClass
        )
    }
}