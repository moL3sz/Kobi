package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.TypeVariable
import java.time.Instant
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.annotation.RequiresApi
import android.view.View.DragShadowBuilder
import android.view.View.OnTouchListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.google.android.gms.fido.fido2.api.common.RequestOptions
import java.lang.Exception
import com.example.essentials.getDrawableByName





public class Keveropult :AppCompatActivity(){

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.N)



    //global vars in class

    lateinit var drinkRecyclerView : RecyclerView;
    var drinkListDrawable = mutableListOf<Int>();

    lateinit var CIRCULAR_drinkSelector : CircularDrinkSelector;
    lateinit var layoutManagerStoreFilter : LinearLayoutManager;
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.keveropult_activity)
        val drinkList: List<TextView> = ArrayList<TextView>();
        val drinks = arrayOf("gin", "jack", "kobi")


        val recieverContainer : TextView = findViewById<TextView> (R.id.piacon)
        var drinkSize = drinks.size;
        for(i in 0 until drinkSize){
            val drinkID : String = drinks[i].toString()
            Log.i("ID",drinkID)

            /*val currentTextView : TextView = findViewById<TextView>(resources.getIdentifier(drinkID,"id",this.packageName));
            val img : ImageView = findViewById<ImageView>(R.id.kobi)
            img.setOnTouchListener { v : View, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val data = ClipData.newPlainText("", "")
                        val shadowBuilder = DragShadowBuilder(img)
                        img.startDrag(data, shadowBuilder, img, 1)
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
            }*/
        }
        recieverContainer.setOnDragListener(dragListen)
        //initalize vars
        drinkRecyclerView = findViewById<RecyclerView>(R.id.cRecycleView)
        drinkRecyclerView.setItemViewCacheSize(3);
        layoutManagerStoreFilter = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        drinkRecyclerView.layoutManager = layoutManagerStoreFilter
        /*(i in 0 until drinks.size){
            val drinkName = drinks[i]+".jpg"
            drinkListDrawable.add(getDrawableByName(this,drinkName))
        }*/
        drinkListDrawable.add(R.drawable.kobi)
        drinkListDrawable.add(R.drawable.gin)
        drinkListDrawable.add(R.drawable.jack)

        //
        CIRCULAR_drinkSelector = CircularDrinkSelector(this, drinkListDrawable,findViewById<TextView>(R.id.currentDrinkLabel));

        val snapHelper: SnapHelper = PagerSnapHelper();
        snapHelper.attachToRecyclerView(drinkRecyclerView);

        drinkRecyclerView.adapter = CIRCULAR_drinkSelector
        layoutManagerStoreFilter.scrollToPosition(((Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2) % drinkListDrawable.size))
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
                Log.e("event",event.clipDescription.toString())
                receiverView.text = "asd"
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


class CircularDrinkSelector(context: Context, list2: List<Int>, drinkLabel : TextView) : RecyclerView.Adapter<CircularDrinkSelector.ViewHolderImage>() {
    private var itemList: List<Int> = ArrayList()
    private lateinit var mContext: Context;

    lateinit var CurrentLabelText : TextView;


    private var labelList : List<String> = listOf(
        "Köbányai sör",
        "Agárdi Chameleon Gin",
        "Jack Daniels Whiskey");

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolderImage {
        val itemViewFooterMenu: View = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.drink_selector_slider, viewGroup, false
        )
        return ViewHolderImage(itemViewFooterMenu)

    }
    fun getItem(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(viewHolder: ViewHolderImage,position: Int) {
        Log.e("VewHOlderCalled", "::$position")
        val positionInList = position % itemList.size



        //Itt még tudjuk manipulálni az egyes ImageViewkat...

        viewHolder.image_slider.id = positionInList


        //Álltisjuk be hogy mi legyen kiírva az ital féle mint név: PL(Köbi kép -> "Kőbányai sör")


        Glide.with(mContext)
            .load(mContext.resources.getDrawable(itemList[positionInList]))
            .into(viewHolder.image_slider);
    }
    override fun onViewDetachedFromWindow(viewHolder : ViewHolderImage){
        val labelPos = (viewHolder.position + 0) % labelList.size
        val currentLabelForDrink = labelList[labelPos];
        CurrentLabelText.text = currentLabelForDrink;
        Log.wtf("view","onViewDetachedFromWindow "+viewHolder);
    }


    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    inner class ViewHolderImage(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image_slider: ImageView

        init {
            image_slider = itemView.findViewById(R.id.image_slider)

            image_slider.setOnLongClickListener{
                        val data : ClipData = ClipData.newPlainText(image_slider.id.toString(),"Hello");

                        val shadowBuilder = DragShadowBuilder(image_slider)
                        image_slider.startDragAndDrop(data, shadowBuilder, image_slider, 0)
                        image_slider.visibility = View.VISIBLE
                        true

            }
        }
    }

    init {
        try {
            mContext = context
            itemList = list2
            CurrentLabelText = drinkLabel;
            //            Log.e("listSizeup::","::"+ itemList.size());
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
