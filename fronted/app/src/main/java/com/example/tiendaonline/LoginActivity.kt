package com.example.tiendaonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        btnBackLoginListener()
        txtRegisterListener()
        btnLoginListener()
    }

    private fun btnLoginListener() {
        btn_login.setOnClickListener{
            if( edt_email.text.toString().trim().isEmpty()){
                edt_email.setError("Este campo es obligatorio")
            }
            if(edt_confirmPassword.text.toString().trim().isEmpty()){
                edt_confirmPassword.setError("Este campo es obligatorio")
            }
        }
    }

    private fun btnBackLoginListener() {
        btn_back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun txtRegisterListener() {
        txt_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}