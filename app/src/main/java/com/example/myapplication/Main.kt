package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.lang.Exception
import kotlin.random.Random
import com.example.essentials.*
import com.example.myapplication.LoginActivity
import com.example.myapplication.RegistrationActivity

class Main : AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.M)

    var regButtonClicked : Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if the token already exists
        if(!readTokenFromFile(this)){
            val FIRST_TOKEN : String = createToken()
           saveTokenToDevice(FIRST_TOKEN,this)
        }
        else{
            Log.i("TOKEN","Megtalálva")
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val intent = Intent(this@Main, LoginActivity::class.java)
            startActivity(intent)
        }
        val registrateButton = findViewById<Button>(R.id.registerButton)
        registrateButton.setOnClickListener {
            val intent : Intent = Intent(this@Main, RegistrationActivity::class.java);
            startActivity(intent)
        }

        //val menu = HorizontalScrollView.find

        InitMenu();
    }

    fun InitMenu() {

    }
}