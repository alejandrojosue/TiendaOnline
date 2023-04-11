package com.example.tiendaonline
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tiendaonline.Adapter.ProductsAdapter
import com.example.tiendaonline.Controllers.AuthActivityController
import com.example.tiendaonline.Filter.ProductFilter
import com.example.tiendaonline.Models.Orders.Order
import com.example.tiendaonline.Models.Orders.OrderData
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.Orders.OrderProduct
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Network.SocketIOManager
import com.example.tiendaonline.Repository.OrdersRepository
import com.example.tiendaonline.Repository.ProductsRepository
import com.example.tiendaonline.databinding.ActivityHomeBinding
import com.example.tiendaonline.util.DeviceUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
class HomeActivity : AppCompatActivity() {
    //private val isTablet = DeviceUtils.isTablet(this)
    private val products = ProductsRepository()
    private  val orderRepository = OrdersRepository()
    private var orderDetailMutableList = mutableListOf<OrderDetail>()
    lateinit var myList:MutableList<ProductsClient>
    private lateinit var adapter:ProductsAdapter
    private lateinit var binding:ActivityHomeBinding
    private val productFilter = ProductFilter()
    var amount:Double = 0.0
    private var columsNumber = 1
    //La cantidad de columnas a mostrar
    private var manager = GridLayoutManager(this,columsNumber)
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Home"
         requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        if(conexion()){
            events()
        }
        sockets()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // La pantalla está en modo horizontal
            manager = GridLayoutManager(this,2)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // La pantalla está en modo vertical
            manager = GridLayoutManager(this,columsNumber)
        }
        binding.rvProducts.layoutManager = manager
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
            if(orderDetailMutableList.size>0){
                crearOrden("Melissa")
            }else{
                Toast.makeText(this, "No ha comprado nada",Toast.LENGTH_LONG).show()
            }
            //products.updateQuantity(1, 1)
//            crearOrden()
        }
        btn3.setOnClickListener{
            startActivity(Intent(this,AuthActivityController::class.java))
        }
    }
    fun addListado(quantity:Int,productId:Int) = orderDetailMutableList.add(OrderDetail(quantity, OrderProduct(productId)))
    fun removeProductoListado(id:Int) = orderDetailMutableList.remove(orderDetailMutableList.single{it.product.id == id}  )
    private fun crearOrden(clientName:String){
        GlobalScope.launch {
            orderRepository.createOrder(Order(OrderData(amount,clientName,orderDetailMutableList.toList())))
            orderDetailMutableList.clear()
            amount=0.0
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
                                adapter.updateList(ProductFilter().filterProduct(productFilter, myList))
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
        GlobalScope.launch {
            val mSocket = SocketIOManager.getSocket()
            mSocket.on("products"){
                if(it[0] != null){
                    runOnUiThread{
                        try {
                            val response = JSONObject(it[0].toString())
                            val x = myList.indexOf(myList.single { it.id == response.getInt("id") })
                            if(response.get("publishedAt").equals(null)){
                                products.clearCache()
                                removeItemRecycleview(x)
                            }
                            updateRecycleview(response, x)
                            tv.setText("${response.get("publishedAt")}${response.get("Name")}${response.get("Description")}${response.get("Price")}${response.getInt("Quantity")}")
                        }catch (e:java.lang.Exception){
                            println(e.message)
                        }
                    }
                }
            }
        }
    }
    private fun removeItemRecycleview(x:Int){
        myList.removeAt(x)
        adapter.notifyItemRemoved(x)
    }
    private fun updateRecycleview(response:JSONObject, x:Int){
        val productUpdate = ProductsClient(response.getInt("id"),response.getString("SKU"),response.getString("Name"),response.getDouble("Price"),response.getInt("Quantity"),myList.get(x).subcategory,myList.get(x).color,response.getString("Description"),myList.get(x).img)
        myList.set(x,productUpdate)
        adapter.notifyItemChanged(x)
    }
    private fun initRecycleview(){
        adapter = ProductsAdapter(myList) { productsClient -> onItemClick(productsClient) /*lambda*/ }
        binding.rvProducts.layoutManager = manager
        binding.rvProducts.adapter = adapter
    }
    private fun onItemClick(productsClient: ProductsClient){
        if(productsClient.Quantity!!>0){
            addListado(1,productsClient.id!!)
            amount+=productsClient.Price!!
            GlobalScope.launch { products.updateQuantity(productsClient.id!!, productsClient.Quantity!!-1) }
        }else{
            Toast.makeText(this, "El producto ${productsClient.Name} está agotado!", Toast.LENGTH_LONG).show()
        }
    }
}