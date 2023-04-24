package com.example.tiendaonline.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiendaonline.Middlewares.Validation
import com.example.tiendaonline.Models.Users.RegisterRequest
import com.example.tiendaonline.R
import com.example.tiendaonline.Repository.UserRepository
import com.example.tiendaonline.util.ToastMessage
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val userRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        setUp()
    }
    private fun setUp(){
        btn_back.setOnClickListener {
            finish()
        }
        txt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        btn_register.setOnClickListener{btnRegisterListener()}
    }
    private fun btnRegisterListener() {
        if (!Validation.validateTextViewRequired(edt_password) { text ->
                Validation.isStrongPassword(text)
            }) {
            ToastMessage.getToast(this, "Contraseña insegura, usar mayúsculas, minúsculas, números y caracteres especiales")
        } else if (!edt_confirmPassword.text.toString().trim()
                .equals(edt_password.text.toString().trim())
        ) {
            ToastMessage.getToast(this, "La contraseña no se confirmó correctamente")
        } else if (!Validation.validateTextViewRequired(edt_email) { text ->
                Validation.isValidEmail(text)
            }) {
            ToastMessage.getToast(this, "Correo inválido")
        }else if (!Validation.validateTextViewRequired(edt_identidad) {
                Validation.validateMinLength(edt_identidad, 13)
            }) {
            ToastMessage.getToast(this, "Ingrese una identidad válida")
        }else{
            if(edt_identidad.text.toString().trim().length==13){
                if(Validation.validateTextViewRequired(edt_fullname) { true } &&
                    Validation.validateTextViewRequired(edt_confirmPassword) { true }){
                    // Registrar
                    val registerRequest = RegisterRequest(
                        Identification = edt_identidad.text.toString().trim(),
                        email = edt_email.text.toString().trim(),
                        username = edt_fullname.text.toString().trim(),
                        password = edt_password.text.toString().trim()
                    )
                    GlobalScope.launch {
                        val response = userRepository.register(registerRequest)
                        if(response.isSuccess){
                            val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

                            preferences.edit().putString("identidad", edt_identidad.text.trim().toString()).apply()
                            preferences.edit().putString("email", edt_email.text.trim().toString()).apply()
                            preferences.edit().putString("fullName", edt_fullname.text.trim().toString()).apply()
                            runOnUiThread{
                                ToastMessage.getToast(applicationContext, "Registrado correctamente")
                            }
                            startActivity(Intent(applicationContext, LoginActivity::class.java))
                            finish()
                        }else{
                            if(response.getOrNull()!!.equals("400")){
                                runOnUiThread{
                                    ToastMessage.getToast(applicationContext, "Correo, usuario o identidad ya han sido registrados")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}