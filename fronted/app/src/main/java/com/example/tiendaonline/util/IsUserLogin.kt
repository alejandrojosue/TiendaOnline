package com.example.tiendaonline.util

import android.content.Context
import android.preference.PreferenceManager
import android.widget.TextView
import com.example.tiendaonline.R
import com.google.android.material.navigation.NavigationView

object IsUserLogin {
    fun isLogin(context: Context, navigationView:NavigationView, logout:Boolean = false){
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        var fullName = preferences.getString("fullName","Mi nombre")
        var email = preferences.getString("email","myUser@gmail.com")
        val headerView = navigationView.getHeaderView(0)
        val emailHeader = headerView.findViewById<TextView>(R.id.header_email)
        val fullnameHeader = headerView.findViewById<TextView>(R.id.header_fullName)
        if(logout){
            fullName = "Mi nombre"
            email = "myUser@gmail.com"
        }
        emailHeader.setText(email)
        fullnameHeader.setText(fullName)
    }
}