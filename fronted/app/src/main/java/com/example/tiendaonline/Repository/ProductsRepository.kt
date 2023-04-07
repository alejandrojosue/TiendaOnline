package com.example.tiendaonline.Repository

import android.os.Parcel
import android.os.Parcelable
import android.util.LruCache
import com.example.tiendaonline.Maper.ProductMap
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Services.ApiServices.IProducts
import com.example.tiendaonline.Services.ServiceBuilder
import retrofit2.awaitResponse

class ProductsRepository() :IProductsRepository {
    private val maxCache = 5 * 1024 * 1024 // 5MB
    private val productCache = LruCache<Int,List<ProductsClient>>(maxCache)
    override suspend fun getAll(): Result<List<ProductsClient>> {
        return try{
            // 1 es la clave. Ésta puede ser cualquier numero
            if(productCache.size() == 0){
                val iProducts =  ServiceBuilder.buildService(IProducts::class.java)
                val response = iProducts.getAll().awaitResponse()
                if(response.isSuccessful){
                    //response devuelve un Product, lo mapeamos para que devuelva un ProductCliente
                    //ponemos valores en la caché
//                    productCache.put(1,response.body()?.map { ProductMap.mapearV3(it) })
                    productCache.put(1,(response.body()?.data)?.map { ProductMap.mapearV4(it) })
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
    override fun clearCache() = productCache.evictAll()
}