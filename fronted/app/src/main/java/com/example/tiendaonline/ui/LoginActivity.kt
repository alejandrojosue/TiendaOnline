package com.example.tiendaonline.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiendaonline.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        setUp()
    }

    private fun setUp(){
        btn_login.setOnClickListener{
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val email = preferences.getString("email", "")
            val pass = preferences.getString("pass", "")
            if(edt_emailLogin.text.trim().toString().equals(email) && edt_passwordLogin.text.toString().trim().equals(pass)){
                preferences.edit().putBoolean("isLogin", true).apply()
                startActivity(Intent(this, ContainerActivity::class.java))
                finish()
            }else{
                Toast.makeText(this, "Correo y/o clave incorrecta", Toast.LENGTH_LONG).show()
            }
        }
        btn_back.setOnClickListener {
            finish()
        }
        txt_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}