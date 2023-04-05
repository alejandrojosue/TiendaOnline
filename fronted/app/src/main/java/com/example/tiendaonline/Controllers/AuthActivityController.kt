package com.example.tiendaonline.Controllers
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tiendaonline.Models.LoginRequest
import com.example.tiendaonline.Models.LoginResponse
import com.example.tiendaonline.HomeActivity
import com.example.tiendaonline.R
import com.example.tiendaonline.Services.ApiServices.ILoginRepository
import com.example.tiendaonline.Services.ServiceBuilder
import com.example.tiendaonline.util.PreferenceHelper
import com.example.tiendaonline.util.PreferenceHelper.get
import com.example.tiendaonline.util.PreferenceHelper.set
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class AuthActivityController : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        title = "Login"

        btn.setOnClickListener{

        }
        buttonLogin.setOnClickListener {


            if(!editTextPassword.text.isNullOrEmpty() && !editTextUsername.text.isNullOrEmpty()){
                taskToken(this)
                val preferenceHelper = PreferenceHelper.defaultPrefs(this)
                println(preferenceHelper["codeRes", ""])
                if(preferenceHelper["codeRes", ""].toString()=="200"){
                    ProductController().getAll(preferenceHelper["token", ""])
                    //ABRIR VENTANA HOME
                    startActivity(Intent(this,HomeActivity::class.java))
                }else{
                    Toast.makeText(this, "Usuario y/o clave incorrectos",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Llene los campos",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setToken(context: Context){
        val requestModel = LoginRequest(editTextUsername.text!!.toString(), editTextPassword.text!!.toString())
        val response = ServiceBuilder.buildService(ILoginRepository::class.java)
        response.sendReq(requestModel).enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val preference = PreferenceHelper.defaultPrefs(context)
                    preference["codeRes"] = response.code().toString()
                    if(response.code()==200){
                        if(response.body()?.jwt.toString() != null){
                            preference["token"] = response.body()?.jwt.toString()
                        }
                    }else{
                        Toast.makeText(context, "Code ${response.code()}",Toast.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    println(t.message.toString())
                }
            })
    }

    private fun taskToken(context: Context){
        GlobalScope.launch {
            setToken(context)
        }
    }

}