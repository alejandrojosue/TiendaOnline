package com.example.tiendaonline.util

import android.content.Context
import android.widget.Toast

object ToastMessage {
    fun getToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}