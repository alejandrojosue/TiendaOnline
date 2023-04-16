package com.example.tiendaonline.Controllers
import com.example.tiendaonline.Repository.ProductsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeController {
    private suspend fun getProducts(token:String){

        val products = ProductsRepository().getAll()

        products.onSuccess { product->
//            val adapter = ArrayAdapter(
//                this@ProductController,
//                android.R.layout.simple_list_item_1,
//                product
//            )
        }


    }
    fun getAll(token: String){CoroutineScope(Dispatchers.IO).launch {getProducts(token)}}//Hilos
}