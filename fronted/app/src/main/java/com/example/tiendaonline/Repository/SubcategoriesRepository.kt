package com.example.tiendaonline.Repository

import android.util.LruCache
import com.example.tiendaonline.Maper.SubcategoryMap
import com.example.tiendaonline.Models.Subcategories.SubcategoriesClient
import com.example.tiendaonline.Services.ApiServices.ISubcategory
import com.example.tiendaonline.Services.ServiceBuilder
import retrofit2.awaitResponse

class SubcategoriesRepository: ISubcategoriesRepository {
    private val maxCache = 50 * 1024 // 50KB
    private val subCategoryListCache = LruCache<String,List<SubcategoriesClient>>(maxCache)
    override suspend fun get(): Result<List<SubcategoriesClient>> {
        return try {
            if(subCategoryListCache.size() == 0) {
                val iSubCategories = ServiceBuilder.buildService(ISubcategory::class.java)
                val response = iSubCategories.getAll().awaitResponse()
                if(response.isSuccessful){
                    //subCategoryListCache.put("Subcategories", (response.body()?.data)!!.map { SubcategoryMap.mapearSubcategoria(it) } )
                    Result.success((response.body()!!.data)!!.map { SubcategoryMap.mapearSubcategoria(it) })
                }else{
                    Result.failure(Exception(response.message()))
                }
            }else{
                Result.success(subCategoryListCache["Subcategories"])
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override fun clearCache() = subCategoryListCache.evictAll()
}