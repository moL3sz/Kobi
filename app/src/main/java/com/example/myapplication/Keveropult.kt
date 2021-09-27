package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
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
import androidx.annotation.RequiresApi
import android.view.View.DragShadowBuilder
import android.view.View.OnTouchListener
import android.widget.*
import com.bumptech.glide.Glide
import com.google.android.gms.fido.fido2.api.common.RequestOptions
import java.lang.Exception
import com.example.essentials.createNewLayerInGlass
import com.example.essentials.rotateArrayAccordingToDirection

import android.view.MotionEvent
import androidx.recyclerview.widget.*
import org.w3c.dom.Text
import kotlin.math.abs
import kotlin.math.floor
import kotlin.properties.Delegates

private val labelList : List<String> = listOf(
    "Köbányai sör",
    "Agárdi Chameleon Gin",
    "Jack Daniels Whiskey");
public class Keveropult :AppCompatActivity(){

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.N)



    //global vars in class
    private var labelList : List<String> = listOf(
        "Köbányai sör",
        "Agárdi Chameleon Gin",
        "Jack Daniels Whiskey");
    lateinit var drinkRecyclerView : RecyclerView;
    var drinkListDrawable = mutableListOf<Int>();
    lateinit var pohar : LinearLayout;
    lateinit var CIRCULAR_drinkSelector : CircularDrinkSelector;
    lateinit var layoutManagerStoreFilter : LinearLayoutManager;
    lateinit var currentLabelText : TextView;

    var touchDownX = 0;
    var touchDownY = 0;
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.keveropult_activity)
        val drinkList: List<TextView> = ArrayList<TextView>();
        val drinks = arrayOf("gin", "jack", "kobi")
        val recieverContainer : TextView = findViewById<TextView> (R.id.piacon)
        var drinkSize = drinks.size;
        recieverContainer.setOnDragListener(dragListen)
        //initalize vars



        currentLabelText = findViewById<TextView>(R.id.currentDrinkLabel);
        drinkRecyclerView = findViewById<RecyclerView>(R.id.cRecycleView)
        drinkRecyclerView.setItemViewCacheSize(0);
        layoutManagerStoreFilter = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        drinkRecyclerView.layoutManager = layoutManagerStoreFilter
        /*(i in 0 until drinks.size){
            val drinkName = drinks[i]+".jpg"
            drinkListDrawable.add(getDrawableByName(this,drinkName))
        }*/

        drinkListDrawable.add(R.drawable.gin)
        drinkListDrawable.add(R.drawable.jack)
        drinkListDrawable.add(R.drawable.kobi)
        drinkRecyclerView.isSaveEnabled = false;

        //
        CIRCULAR_drinkSelector = CircularDrinkSelector(this, drinkListDrawable, currentLabelText);

        val snapHelper: SnapHelper = PagerSnapHelper();
        snapHelper.attachToRecyclerView(drinkRecyclerView);



        drinkRecyclerView.adapter = CIRCULAR_drinkSelector
        val offset : Int = 1000000

        layoutManagerStoreFilter.scrollToPosition((offset * drinkSize)+1);

        //pohár töltés

        pohar = findViewById <LinearLayout>(R.id.pohar)
        recieverContainer.setOnDragListener(dragListen)


        //swipe check






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


                //init a new thread of filling the class up!
                val newLayer : TextView = createNewLayerInGlass(this,1,R.color.yellow,pohar)
                pohar.addView(newLayer)
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
}


class CircularDrinkSelector(context: Context, list2: List<Int>, drinkLabel : TextView) : RecyclerView.Adapter<CircularDrinkSelector.ViewHolderImage>() {
    private var itemList: List<Int> = ArrayList()
    private lateinit var mContext: Context;
    lateinit var drinkLabel: TextView;


    private  var prevPosition = 0;

    lateinit var CurrentLabelText : TextView;


    private var stateOfDrinks = mutableListOf<Int>();
    private var lastAttachedPos by Delegates.notNull<Int>();


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
        val positionInList = position % itemList.size

        viewHolder.image_slider.id = positionInList
        Glide.with(mContext)
            .load(mContext.resources.getDrawable(itemList[positionInList]))
            .into(viewHolder.image_slider);
    }

    override fun onViewAttachedToWindow(viewHolder: ViewHolderImage) {
        val rawPosition : Int = viewHolder.adapterPosition;
        val positionInList = (viewHolder.adapterPosition) % itemList.size;
        //Add the current position to the drinkStates
        if(stateOfDrinks.size == 3){
            lastAttachedPos = rawPosition
            stateOfDrinks = rotateArrayAccordingToDirection(rawPosition,stateOfDrinks)
            drinkLabel.text = labelList[(stateOfDrinks[1]+1) % stateOfDrinks.size] //AZé van +1 mert így működik azt nem kell bántani xd
        }
        else {
            if (stateOfDrinks.indexOf(rawPosition) == -1) {
                stateOfDrinks.add(rawPosition)
            }
        }

        Log.wtf("PositionAT",viewHolder.adapterPosition.toString())
        Log.wtf("PositionState",stateOfDrinks.toString())
        //drinkLabel.text = labelList[positionInList]
    }

    override fun onViewDetachedFromWindow(viewHolder: ViewHolderImage) {
        val rawPosition : Int = viewHolder.adapterPosition;
        if(rawPosition == lastAttachedPos){
            drinkLabel.text = labelList[(stateOfDrinks[1]) % stateOfDrinks.size]
            Log.wtf("Pos","Same position was detached!!!!!!!!!!")
        }
        Log.wtf("PositionDE",rawPosition.toString())

        Log.wtf("PositionStateDE",stateOfDrinks.toString())

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
            this.drinkLabel = drinkLabel
            mContext = context
            itemList = list2
            //            Log.e("listSizeup::","::"+ itemList.size());
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
