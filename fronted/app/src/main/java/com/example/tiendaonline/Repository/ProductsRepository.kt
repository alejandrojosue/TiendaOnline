package com.example.tiendaonline.Repository

import android.util.LruCache
import android.widget.Toast
import com.example.tiendaonline.Maper.ProductMap
import com.example.tiendaonline.Middlewares.Models.Product
import com.example.tiendaonline.Middlewares.Models.ProductClient
import com.example.tiendaonline.Services.ApiServices.IProducts
import com.example.tiendaonline.Services.ServiceBuilder
import com.example.tiendaonline.util.PreferenceHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class ProductsRepository:IProductsRepository{
    private val maxCache = 5 * 1024 * 1024 // 5MB
    private val productCache = LruCache<Int,List<ProductClient>>(maxCache)
    override suspend fun getAll(): Result<List<ProductClient>> {
        return try{
            // 1 es la clave. Ésta puede ser cualquier numero
            if(productCache.size() == 0){
                val iProducts =  ServiceBuilder.buildService(IProducts::class.java)
                val response = iProducts.getAll().awaitResponse()
                if(response.isSuccessful){
                    //response devuelve un Product, lo mapeamos para que devuelva un ProductCliente
                    //ponemos valores en la caché
                    productCache.put(1,response.body()?.map { ProductMap.mapear(it) })
                    Result.success(productCache[1])
                }else{
                    Result.failure(Exception(response.message()))
                }
            }else{
                Result.success(productCache[1])
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}