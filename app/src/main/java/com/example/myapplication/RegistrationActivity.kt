package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.essentials._Request
import java.time.Instant

class RegistrationActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


        //set the current layout view
        //registration_activity.xml
        setContentView(R.layout.registration_activity)


        //váltsunk át a registráció layoutra
        //kell majd egy "Elmultál 18 éves "layout is
        setContentView(R.layout.registration_activity)
        val sendRegButton = findViewById<Button>(R.id.sendregbutton);
        sendRegButton.setOnClickListener {
            //send reg request to the server ... handling in Essentials.kt
            val req = _Request(this)
            req.registrateRequest("hello","world", "123456789", "asd")

        }







    }

}