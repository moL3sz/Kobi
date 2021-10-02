package com.example.myapplication

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView

class MainPage : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage_activity)
        //click on menu button
        val drawLayout : DrawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        findViewById<ImageView>(R.id.expanded_menu).setOnClickListener(View.OnClickListener {
            drawLayout.openDrawer(GravityCompat.START)
        })
        //handle clicks on menu items

        val navMenu = findViewById<NavigationView>(R.id.navview)
        navMenu?.setNavigationItemSelectedListener (this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.toKeverpult->{
                val intent = Intent(this,Keveropult::class.java)
                startActivity(intent)
            }
        }
        return  super.onOptionsItemSelected(item)
    }
}