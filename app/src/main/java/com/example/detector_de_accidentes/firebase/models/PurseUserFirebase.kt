package com.example.detector_de_accidentes.firebase.models

class PurseUserFirebase (
    var user_id: String,
    var purse_id: String,
    var phone_id: String
): FirebaseEntity(documentId = null) {
    constructor(): this(
        user_id = "", purse_id = "", phone_id=""
    )

    override fun toString(): String {
        return "User - Purse ($user_id, $purse_id, $phone_id)"
    }

}