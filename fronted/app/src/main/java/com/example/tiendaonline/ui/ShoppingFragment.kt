package com.example.tiendaonline.ui

import android.content.Context
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
import com.example.tiendaonline.Models.Orders.Order
import com.example.tiendaonline.Models.Orders.OrderData
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Repository.OrdersRepository
import com.example.tiendaonline.databinding.FragmentShoppingBinding
import com.example.tiendaonline.util.Enviroments
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingFragment : Fragment() {
    private lateinit var adapter: OrdersAdapter
    private lateinit var binding: FragmentShoppingBinding
    private  val orderRepository = OrdersRepository()
    private var columsNumber = 1
    private var manager = GridLayoutManager(context, columsNumber)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Enviroments.myListOrder.size==0) binding.tvReport.isVisible = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentShoppingBinding.inflate(layoutInflater)
    }

    private fun setUp(){
        initRecycleView()
        binding.btnComprar.setOnClickListener {
            crearOrden("Alejandro")
        }
    }

    private fun initRecycleView(){
        adapter = OrdersAdapter(
            productList = Enviroments.myListProduct.toList(),
            onClickListener = { orderDetail -> onItemSelected(orderDetail) },
            onClickDelete = { position -> onDeletedItem(position) },
            orderDetailList = Enviroments.myListOrder.toList(),
        )
        val decoration = DividerItemDecoration(context, manager.orientation)
        binding.rvOrder.layoutManager = manager
        binding.rvOrder.adapter = adapter
        binding.rvOrder.addItemDecoration(decoration)

    }
    private fun onDeletedItem(position:Int){
        Enviroments.myListOrder.removeAt(position)
        adapter.notifyItemRemoved(position)
    }
    private fun onItemSelected(orderDetail: OrderDetail){

    }

    fun crearOrden(clientName:String){
        GlobalScope.launch {
            if(Enviroments.myListOrder.size>0){
                orderRepository.createOrder(
                    Order(
                        OrderData(
                            Enviroments.amount,
                            clientName,
                            Enviroments.myListOrder.toList()
                        )
                    )
                )
                Enviroments.myListOrder.clear()
                Enviroments.amount =0.0
            }else{
                Toast.makeText(context, "No ha comprado nada", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun removeProductoListado(id:Int) = Enviroments.myListOrder.remove(Enviroments.myListOrder.single{it.product.id == id}  )

}