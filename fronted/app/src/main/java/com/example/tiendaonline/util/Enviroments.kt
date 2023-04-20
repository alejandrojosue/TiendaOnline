package com.example.tiendaonline.util

import android.app.Application
import android.preference.PreferenceManager
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.ProductInformation.ProductsClient

class Enviroments: Application() {
    companion object{
        var myListOrder: MutableList<OrderDetail> = mutableListOf()
        var myListProduct: MutableList<ProductsClient> = mutableListOf()
        var amount:Double = 0.0
        var myFullNameOrder: String = ""
        var myEmailOrder: String = ""
    }
}