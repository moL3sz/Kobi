package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.lang.Exception
import kotlin.random.Random

class LoginRegister : AppCompatActivity(){
    val TOKEN_LENGTH = 128;


    fun createToken() : String{
        val lowerAlphabet = "abcdefghijklmnopqrstuvwxyz"
        val upperAlphabet = lowerAlphabet.uppercase();
        val numbers = "0123456789";
        val special = "*._;/$-"
        val parsed = lowerAlphabet+upperAlphabet+numbers+special;
        var token = "";
        for(i in 0..TOKEN_LENGTH){
            token += parsed[Random.nextInt(0,parsed.length)];
        }
        return token;
    }

    fun saveTokenToDevice(token: String){
        try {
            //save the token the the device memory on the first run

        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val TOKEN = createToken()

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            setContentView(R.layout.login_activity)
            val loginSendButton = findViewById<Button>(R.id.sendLoginButton)
            loginSendButton.setOnClickListener {
                Toast.makeText(this@LoginRegister, createToken(),Toast.LENGTH_LONG).show();
                val email = findViewById<EditText>(R.id.loginInput).text
                val password = findViewById<EditText>(R.id.passwordInput).text;

                //baszunk egy requestet a szervernek, emailel jelszoval meg egy tokennel
                //AMikor az ipse megnyitja eloszor az appot generálódik egy token a localba,
                //És ha van netje a csavonak, akkor azt elküldi a szevernek, igy lesz egy jó biztonságos authentication
                //szoval ha valameilyen hacker próbál nekünk rosszat tenni az nem fog tudni mert nincs meg neki a tokenje ;)



            }
        }




    }


}