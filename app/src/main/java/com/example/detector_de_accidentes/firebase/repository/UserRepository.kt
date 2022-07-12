package com.example.detector_de_accidentes.firebase.repository

import com.example.detector_de_accidentes.firebase.models.User
import com.example.detector_de_accidentes.firebase.service.AuthService
import com.google.firebase.auth.FirebaseUser

object UserRepository : FirebaseRepository<User> (User::class.java){

    private lateinit var authUser: FirebaseUser

    fun saveUser(user: User) {
        authUser = AuthService.firebaseGetCurrentUser()!!
        this.saveWithExistentDocumentId(documentId = authUser.uid, User(
            user.fullname,
            user.email
        ))
    }

    private fun saveWithExistentDocumentId(documentId: String, user: User) =
        this.collectionReference.document(documentId).set(user)

}