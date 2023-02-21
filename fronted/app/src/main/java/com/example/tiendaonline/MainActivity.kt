package com.example.tiendaonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tiendaonline.EntityModels.Repository.*
import com.example.tiendaonline.Services.ApiServices.ILoginRepository
import com.example.tiendaonline.Services.ApiServices.IProductsRepository
import com.example.tiendaonline.Services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestModel = LoginRequest("user@gmail.com", "strapiPassword")

        val response = ServiceBuilder.buildService(ILoginRepository::class.java)
        response.sendReq(requestModel).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val token = response.body()?.jwt.toString()
//                    println(token)


                    Toast.makeText(this@MainActivity,token, Toast.LENGTH_LONG).show()
                    val iProductsRepository =  ServiceBuilder.buildService(IProductsRepository::class.java)
                        iProductsRepository.getAll().enqueue(object : Callback<List<Product>> {
                            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                                if (response.isSuccessful) {
                                    val products = response.body()
                                    products?.forEach{
                                        println(it)
                                    }

                                    // Maneja la respuesta aquí
                                    Toast.makeText(this@MainActivity,"products", Toast.LENGTH_LONG).show()
                                } else {
                                    // Maneja el error aquí
                                    println(response.code())
                                    Toast.makeText(this@MainActivity,response.message().toString(), Toast.LENGTH_LONG).show()

                                }
                            }

                            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                                // Maneja el error aquí
                                Toast.makeText(this@MainActivity,"products error${t.message.toString()}", Toast.LENGTH_LONG).show()

                            }
                        })
                    }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity,t.toString(), Toast.LENGTH_LONG).show()

                }
            }
        )
    }
}