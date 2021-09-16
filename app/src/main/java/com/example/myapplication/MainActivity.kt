package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val keveropult = findViewById<Button>(R.id.asd)
        keveropult.setOnClickListener{
            Toast.makeText(this@MainActivity,"Ittál ma köcsög?",Toast.LENGTH_SHORT).show();
        }
    }
}