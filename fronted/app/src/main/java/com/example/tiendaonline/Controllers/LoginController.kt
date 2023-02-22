package com.example.tiendaonline.Controllers

import android.content.Context
import com.example.tiendaonline.EntityModels.Repository.LoginRequest
import com.example.tiendaonline.EntityModels.Repository.LoginResponse
import com.example.tiendaonline.Services.ApiServices.ILoginRepository
import com.example.tiendaonline.Services.ServiceBuilder
import com.example.tiendaonline.util.PreferenceHelper
import com.example.tiendaonline.util.PreferenceHelper.set
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginController {

    private var identifier:String = ""
    private var password:String = ""
    private lateinit var context:Context

    constructor(identifier: String,password: String, context: Context){
        this.identifier = identifier
        this.password   = password
        this.context    = context
        taskToken()
    }

    private fun setToken(){
        val requestModel = LoginRequest(this.identifier, this.password)
        val response = ServiceBuilder.buildService(ILoginRepository::class.java)

        response.sendReq(requestModel).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val preference = PreferenceHelper.defaultPrefs(context)
                    preference["token"] = response.body()?.jwt.toString()
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    println(t.message.toString())
                }
            })
    }

    private fun taskToken(){
        GlobalScope.launch {
            setToken()
        }
    }

}