package com.example.detector_de_accidentes.firebase.models

class PurseFirebase(
    var user_id: String,
    var name: String,
    var description: String,
    var icon_name: String,
    var sub_total: Int
) : FirebaseEntity(documentId = null) {
    constructor() : this(
        user_id = "", name = "",
        description = "", icon_name = "", sub_total = 0
    )

    override fun toString(): String {
        return "Purse ($user_id, $name, $description, $icon_name, $sub_total)"
    }
}