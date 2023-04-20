package com.example.tiendaonline.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tiendaonline.Adapter.ProductsAdapter
import com.example.tiendaonline.Filter.ProductFilter
import com.example.tiendaonline.Maper.OrderMap
import com.example.tiendaonline.Middlewares.NetworkUtils
import com.example.tiendaonline.Models.Categories.CategoryClient
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.Orders.OrderProduct
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Models.Subcategories.SubcategoriesClient
import com.example.tiendaonline.Network.SocketIOManager
import com.example.tiendaonline.Repository.CategoriesRepository
import com.example.tiendaonline.Repository.ProductsRepository
import com.example.tiendaonline.Repository.SubcategoriesRepository
import com.example.tiendaonline.databinding.FragmentHomeBinding
import com.example.tiendaonline.util.DialogMessage
import com.example.tiendaonline.util.Enviroments
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val products = ProductsRepository()
    private val categoriesRepository = CategoriesRepository()
    private val subcategoriesRepository = SubcategoriesRepository()
    lateinit var myList:MutableList<ProductsClient>
    lateinit var myListCategories: List<CategoryClient>
    lateinit var myListSubcategories: List<SubcategoriesClient>
    private lateinit var adapter: ProductsAdapter
    private lateinit var binding: FragmentHomeBinding
    private val productFilter = ProductFilter()
    private var columsNumber = 1
    //La cantidad de columnas a mostrar
    private var manager = GridLayoutManager(context, columsNumber)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events()
        sockets()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // La pantalla está en modo horizontal
            manager = GridLayoutManager(context, 2)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // La pantalla está en modo vertical
            manager = GridLayoutManager(context, columsNumber)
        }
        binding.rvProducts.layoutManager = manager
    }

    private fun events(){
        mostrarProductos()
        mostrarCategorias()
        mostrarSubcategorias()
        mostrarSubcategoriasFiltros()
        binding.btn.setOnClickListener {
            if(NetworkUtils.isConnected(requireContext())){
                mostrarProductos()
            }else{
                Toast.makeText(context, "SIN INTERNET", Toast.LENGTH_LONG).show()
            }
        }
        binding.etSearch.addTextChangedListener{
            if(rvProducts.size > 0)  mostrarProductos(it.toString())
            if(binding.etSearch.text.length == 0)  mostrarProductos()
        }
        //refrescar el recycleview
        binding.swipe.setOnRefreshListener {
            mostrarProductos()
            binding.chipTodo.isChecked = true
            binding.swipe.isRefreshing = false
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

    private fun filtrarSubcategoria(categoria:String) = adapter.updateList(productFilter.filterProductBySubcategory(categoria, myList))
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
    private fun mostrarCategorias(){}
    fun addListado(quantity:Int,productId:Int) = Enviroments.myListOrder.add(
        OrderDetail(
            quantity,
            OrderProduct(productId)
        )
    )

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
        val productUpdate = ProductsClient(
            response.getInt("id"),
            response.getString("SKU"),
            response.getString("Name"),
            response.getDouble("Price"),
            response.getInt("Quantity"),
            myList.get(x).subcategory,
            myList.get(x).color,
            response.getString("Description"),
            myList.get(x).img
        )
        myList.set(x,productUpdate)
        adapter.notifyItemChanged(x)
    }
    private fun initRecycleview(){
        adapter =
            ProductsAdapter(myList) { productsClient -> onItemClick(productsClient) /*lambda*/ }
        val decoration = DividerItemDecoration(context, manager.orientation)
        binding.rvProducts.layoutManager = manager
        binding.rvProducts.adapter = adapter
        binding.rvProducts.addItemDecoration(decoration)
    }
    private fun onItemClick(_productsClient: ProductsClient) = dialog(_productsClient)
    private fun dialog(_productsClient: ProductsClient){
        DialogMessage(
            onSubmitClickListener = { quantity ->
                if (_productsClient.Quantity!! > 0) {
                    addListado(quantity, _productsClient.id!!)
                    Enviroments.amount += _productsClient.Price!!
                    GlobalScope.launch {
                        products.updateQuantity(
                            _productsClient.id!!,
                            _productsClient.Quantity!! - quantity
                        )
                        Enviroments.myListProduct.add(_productsClient)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "El producto ${_productsClient.Name} está agotado!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            productsClient = _productsClient
        ).show(parentFragmentManager, "dialog")
    }

}