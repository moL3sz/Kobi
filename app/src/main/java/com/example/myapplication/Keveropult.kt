package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.TypeVariable
import java.time.Instant
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.DragEvent
import android.widget.ImageView
import androidx.annotation.RequiresApi
import android.view.MotionEvent
import android.view.View.DragShadowBuilder
import android.view.View.OnTouchListener


public class Keveropult :AppCompatActivity(){

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.keveropult_activity)
        val drinkList : List<TextView> = ArrayList<TextView>();
        val drinks = arrayOf("sor","bor","whisky","brandy")


        val recieverContainer : TextView = findViewById<TextView> (R.id.piacon)
        var drinkSize = drinks.size;
        for(i in 0 until drinkSize){
            val drinkID : String = drinks[i].toString()
            Log.i("ID",drinkID)

            val currentTextView : TextView = findViewById<TextView>(resources.getIdentifier(drinkID,"id",this.packageName));
            val img : ImageView = findViewById<ImageView>(R.id.kobi)
            img.setOnTouchListener { v : View, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val data = ClipData.newPlainText("", "")
                        val shadowBuilder = DragShadowBuilder(img)
                        img.startDragAndDrop(data, shadowBuilder, img, 1)
                        img.visibility = View.INVISIBLE
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        img.visibility = View.VISIBLE
                        true
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        img.visibility = View.VISIBLE
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
        recieverContainer.setOnDragListener(dragListen)



    }
    private val dragListen = View.OnDragListener { v, event ->
        val receiverView:TextView = v as TextView
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    v.invalidate()
                    true
                } else {
                    false
                }
            }

            DragEvent.ACTION_DRAG_ENTERED -> {

                receiverView.text = "Öntsed öntseeed!!!"
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION ->
                true

            DragEvent.ACTION_DRAG_EXITED -> {
                //itt fog visszaugrani a pia icon a helyére
                //majd megcsinálom függvényt rá + eddig csak textViewval megy de csak a test kedvéért
                //de majd később ugye iconokkal
                receiverView.text = "Huzd ide a piát!"
                v.invalidate()
                true
            }

            DragEvent.ACTION_DROP -> {
                //itt is vissza fogja dobni az eredeti positionre
                /*
                val item: ClipData.Item = event.clipData.getItemAt(0)
                val dragData = item.text
                receiverView.text = "You dropped : $dragData"
                v.invalidate()*/
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                v.invalidate()
                when(event.result) {
                    true ->
                        // drop was handled
                        receiverView.text = "Vissza raktad!"

                    else ->{
                        //
                        receiverView.text = "Vissza raktad!"
                    }
                }
                true
            }

            else -> {
                false
            }
        }
    }

    //Todo kell akkor amit mondtam a horizontal scroll cucc, szerintem holnap megcsinálom!
    //Vagy hardcodeljuk bele az adatot vagy dinamikusan legenráljuk
    //Hard-code -> Nehezebb lesz skálázni
    //Dynamic-generate
}
