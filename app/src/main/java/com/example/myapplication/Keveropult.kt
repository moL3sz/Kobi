package com.example.myapplication

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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.DragEvent
import org.w3c.dom.Text

public class Keveropult :AppCompatActivity(){

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

            currentTextView.setOnLongClickListener{v:View ->
                val item = ClipData.Item(v.tag as? CharSequence)

                val dragData = ClipData(
                    "drag",
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    item
                )

                val myShadow = MyDragShadowBuilder(v)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(dragData, myShadow, null, 0)
                }
                else{
                    false;
                }

            }
        }

        recieverContainer.setOnDragListener(dragListen)


    }
    private class MyDragShadowBuilder(v: View) : View.DragShadowBuilder(v) {
        //itt majd nyilván a saját iconja lesz majd egy color helyett

        private val shadow = ColorDrawable(Color.LTGRAY)

        override fun onProvideShadowMetrics(size: Point, touch: Point) {
            val width: Int = (view.width / 1.2F).toInt()
            val height: Int = (view.height / 1.2F).toInt()
            //val width: Int = view.width / 2
            //val height: Int = view.height / 2

            shadow.setBounds(0, 0, width, height)
            size.set(width, height)
            touch.set(width / 2, height / 2)
        }
        override fun onDrawShadow(canvas: Canvas) {
            shadow.draw(canvas)
        }
    }
    private val dragListen = View.OnDragListener { v, event ->
        val receiverView:TextView = v as TextView
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    receiverView.setBackgroundColor(Color.CYAN)
                    v.invalidate()
                    true
                } else {
                    false
                }
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                receiverView.setBackgroundColor(Color.GREEN)

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
                        receiverView.setBackgroundColor(Color.RED)
                    }
                }
                true
            }

            else -> {
                false
            }
        }
    }
}
