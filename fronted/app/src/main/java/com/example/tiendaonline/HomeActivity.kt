package com.example.tiendaonline
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tiendaonline.Adapter.ProductsAdapter
import com.example.tiendaonline.Controllers.AuthActivityController
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Network.SocketIOManager
import com.example.tiendaonline.Repository.ProductsRepository
import com.example.tiendaonline.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
class HomeActivity : AppCompatActivity() {
    private val products = ProductsRepository()
    private lateinit var adapter:ProductsAdapter
    lateinit var myList:MutableList<ProductsClient>
    private lateinit var binding:ActivityHomeBinding
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Home"
        if(conexion()){

        }
        events()
        sockets()
    }
    fun conexion():Boolean{
        val conexionInfo = ((getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)).activeNetworkInfo
        return (conexionInfo!=null && conexionInfo.isConnected)
    }
    private fun events(){
        btn.setOnClickListener {
            if(conexion()){
                mostrarProductos()
            }else{
                Toast.makeText(this, "SIN INTERNET",Toast.LENGTH_LONG).show()
            }
        }
        etSearch.addTextChangedListener{
            if(rvProducts.size > 0)  mostrarProductos(it.toString())
            if(etSearch.text.length == 0)  mostrarProductos()
        }
        btn2.setOnClickListener{
            GlobalScope.launch {
                products.updateQuantity(1, 1)
            }
            //startActivity(Intent(this,AuthActivityController::class.java))
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
                    }
                }
            } catch (e: Exception) {
                println("Error catch ${e.message.toString()}")
            }
    }
    private fun sockets(){
        SocketIOManager.setSocket()
        SocketIOManager.establishConnection()
        val mSocket = SocketIOManager.getSocket()
        mSocket.on("products"){
            if(it[0] != null){
                runOnUiThread{
                    try {
                        val response = JSONObject(it[0].toString())
                        if(response.get("publishedAt").equals(null)){
                            products.clearCache()
                        }
                        val x = myList.indexOf(myList.single { it.id == response.getInt("id") })
                        val productUpdate = ProductsClient(response.getInt("id"),response.getString("SKU"),response.getString("Name"),response.getDouble("Price"),response.getInt("Quantity"),myList.get(x).subcategory,myList.get(x).color,response.getString("Description"),myList.get(x).img)
                        myList.set(x,productUpdate)
                        mostrarProductos()
                        tv.setText("${response.get("publishedAt")}${response.get("Name")}${response.get("Description")}${response.get("Price")}${response.getInt("Quantity")}")
                    }catch (e:java.lang.Exception){
                        println(e.message)
                    }
                }
            }
        }
        mSocket.emit("bla", "valor")
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
        try {
            val productFiltered = myList.filter {
                    productsClient -> productsClient.Name!!.lowercase().contains(productFilter.lowercase()) || productsClient.description!!.lowercase().contains(productFilter.lowercase())
            }
            adapter.updateList(productFiltered)
        }catch (e:java.lang.Exception){}
    }
    private fun onItemClick(productsClient: ProductsClient){
        Toast.makeText(this, productsClient.description,Toast.LENGTH_LONG).show()
    }
}