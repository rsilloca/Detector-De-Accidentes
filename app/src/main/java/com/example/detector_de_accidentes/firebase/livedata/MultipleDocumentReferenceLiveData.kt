package com.example.detector_de_accidentes.firebase.livedata

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.detector_de_accidentes.firebase.models.FirebaseEntity
import com.google.firebase.firestore.*

class MultipleDocumentReferenceLiveData<T : FirebaseEntity?, L : Query?>(
    private val multipleDocuments: L, private val entityClass: Class<T>
) :
    LiveData<List<T>?>(),
    EventListener<QuerySnapshot?> {
    protected var listenerRegistration = ListenerRegistration {}

    // Entity Utils.
    protected val entityList: MutableList<T> = ArrayList()
    override fun onActive() {
        listenerRegistration = multipleDocuments!!.addSnapshotListener(this)
        super.onActive()
    }

    override fun onInactive() {
        listenerRegistration.remove()
        super.onInactive()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (querySnapshot != null && !querySnapshot.isEmpty) {
            Log.e(TAG, "Updating")
            entityList.clear()
            entityList.addAll(querySnapshot.toObjects(entityClass))
            for (i in 0 until querySnapshot.size()) {
                entityList[i]!!.documentId = querySnapshot.documents[i].id
            }
            this.setValue(entityList)
        } else if (error != null) {
            Log.e(TAG, error.message, error.cause)
        }
    }

    companion object {
        protected var TAG = MultipleDocumentReferenceLiveData::class.java.simpleName
    }
}