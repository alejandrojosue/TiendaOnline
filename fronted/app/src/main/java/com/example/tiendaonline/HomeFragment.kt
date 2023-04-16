package com.example.tiendaonline

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tiendaonline.Adapter.ProductsAdapter
import com.example.tiendaonline.Filter.ProductFilter
import com.example.tiendaonline.Filter.SubcategoryFilter
import com.example.tiendaonline.Models.Categories.CategoryClient
import com.example.tiendaonline.Models.Orders.Order
import com.example.tiendaonline.Models.Orders.OrderData
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.Orders.OrderProduct
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Models.Subcategories.SubcategoriesClient
import com.example.tiendaonline.Network.SocketIOManager
import com.example.tiendaonline.Repository.CategoriesRepository
import com.example.tiendaonline.Repository.OrdersRepository
import com.example.tiendaonline.Repository.ProductsRepository
import com.example.tiendaonline.Repository.SubcategoriesRepository
import com.example.tiendaonline.databinding.ActivityHomeBinding
import com.example.tiendaonline.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val products = ProductsRepository()
    private  val orderRepository = OrdersRepository()
    private val categoriesRepository = CategoriesRepository()
    private val subcategoriesRepository = SubcategoriesRepository()
    private var orderDetailMutableList = mutableListOf<OrderDetail>()
    lateinit var myList:MutableList<ProductsClient>
    lateinit var myListCategories: List<CategoryClient>
    lateinit var myListSubcategories: List<SubcategoriesClient>
    private lateinit var adapter: ProductsAdapter
    private lateinit var binding: FragmentHomeBinding //= FragmentHomeBinding.inflate(layoutInflater)
    private val productFilter = ProductFilter()
    private val subcategoryFilter = SubcategoryFilter()
    var amount:Double = 0.0
    private var columsNumber = 1
    //La cantidad de columnas a mostrar
    private var manager = GridLayoutManager(context,columsNumber)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Inicializar el LayoutInflater
        binding = FragmentHomeBinding.inflate(layoutInflater)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(conexion()){
            events()
        }
        sockets()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root //inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // La pantalla está en modo horizontal
            manager = GridLayoutManager(context,2)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // La pantalla está en modo vertical
            manager = GridLayoutManager(context,columsNumber)
        }
        binding.rvProducts.layoutManager = manager
    }
    fun conexion(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return (networkInfo != null && networkInfo.isConnected)
    }

    private fun events(){
        mostrarProductos()
        mostrarCategorias()
        mostrarSubcategorias()
        mostrarSubcategoriasFiltros()
        binding.btn.setOnClickListener {
            if(conexion()){
                mostrarProductos()
            }else{
                Toast.makeText(context, "SIN INTERNET", Toast.LENGTH_LONG).show()
            }
        }
        binding.etSearch.addTextChangedListener{
            if(rvProducts.size > 0)  mostrarProductos(it.toString())
            if(binding.etSearch.text.length == 0)  mostrarProductos()
        }
        binding.btn2.setOnClickListener{
            if(orderDetailMutableList.size>0){
                crearOrden("Melissa")
            }else{
                Toast.makeText(context, "No ha comprado nada", Toast.LENGTH_LONG).show()
            }
            //products.updateQuantity(1, 1)
//            crearOrden()
        }
        binding.btn3.setOnClickListener{
            //startActivity(Intent(this,LoginActivity::class.java))
        }
        //refrescar el recycleview
        binding.swipe.setOnRefreshListener {
            mostrarProductos()
            binding.chipTodo.isChecked = true
            binding.swipe.isRefreshing = false
        }
        binding.mode.setOnCheckedChangeListener {
                _, changeState ->
            if(changeState){
//                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
//                delegate.applyDayNight()
            }else{
//                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
//                delegate.applyDayNight()
            }
        }
    }

    private fun mostrarSubcategoriasFiltros() {
        binding.chipGroup.setOnCheckedChangeListener{group, checkedId ->
            try{
                val chip = binding.chipGroup.findViewById<Chip>(checkedId)
                if(chip.isChecked){
                    if(chip.text.toString().trim().equals("Todo")) mostrarProductos() else filtrarSubcategoria(chip.text.toString())
                }
            }catch (e:Exception){
                println(e.message)
            }

        }
    }

    private fun filtrarSubcategoria(categoria:String){
        adapter.updateList(productFilter.filterProductBySubcategory(categoria, myList))
    }
    private fun mostrarSubcategorias(){
        GlobalScope.launch {
            val subcategories = subcategoriesRepository.get()
            if(subcategories.isSuccess){
                if(subcategories.isSuccess){
                    myListSubcategories = subcategories.getOrNull()!!
                    myListSubcategories.forEach {category->
                        activity?.runOnUiThread {
                            val chip = Chip(context)
                            chip.setText(category.Name)
                            chip.isCheckable = true

                            binding.chipGroup.addView(chip)
                        }
                    }
                }else{
                    println("Error")
                }
            }
        }
    }
    private fun mostrarCategorias(){
//        GlobalScope.launch {
//            val categories = categoriesRepository.getCategories()
//            if(categories.isSuccess){
//                myListCategories = categories.getOrNull()!!
//                myListCategories.forEach {category->
//                    runOnUiThread {
//                        val chip = Chip(this@HomeActivity)
//                        chip.setText(category.Name)
//                        chip.isCheckable = true
//                        binding.chipGroup.addView(chip)
//                    }
//                }
//            }else{
//                println("Error")
//            }
//        }
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
                    activity?.runOnUiThread{
                        if(productFilter.trim().equals("")){
                            initRecycleview()
                        }else{
                            adapter.updateList(ProductFilter().filterProduct(productFilter, myList))
                        }
                        isLoading.isVisible = false
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
                    activity?.runOnUiThread{
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
    private fun updateRecycleview(response: JSONObject, x:Int){
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
//        if(productsClient.Quantity!!>0){
//            addListado(1,productsClient.id!!)
//            amount+=productsClient.Price!!
//            GlobalScope.launch { products.updateQuantity(productsClient.id!!, productsClient.Quantity!!-1) }
//        }else{
//            Toast.makeText(this, "El producto ${productsClient.Name} está agotado!", Toast.LENGTH_LONG).show()
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}