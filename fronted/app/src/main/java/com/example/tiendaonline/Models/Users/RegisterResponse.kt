package com.example.tiendaonline.Models.Users

data class RegisterResponse(
    val jwt: String,
    val user: User
)