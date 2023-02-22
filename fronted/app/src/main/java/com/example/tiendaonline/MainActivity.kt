package com.example.tiendaonline
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiendaonline.Controllers.LoginController
import com.example.tiendaonline.Controllers.ProductController
import com.example.tiendaonline.util.PreferenceHelper
import com.example.tiendaonline.util.PreferenceHelper.get

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val identifier = "user@gmail.com"
        val password = "strapiPassword"
        LoginController(identifier, password,this)
        val preferenceHelper = PreferenceHelper.defaultPrefs(this)
        ProductController().getAll(preferenceHelper["token", ""])
    }
}