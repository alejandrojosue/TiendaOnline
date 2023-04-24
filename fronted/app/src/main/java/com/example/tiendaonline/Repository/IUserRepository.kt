package com.example.tiendaonline.Repository

import com.example.tiendaonline.Models.Users.*

interface IUserRepository {
    suspend fun login(requestModel: LoginRequest): Result<LoginResponse>
    suspend fun register(requestModel: RegisterRequest): Result<RegisterResponse>
    suspend fun updateUser(id:Int, requestModel: RegisterRequest): Result<User>
}