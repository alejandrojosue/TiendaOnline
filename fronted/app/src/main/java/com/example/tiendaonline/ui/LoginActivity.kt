package com.example.tiendaonline.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiendaonline.Models.Users.LoginRequest
import com.example.tiendaonline.R
import com.example.tiendaonline.Repository.UserRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val userRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        setUp()
    }
    private fun setUp(){
        btn_login.setOnClickListener{
            GlobalScope.launch {
                val loginRequest = LoginRequest(
                    identifier = edt_emailLogin.text.trim().toString(),
                    password = edt_passwordLogin.text.toString().trim()
                )
                val response = userRepository.login(loginRequest)
                if(response.isSuccess){
                    val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                    preferences.edit().putString("identidad", response.getOrNull()!!.user.Identification).apply()
                    preferences.edit().putString("fullName", response.getOrNull()!!.user.username).apply()
                    preferences.edit().putString("email", response.getOrNull()!!.user.email).apply()
                    preferences.edit().putInt("id", response.getOrNull()!!.user.id).apply()
                    preferences.edit().putBoolean("isLogin", true).apply()
                    startActivity(Intent(applicationContext, ContainerActivity::class.java))
                    finish()
                }else{
                    runOnUiThread{
                        Toast.makeText(applicationContext, "Correo y/o clave incorrecta", Toast.LENGTH_LONG).show()
                    }
                }
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