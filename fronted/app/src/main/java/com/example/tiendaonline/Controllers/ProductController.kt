package com.example.tiendaonline.Controllers
import com.example.tiendaonline.Repository.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductController {



    private suspend fun getProducts(token:String){
        val products = ProductsRepository().getAll()

        products.onSuccess { product->
//            val adapter = ArrayAdapter(
//                this@ProductController,
//                android.R.layout.simple_list_item_1,
//                product
//            )
        }






    //            ServiceBuilder.token = token
//        val iProducts =  ServiceBuilder.buildService(IProducts::class.java)
//            iProducts.getAll().enqueue(object : Callback<List<Product>> {
//                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
//                    if (response.isSuccessful) {
//                        val products = response.body()
//                        products?.forEach{
//                           println(it)//imprimir productos
//                        }
//                           // Maneja la respuesta aquí
//                        } else {
//                           // Maneja el error aquí
//                           println("${response.code()}")
//                        }
//                    }
//                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
//                    // Maneja el error aquí
//                }
//        })





    }
    fun getAll(token: String){CoroutineScope(Dispatchers.IO).launch {getProducts(token)}}//Hilos
}