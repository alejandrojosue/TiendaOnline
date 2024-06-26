package com.example.tiendaonline.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagesAdapter(
    list: ArrayList<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
    ): FragmentStateAdapter(fragmentManager,lifecycle) {
    private val fragmentList = list
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

}