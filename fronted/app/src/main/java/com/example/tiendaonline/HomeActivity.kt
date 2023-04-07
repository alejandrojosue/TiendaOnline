package com.example.tiendaonline
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiendaonline.Controllers.AuthActivityController
import com.example.tiendaonline.Network.SocketIOManager
import com.example.tiendaonline.Repository.ProductsRepository
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        title = "Home"
        events()
        SocketIOManager.setSocket()
        SocketIOManager.establishConnection()
        val mSocket = SocketIOManager.getSocket()
        mSocket.on("products"){
            if(it[0] != null){
                runOnUiThread{
                    tv.setText(it[0].toString())
//                    btn.performClick()
                }
            }
        }
        mSocket.emit("bla", "valor")
    }
    private fun events(){
        val products = ProductsRepository()
        btn.setOnClickListener{
            try {
                GlobalScope.launch {
                    val res = products.getAll()
                    if(res.isSuccess){
                        res.getOrNull()?.forEach {
                            println(it.description)
                        }
                    }else{
                        println("Error failed: ${res.exceptionOrNull()}")
                        startActivity(Intent(this@HomeActivity,AuthActivityController::class.java))
                    }
                }
            } catch (e: Exception) {
                println("Error catch ${e.message.toString()}")
            }
        }
    }
}