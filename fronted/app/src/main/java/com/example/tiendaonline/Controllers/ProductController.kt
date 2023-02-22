package com.example.tiendaonline.Controllers

import com.example.tiendaonline.EntityModels.Repository.Product
import com.example.tiendaonline.Services.ApiServices.IProductsRepository
import com.example.tiendaonline.Services.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductController {
    private fun getProducts(token:String){
            ServiceBuilder.token = token
        val iProductsRepository =  ServiceBuilder.buildService(IProductsRepository::class.java)
            iProductsRepository.getAll().enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    if (response.isSuccessful) {
                        val products = response.body()
                        products?.forEach{
                           println(it)//imprimir productos
                        }
                           // Maneja la respuesta aquí
                        } else {
                           // Maneja el error aquí
                           println(response.code())
                        }
                    }
                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    // Maneja el error aquí
                }
        })
    }
    fun getAll(token: String){CoroutineScope(Dispatchers.IO).launch {getProducts(token)}}//Hilos
}