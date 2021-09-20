package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.lang.Exception
import kotlin.random.Random
import com.example.essentials.*


class Main : AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.M)

    var regButtonClicked : Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?){

        val testUsername : String = "hello"
        val testPassword : String = "world"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TOKEN = createToken()

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
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

                if(testUsername == email && password == testPassword){
                    Toast.makeText(this, "Bejelentkeztél!",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Beszoptad",Toast.LENGTH_SHORT).show()
                }

                //baszunk egy requestet a szervernek, emailel jelszoval meg egy tokennel
                //AMikor az ipse megnyitja eloszor az appot generálódik egy token a localba,
                //És ha van netje a csavonak, akkor azt elküldi a szevernek, igy lesz egy jó biztonságos authentication
                //szoval ha valameilyen hacker próbál nekünk rosszat tenni az nem fog tudni mert nincs meg neki a tokenje ;)
            }
        }


        val registrateButton = findViewById<Button>(R.id.registerButton)
        registrateButton.setOnClickListener {
            //váltsunk át a registráció layoutra
            //kell majd egy "Elmultál 18 éves "layout is
            setContentView(R.layout.registration_activity)
            val sendRegButton = findViewById<Button>(R.id.sendregbutton);
            sendRegButton.setOnClickListener {
                //send reg request to the server ... handling in Essentials.kt

            }
        }
    }
}