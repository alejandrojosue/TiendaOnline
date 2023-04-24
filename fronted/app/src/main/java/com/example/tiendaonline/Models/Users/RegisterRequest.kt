package com.example.tiendaonline.Models.Users

data class RegisterRequest(
    val Identification: String,
    val email: String,
    val password: String,
    val username: String
)