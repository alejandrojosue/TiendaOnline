package com.example.tiendaonline.Repository

import com.example.tiendaonline.Models.Users.*
import com.example.tiendaonline.Services.ApiServices.IUser
import com.example.tiendaonline.Services.ServiceBuilder
import retrofit2.awaitResponse

class UserRepository: IUserRepository {
    override suspend fun login(requestModel: LoginRequest): Result<LoginResponse> {
        return try {
            val iUser =  ServiceBuilder.buildService(IUser::class.java)
            val response = iUser.login(requestModel).awaitResponse()
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Error(response.code().toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun register(requestModel: RegisterRequest): Result<RegisterResponse> {
        return try {
            val iUser =  ServiceBuilder.buildService(IUser::class.java)
            val response = iUser.register(requestModel).awaitResponse()
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Error(response.code().toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun updateUser(id:Int, requestModel: RegisterRequest): Result<User> {
        return try {
            val iUser =  ServiceBuilder.buildService(IUser::class.java)
            val response = iUser.updateUser(id, requestModel).awaitResponse()
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Error(response.code().toString()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}