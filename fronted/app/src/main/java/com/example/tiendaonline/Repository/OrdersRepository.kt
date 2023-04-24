package com.example.tiendaonline.Repository

import com.example.tiendaonline.Models.Orders.Order
import com.example.tiendaonline.Services.ApiServices.IOrder
import com.example.tiendaonline.Services.ApiServices.IProducts
import com.example.tiendaonline.Services.ServiceBuilder
import retrofit2.awaitResponse

class OrdersRepository: IOrdersRepository {
    override suspend fun createOrder(order: Order){
        try{
            val iOrder =  ServiceBuilder.buildService(IOrder::class.java)
            val response = iOrder.create(order).awaitResponse()
            if(response.isSuccessful){
                println(response.body())
            }else{
                println("${response.code()}")
                println("${response.errorBody()}")
            }
        }catch (e:Exception){
            println(e.message)
        }
    }
}