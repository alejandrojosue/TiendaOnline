package com.example.tiendaonline.Repository

import android.util.LruCache
import com.example.tiendaonline.Maper.CategoryMap
import com.example.tiendaonline.Models.Categories.CategoryClient
import com.example.tiendaonline.Services.ApiServices.ICategory
import com.example.tiendaonline.Services.ServiceBuilder
import retrofit2.awaitResponse

class CategoriesRepository: ICategoriesRepository {
    private val maxCache = 1 * 1024 // 1KB
    val categoryListCache = LruCache<String,List<CategoryClient>>(maxCache)
    override suspend fun getCategories(): Result<List<CategoryClient>> {
        return try{
            if(categoryListCache.size()==0){
                val iCategories =  ServiceBuilder.buildService(ICategory::class.java)
                val response = iCategories.getAll().awaitResponse()
                if(response.isSuccessful){
                    categoryListCache.put("Categories", (response.body()?.data)!!.map { CategoryMap.mapearCategoria(it) })
                    Result.success(categoryListCache["Categories"])
                }else{
                    Result.failure(Exception(response.message()))
                }
            }else{
                Result.success(categoryListCache["Categories"])
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override fun clearCache() = categoryListCache.evictAll()
}