package com.example.tiendaonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiendaonline.Controllers.AuthActivityController
import com.example.tiendaonline.Repository.ProductsRepository
import kotlinx.android.synthetic.main.activity_home.*

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        title = "Home"
        val products = ProductsRepository()
        btn.setOnClickListener{
            println("Aqui esta el listado")
            try {
                GlobalScope.launch {
                    val res = products.getAll()
                    if(res.isSuccess){
                         res.getOrNull()?.forEach {
                            println(it.Name)
                        }
                    }else{

                        println("Error failed: ${res.exceptionOrNull()}")
                        startActivity(Intent(this@HomeActivity,AuthActivityController::class.java))
                    }
                    println("final")
                }
            } catch (e: Exception) {

                println("Error catch ${e.message.toString()}")
            }
        }

    }
}