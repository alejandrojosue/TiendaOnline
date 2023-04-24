package com.example.tiendaonline.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Repository.ProductsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Enviroments: Application() {
    private val productsRepository = ProductsRepository()
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
//                updateProductsOnClose()
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity
//                updateProductsOnClose(-1)
            }

            override fun onActivityStarted(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityDestroyed(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityStopped(activity: Activity) {guardarListasEnPreferencias()}

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        })
        //updateProductOnOpen()
    }

    private fun updateProductOnOpen(){
        cargarListasDesdePreferencias()
        if(myListProduct.size > 0){
            try {
                GlobalScope.launch {
                    var i = -1
                    for (productsClient in myListProduct) {
                        i++
                        val response = productsRepository.getById(productsClient.id!!)
                        if(response.isSuccess && myListOrder.size > 0){
                            productsRepository.updateQuantity(productsClient.id!!, response.getOrNull()!!.Quantity!! - myListOrder[i].Quantity!!)
                        }
                    }
                }
            }catch (e:Exception){}
        }
    }

    fun updateProductsOnClose(sign:Int = 1){
        if(myListProduct.size > 0){
            try {
                GlobalScope.launch {
                    var i = -1
                    if(!myListProduct.isNullOrEmpty()){
                        for (productsClient in myListProduct) {
                            i++
                            val response = productsRepository.getById(productsClient.id!!)
                            if(response.isSuccess && myListOrder.size > 0){
                                productsRepository.updateQuantity(productsClient.id, response.getOrNull()!!.Quantity!! + myListOrder[i].Quantity!!*(sign))
                            }
                        }
                    }
                }
            }catch (e:Exception){}
        }
    }
    fun guardarListasEnPreferencias() {
        val sharedPrefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("myListOrder", Gson().toJson(myListOrder))
        editor.putString("myListProduct", Gson().toJson(myListProduct))
        editor.putFloat("amount", amount.toFloat())
        editor.apply()
    }

    private fun cargarListasDesdePreferencias() {
        val sharedPrefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        if(!sharedPrefs.getString("myListOrder", "").isNullOrEmpty()){
            myListOrder = Gson().fromJson(sharedPrefs.getString("myListOrder", ""), object : TypeToken<MutableList<OrderDetail>>() {}.type)
            myListProduct = Gson().fromJson(sharedPrefs.getString("myListProduct", ""), object : TypeToken<MutableList<ProductsClient>>() {}.type)
            amount = sharedPrefs.getFloat("amount", 0F).toDouble()
        }
    }
    companion object{
        var myListOrder: MutableList<OrderDetail> = mutableListOf()
        var myListProduct: MutableList<ProductsClient> = mutableListOf()
        var amount:Double = 0.0
        var myFullNameOrder: String = ""
        var myEmailOrder: String = ""
        var currentActivity: Activity? = null
    }
}