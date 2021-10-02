package com.example.myapplication

import android.R.attr
import android.R.attr.*
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import android.view.View.DragShadowBuilder
import android.widget.*
import com.bumptech.glide.Glide
import java.lang.Exception
import com.example.essentials.createNewLayerInGlass
import com.example.essentials.rotateArrayAccordingToDirection
import com.example.essentials.getSwipeDirectionFromPosition
import com.example.essentials.isDone
import android.graphics.Matrix
import android.widget.ImageView

import com.example.essentials._Color

import androidx.recyclerview.widget.*
import kotlin.properties.Delegates

import android.graphics.Bitmap

import android.graphics.BitmapFactory
import android.view.animation.RotateAnimation
import android.view.animation.LinearInterpolator

import android.view.animation.Animation
import java.util.*
import kotlin.collections.ArrayList
import android.view.ViewGroup

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.R.attr.path

import android.view.MotionEvent
import com.google.android.material.chip.Chip

lateinit var ezkellnekem : ImageView

private val labelList : List<String> = listOf(
    "Kőbányai sör",
    "Agárdi Chameleon Gin",
    "Jack Daniels Whiskey",
    "Koccintós bor")

public class Keveropult :AppCompatActivity(){
    lateinit var colorList : MutableList<_Color>
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.N)

    //global vars in class
    lateinit var drinkRecyclerView : RecyclerView
    var drinkListDrawable = mutableListOf<Int>()
    lateinit var pohar : LinearLayout
    lateinit var CIRCULAR_drinkSelector : CircularDrinkSelector
    lateinit var layoutManagerStoreFilter : LinearLayoutManager
    lateinit var currentLabelText : TextView
    lateinit var bal_piatolto: Chip

    var touchDownX = 0
    var touchDownY = 0
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.keveropult_activity)
        val drinkList: List<TextView> = ArrayList<TextView>()
        val recieverContainer : TextView = findViewById<TextView> (R.id.piacon)
        val drinkSize = labelList.size
        recieverContainer.setOnDragListener(dragListen)
        //initalize vars

        currentLabelText = findViewById<TextView>(R.id.currentDrinkLabel)
        drinkRecyclerView = findViewById<RecyclerView>(R.id.cRecycleView)
        drinkRecyclerView.setItemViewCacheSize(0)
        layoutManagerStoreFilter = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        drinkRecyclerView.layoutManager = layoutManagerStoreFilter
        /*(i in 0 until drinks.size){
            val drinkName = drinks[i]+".jpg"
            drinkListDrawable.add(getDrawableByName(this,drinkName))
        }*/
        drinkListDrawable.add(R.drawable.kobi)
        drinkListDrawable.add(R.drawable.gin)
        drinkListDrawable.add(R.drawable.jack)
        drinkListDrawable.add(R.drawable.koccintos)

        drinkRecyclerView.isSaveEnabled = false

        //
        CIRCULAR_drinkSelector = CircularDrinkSelector(this, drinkListDrawable, currentLabelText)

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(drinkRecyclerView)

        drinkRecyclerView.adapter = CIRCULAR_drinkSelector
        val offset : Int = 1000000

        layoutManagerStoreFilter.scrollToPosition((offset * drinkSize)+1)

        //pohár töltés

        pohar = findViewById <LinearLayout>(R.id.pohar)
        recieverContainer.setOnDragListener(dragListen)

        //swipe check

    }

    var layerCount = 0
    var layerHeightOffset = 0

    @SuppressLint("ResourceAsColor")
    private fun createRunnable(drinkView: TextView): Runnable? {
        return Runnable {
            while (true) {
                try {
                    val layoutParams: ViewGroup.LayoutParams = drinkView.layoutParams
                    layoutParams.height = (drinkView.height + 1)
                    drinkView.layoutParams = layoutParams
                    Thread.sleep(1)

                }
                catch (e : InterruptedException) {
                    break
                }
            }
        }
    }

    private var UIThreadScaleDrink = Timer();
    lateinit var anim : ValueAnimator;
    private val dragListen = View.OnDragListener { v, event ->
        val receiverView:TextView = v as TextView
        var drinkOffset = 0;
        var currentLayer : TextView

        val rotate = RotateAnimation(
            45F,
            45F,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )

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
                Log.e("event", event.clipDescription.toString())

                //init a new thread of filling the class up!
                val colorId = event.clipDescription.label.toString().toInt()
                var currentColor: String = "#ffffff"
                var currentAlpha: Float = 1f
                when (colorId) {
                    0 -> {
                        currentColor = "#f28e1c"
                        currentAlpha = 0.8f
                    }
                    1 -> {
                        currentColor = "#d8e4bc"
                        currentAlpha = 0.8f
                    }
                    2 -> {
                        currentColor = "#ddaa55"
                        currentAlpha = 0.9f
                    }
                    3 -> {
                        currentColor = "#dddd55"
                        currentAlpha = 0.9f
                    }
                }
                isDone = false;

                val newLayer: TextView =
                    createNewLayerInGlass(this, 0, currentColor, pohar, currentAlpha)

                rotate.repeatCount = Animation.INFINITE
                rotate.interpolator = LinearInterpolator()

                anim = ValueAnimator.ofInt(drinkOffset, pohar.height)
                anim.addUpdateListener { valueAnimator ->
                    val h = valueAnimator.animatedValue as Int
                    val layoutParams: ViewGroup.LayoutParams =
                        newLayer.layoutParams
                    layoutParams.height =h
                    newLayer.layoutParams = layoutParams
                }
                anim.duration = 2000

                ezkellnekem.startAnimation(rotate)

                pohar.addView(newLayer, 0)
                anim.start()

                v.invalidate()

                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                anim.cancel()
                drinkOffset += pohar.getChildAt(0).height

                isDone = true;
                //stop the scaling thread -> nem nő tovább a pina (pia)
                UIThreadScaleDrink.cancel()
                ezkellnekem.clearAnimation()
                receiverView.text = "Huzd ide a piát!"
                v.invalidate()
                true
            }

            DragEvent.ACTION_DROP -> {
                anim.cancel()
                drinkOffset += pohar.getChildAt(0).height
                isDone = true;

                ezkellnekem.clearAnimation()
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                isDone = true;
                drinkOffset += pohar.getChildAt(0).height

                anim.cancel()

                v.invalidate()
                when (event.result) {
                    true -> {
                        ezkellnekem.clearAnimation()
                        receiverView.text = "Vissza raktad!"
                    }
                    // drop was handled
                    else -> {
                        //
                        ezkellnekem.clearAnimation()
                        receiverView.text = "Vissza raktad!"
                    }
                }
                v.invalidate()
                true
            }
            else -> {
                false
            }
        }
    }
}

class receiverView {

}

class CircularDrinkSelector(context: Context, list2: List<Int>, drinkLabel : TextView) : RecyclerView.Adapter<CircularDrinkSelector.ViewHolderImage>() {
    private var itemList: List<Int> = ArrayList()
    private lateinit var mContext: Context
    lateinit var drinkLabel: TextView

    private  var prevPosition = 0

    lateinit var CurrentLabelText : TextView

    private var stateOfDrinks = mutableListOf<Int>()
    private var lastAttachedPos by Delegates.notNull<Int>()

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
            .into(viewHolder.image_slider)
    }

    override fun onViewAttachedToWindow(viewHolder: ViewHolderImage) {
        val rawPosition : Int = viewHolder.adapterPosition
        val positionInList = (viewHolder.adapterPosition) % labelList.size
        lastAttachedPos = rawPosition
        //Add the current position to the drinkStates
        if(stateOfDrinks.size == 3){

            stateOfDrinks = rotateArrayAccordingToDirection(rawPosition,stateOfDrinks)
            drinkLabel.text = labelList[(stateOfDrinks[1]) % labelList.size] //AZé van +1 mert így működik azt nem kell bántani xd
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
        val rawPosition : Int = viewHolder.adapterPosition
        if(rawPosition == lastAttachedPos){
            //TODO Itt van a baj ezt kell valahogyan kijavitani
            val offset = if (getSwipeDirectionFromPosition(rawPosition,stateOfDrinks)) 2 else 0
            lastAttachedPos = 0
            //drinkLabel.text = labelList[(stateOfDrinks[offset]) % labelList.size] még kissé bugos
            Log.wtf("Pos","Same position was detached!!!!!!!!!!")
        }
        Log.wtf("PositionDE",rawPosition.toString())
        Log.wtf("PositionStateDE",stateOfDrinks.toString())

    }
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun onDoubleTap(e: MotionEvent): Boolean {
        val x = e.x
        val y = e.y

        // clean drawing area on double tap
        Log.d("Double Tap", "Tapped at: ($x,$y)")
        return true
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    inner class ViewHolderImage(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image_slider: ImageView

        init {
            image_slider = itemView.findViewById(R.id.image_slider)

            image_slider.setOnLongClickListener{
                val data : ClipData = ClipData.newPlainText(image_slider.id.toString(),"Hello")

                val shadowBuilder = DragShadowBuilder(image_slider)
                image_slider.startDragAndDrop(data, shadowBuilder, image_slider, 0)
                image_slider.visibility = View.VISIBLE
                ezkellnekem = image_slider
                true
            }
        }
    }

    init {
        try {
            this.drinkLabel = drinkLabel
            mContext = context
            itemList = list2
            //            Log.e("listSizeup::","::"+ itemList.size())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}