package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.essentials._Request

class LoginActivity : AppCompatActivity(){

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val testUsername: String = "hello"
        val testPassword: String = "world"

        setContentView(R.layout.login_activity)


        val loginSendButton = findViewById<Button>(R.id.sendLoginButton)
        loginSendButton.setOnClickListener {

            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
            //Toast.makeText(this@Main, checkForInternet(cm).toString(),Toast.LENGTH_LONG).show();
            val req = _Request(this)
            val username = findViewById<EditText>(R.id.loginInput).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()
            req.loginRequest(username = username, password = password, token = "TOken")
            //Le kapjuk a input fieldeknek az értékeit
            //val email = findViewById<EditText>(R.id.loginInput).text.toString()
           // val password = findViewById<EditText>(R.id.passwordInput).text.toString()





        }
    }
}