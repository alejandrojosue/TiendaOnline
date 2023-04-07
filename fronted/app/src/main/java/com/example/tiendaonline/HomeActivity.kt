package com.example.tiendaonline
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.Adapter.ProductsAdapter
import com.example.tiendaonline.Controllers.AuthActivityController
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Network.SocketIOManager
import com.example.tiendaonline.Repository.ProductsRepository
import com.example.tiendaonline.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
class HomeActivity : AppCompatActivity() {
    private val products = ProductsRepository()
    private lateinit var adapter:ProductsAdapter
    private lateinit var myList:List<ProductsClient>
    private lateinit var binding:ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Home"
        events()
        SocketIOManager.setSocket()
        SocketIOManager.establishConnection()
        val mSocket = SocketIOManager.getSocket()
        mSocket.on("products"){
            if(it[0] != null){
                runOnUiThread{
                    tv.setText(it[0].toString())
                    products.clearCache()
                    btn.performClick()
                }
            }
        }
        mSocket.emit("bla", "valor")
    }
    private fun events(){
        btn.setOnClickListener{
            mostrarProductos()
        }
        etSearch.addTextChangedListener{
            mostrarProductos(it.toString())
        }
    }
    private fun mostrarProductos(productFilter:String = ""){
        try {
            GlobalScope.launch {
                val res = products.getAll()
                if(res.isSuccess){
                    myList = res.getOrNull()!!
                    runOnUiThread{
                        if(productFilter.trim().equals("")){
                            initRecycleview()
                        }else{
                            filterRecycleView(productFilter.trim())
                        }
                    }
                }else{
                    println("Error failed: ${res.exceptionOrNull()}")
//                    startActivity(Intent(this@HomeActivity,AuthActivityController::class.java))
                }
            }
        } catch (e: Exception) {
            println("Error catch ${e.message.toString()}")
        }
    }
    private fun initRecycleview(){
            adapter = ProductsAdapter(myList) { productsClient -> onItemClick(productsClient) /*lambda*/ }
            //crear separación de items
            val columsNumber = 1
            val manager = GridLayoutManager(this,columsNumber) //La cantidad de columnas a mostrar
            binding.rvProducts.layoutManager = manager
            binding.rvProducts.adapter = adapter
    }
    private fun filterRecycleView(productFilter:String){
            val productFiltered = myList?.filter {
                    productsClient -> productsClient.Name!!.toLowerCase().contains(productFilter.toLowerCase())
            }
            adapter.updateList(productFiltered!!)
    }
    private fun onItemClick(productsClient: ProductsClient){
        Toast.makeText(this, productsClient.description,Toast.LENGTH_LONG).show()
    }
}