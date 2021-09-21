package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.essentials._Request

class LoginActivity : AppCompatActivity(){

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

            //req.loginRequest(username = "hello", password = "jelszo", token = "TOken")


            //Le kapjuk a input fieldeknek az értékeit
            val email = findViewById<EditText>(R.id.loginInput).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()

            /*
                test: csak nézzük meg szimplán hogy milyen lesz a auth
                Kérdés?:
                Login után legyen meg a token először, vagy regisztráció után  vagy tökmindegy
                (Át kell gondolni)
             */
            //Majd az azonosítás is a szerveren fog történni, Sima lekérdezés alapján

            if (testUsername == email && password == testPassword) {
                Toast.makeText(this, "Bejelentkeztél!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Beszoptad", Toast.LENGTH_SHORT).show()
            }


        }
    }
}