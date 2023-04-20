package com.example.tiendaonline.ui.ConfirmShopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tiendaonline.Adapter.ViewPagesAdapter
import com.example.tiendaonline.R
import kotlinx.android.synthetic.main.fragment_view_pages.view.*

class ViewPagesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_view_pages, container, false)
        val fragmentList = arrayListOf<Fragment>(
            DataClientFragment(),
            ShoppingFinishFragment()
        )
        val adapter = ViewPagesAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        view.viewPager2.adapter = adapter
        return view
    }
}