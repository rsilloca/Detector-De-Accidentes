package com.example.detector_de_accidentes.firebase.repository

import android.util.Log
import com.example.detector_de_accidentes.firebase.livedata.DocumentReferenceFirebaseLiveData
import com.example.detector_de_accidentes.firebase.livedata.MultipleDocumentReferenceLiveData
import com.example.detector_de_accidentes.firebase.models.FirebaseEntity
import com.example.detector_de_accidentes.firebase.utils.GenericMapper
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

abstract class FirebaseRepository<E : FirebaseEntity>(entityClass: Class<E>) :
    CrudFirebaseRepository<E, String> {
    val collectionReference: CollectionReference
    val entityClass: Class<E>
    override fun findAll(): MultipleDocumentReferenceLiveData<E, CollectionReference> {
        Log.e(TAG, "findAll()")
        return MultipleDocumentReferenceLiveData(collectionReference, entityClass)
    }

    override fun findById(identifier: String): DocumentReferenceFirebaseLiveData<E> {
        Log.e(TAG, "findById()")
        return DocumentReferenceFirebaseLiveData(
            collectionReference.document(identifier),
            entityClass
        )
    }

    override fun save(entity: E) {
        Log.e(TAG, "save()")

        collectionReference.document().set(entity)
    }

    override fun saveAll(entities: List<E>) {
        Log.e(TAG, "saveAll()")
        val batch = FirebaseFirestore.getInstance().batch()
        for (entity in entities) {
            val documentReference = collectionReference.document()
            batch.set(documentReference, entity)
        }
        batch.commit()
    }

    override fun update(entity: E) {
        Log.e(TAG, "update()")
        collectionReference.document(entity.documentId!!).set(entity)
    }

    @Throws(IllegalAccessException::class)
    override fun updateAll(entities: List<E>) {
        Log.e(TAG, "updateAll()")
        val batch = FirebaseFirestore.getInstance().batch()
        for (entity in entities) {
            val documentReference = collectionReference.document(entity.documentId!!)
            batch.update(documentReference, GenericMapper.convertMapToObject(entity))
        }
        batch.commit()
    }

    override fun delete(entity: E): Task<Void> {
        Log.e(TAG, "delete()")
        return collectionReference.document(entity.documentId!!).delete()
    }

    override fun deleteAll(entities: List<E>) {
        Log.e(TAG, "deleteAll()")
        val batch = FirebaseFirestore.getInstance().batch()
        for (entity in entities) {
            batch.delete(collectionReference.document(entity.documentId!!))
        }
        batch.commit()
    }

    companion object {
        private val TAG = FirebaseRepository::class.java.simpleName
    }

    init {
        Log.e(TAG, "FIREBASE_REPOSITORY")
        this.entityClass = entityClass
        collectionReference =
            FirebaseFirestore.getInstance().collection(this.entityClass.simpleName)
    }
}