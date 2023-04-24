package com.example.tiendaonline.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tiendaonline.Adapter.OrdersAdapter
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Repository.ProductsRepository
import com.example.tiendaonline.databinding.FragmentShoppingBinding
import com.example.tiendaonline.ui.ConfirmShopping.Confirm_ShoppingActivity
import com.example.tiendaonline.util.Enviroments
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingFragment : Fragment() {
    private lateinit var adapter: OrdersAdapter
    private lateinit var binding: FragmentShoppingBinding
    private var columsNumber = 1
    private var manager = GridLayoutManager(context, columsNumber)
    private val productsRepository = ProductsRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
        if(Enviroments.myListOrder.size==0) binding.tvReport.isVisible = true
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentShoppingBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.rvOrder.layoutManager = manager
    }

    private fun setUp(){
        initRecycleView()
        binding.tvTotalaPagar.setText("L. ${Enviroments.amount}")
        binding.btnComprar.setOnClickListener {
            if (Enviroments.myListOrder.size > 0) startActivity(
                Intent(
                    context,
                    Confirm_ShoppingActivity::class.java
                )
            ) else Toast.makeText(context, "No ha comprado nada", Toast.LENGTH_LONG).show()
        }
    }
    private fun onDeletedItem(position:Int, quantity: Int){
        GlobalScope.launch {
            try {
                val response = productsRepository.getById(Enviroments.myListProduct[position].id!!)
                if(response.isSuccess){
                    val product: ProductsClient = response.getOrNull()!!
                    product.Quantity!!.let {
                        productsRepository.updateQuantity(
                            Enviroments.myListProduct[position].id!!,
                            (it + quantity)
                        )
                    }
                }else{
                    println(response.exceptionOrNull())
                }
            }catch (e:Exception){
                println(e.message)
            }
            activity?.runOnUiThread {
                removeItemRecycleview(position)
            }
        }
        Enviroments.amount -= Enviroments.myListOrder[position].Quantity * Enviroments.myListProduct[position].Price!!
        binding.tvTotalaPagar.setText("L. ${Enviroments.amount}")
    }

    private fun removeItemRecycleview(position: Int) {
        Enviroments.myListProduct.removeAt(position)
        Enviroments.myListOrder.removeAt(position)
        initRecycleView()
    }

    private fun onItemSelected(orderDetail: OrderDetail){}
    private fun initRecycleView(){
        if(Enviroments.myListOrder.size==0) binding.tvReport.isVisible = true
        adapter = OrdersAdapter(
            productList = Enviroments.myListProduct.toList(),
            onClickListener = { orderDetail -> onItemSelected(orderDetail) },
            onClickDelete = { position, quantity -> onDeletedItem(position, quantity) },
            orderDetailList = Enviroments.myListOrder.toList(),
            onClickMinusPlus = {position, isPlus, quantity -> onChangeQuantity(position,isPlus, quantity)}
        )
        binding.rvOrder.layoutManager = manager
        binding.rvOrder.adapter = adapter
        binding.rvOrder.addItemDecoration(DividerItemDecoration(context, manager.orientation))
    }
    private fun onChangeQuantity(position: Int, isPlusButton: Boolean, quantity: Int){
        GlobalScope.launch {
            var x = 1
            try {
                val response = productsRepository.getById(Enviroments.myListProduct[position].id!!)
                if (response.isSuccess) {
                    val product: ProductsClient = response.getOrNull()!!
                    if(isPlusButton) {
                        x = -1
                        Enviroments.amount += Enviroments.myListProduct[position].Price!!
                    }else{
                        Enviroments.amount -= Enviroments.myListProduct[position].Price!!
                    }
                    product.Quantity.let {
                        productsRepository.updateQuantity(
                            Enviroments.myListProduct[position].id!!,
                            it!! + x
                        )
                    }
                    Enviroments.myListOrder[position].Quantity -= x
                    activity?.runOnUiThread{binding.tvTotalaPagar.setText("L. ${Enviroments.amount}")}
                }
            }catch (e:Exception){}
        }
    }
}