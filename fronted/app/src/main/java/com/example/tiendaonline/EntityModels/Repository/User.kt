package com.example.tiendaonline.EntityModels.Repository

data class User(
    val Identification: String,
    val Phone: String,
    val Photo: Any,
    val blocked: Boolean,
    val confirmed: Boolean,
    val created_at: String,
    val email: String,
    val id: Int,
    val provider: String,
    val role: Role,
    val updated_at: String,
    val username: String
)
