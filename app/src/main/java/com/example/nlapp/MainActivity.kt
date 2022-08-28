package com.example.nlapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nlapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    lateinit var navView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val controller = findNavController(R.id.fragmentContainerView)
        navView = findViewById(R.id.bottomNavMenu)

        val appBarConfig = AppBarConfiguration(
            setOf(
                R.id.cryptoFragment,
                R.id.exchangeFragment,
                R.id.profileFragment
            )
        )

        setupActionBarWithNavController(controller, appBarConfig)
        navView.setupWithNavController(controller)

        hideNavBar()
    }


    fun hideNavBar() {
        navView.visibility = View.GONE
    }

    fun showNavBar() {
        navView.visibility = View.VISIBLE
    }

}