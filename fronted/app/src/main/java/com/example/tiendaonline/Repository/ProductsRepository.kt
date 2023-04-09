package com.example.tiendaonline.Repository

import android.os.Parcel
import android.os.Parcelable
import android.util.JsonWriter
import android.util.LruCache
import com.example.tiendaonline.Maper.ProductMap
import com.example.tiendaonline.Models.Orders.DataProductOrder
import com.example.tiendaonline.Models.Orders.ProductUpdate
import com.example.tiendaonline.Models.ProductInformation.Products
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Services.ApiServices.IProducts
import com.example.tiendaonline.Services.ServiceBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.JsonSerializationContext
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.awaitResponse

class ProductsRepository() :IProductsRepository {
    private val maxCache = 5 * 1024 * 1024 // 5MB
    val productListCache = LruCache<Int,MutableList<ProductsClient>>(maxCache)
    override suspend fun getAll(): Result<MutableList<ProductsClient>> {
        return try{
            // 1 es la clave. Ésta puede ser cualquier número
            if(productListCache.size() == 0){
                val iProducts =  ServiceBuilder.buildService(IProducts::class.java)
                val response = iProducts.getAll().awaitResponse()
                if(response.isSuccessful){
                    //response devuelve un Product, lo mapeamos para que devuelva un ProductCliente
                    //ponemos valores en la caché
//                    productCache.put(1,response.body()?.map { ProductMap.mapearV3(it) })
                    productListCache.put(1,(response.body()?.data)?.map { ProductMap.mapearV4(it) } as MutableList)
                    Result.success(productListCache[1])
                }else{
                    Result.failure(Exception(response.message()))
                }
            }else{
                Result.success(productListCache[1])
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
    override suspend fun updateQuantity(id: Int, quantity: Int) {
        val iProducts =  ServiceBuilder.buildService(IProducts::class.java)
        val data = DataProductOrder(quantity)
        var productUpdate = ProductUpdate(data)
        try {
            val response = iProducts.putQuantity(id, productUpdate).awaitResponse()
            if (response.isSuccessful){
                println("Correcto")
            }
        }catch (e:java.lang.Exception){
        }
    }
    override fun clearCache() = productListCache.evictAll()
}