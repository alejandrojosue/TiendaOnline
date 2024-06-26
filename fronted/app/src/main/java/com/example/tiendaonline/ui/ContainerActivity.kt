package com.example.tiendaonline.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.tiendaonline.Middlewares.NetworkUtils
import com.example.tiendaonline.R
import com.example.tiendaonline.util.IsUserLogin
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class ContainerActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        splash.setKeepOnScreenCondition{false}
        if (NetworkUtils.isConnected(this)){
            setUp()
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, HomeFragment())
                    .commit()
                navigationView.setCheckedItem(R.id.nav_home)
                //Enviroments.cargarListasDesdePreferencias(this)
            }
        }else{
            startActivity(Intent(this, OfflLineActivity::class.java))
            finish()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun showBottomDialog() {}
    private fun setUp(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        if(preferences.getBoolean("DarkMode",true)) AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        ) else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        navigationView =  findViewById(R.id.nav_view)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fab = findViewById(R.id.fab)
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        bottomNavigationView.background = null
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.about -> {replaceFragment(AboutFragment())}
                R.id.profile -> {
                    if(!preferences.getBoolean("isLogin", false))
                        startActivity(Intent(this, MainActivity::class.java))
                    else
                        replaceFragment(ProfileFragment())
                }
               R.id.shopping -> replaceFragment(ShoppingFragment())
            }
            true
        }

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_settings -> replaceFragment(SettingsFragment())
                R.id.nav_about -> replaceFragment(AboutFragment())
                R.id.nav_logout -> {
                    preferences.edit().putBoolean("isLogin", false).apply()
                    IsUserLogin.isLogin(this, navigationView, true)
                }
            }
            true
        }

        if(preferences.getBoolean("isLogin", false)) IsUserLogin.isLogin(this, navigationView)

        fab.setOnClickListener {
            showBottomDialog()
        }
    }

    companion object{
        fun moreInfo(context: Context, name:String, price:String, quantity:String, description:String){
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottomsheetlayout)
            val nameTextView = dialog.findViewById<TextView>(R.id.tvNameMoreInfo)
            val priceTextView = dialog.findViewById<TextView>(R.id.tvPriceMoreInfo)
            val quantityTextView = dialog.findViewById<TextView>(R.id.tvQuantityMoreInfo)
            val descriptionTextView = dialog.findViewById<TextView>(R.id.tvDescriptionMoreInfo)
            val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)
            nameTextView.text = name
            priceTextView.text = price
            quantityTextView.text = quantity
            descriptionTextView.text = description
            cancelButton.setOnClickListener { dialog.dismiss() }
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }
    }

}