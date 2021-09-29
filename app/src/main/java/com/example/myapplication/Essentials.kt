package com.example.essentials
import android.R.attr
import android.app.DownloadManager
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.app.Activity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.*
import java.lang.Exception
import kotlin.random.Random
import android.R.attr.data
import android.R.attr.width
import android.annotation.SuppressLint
import android.app.Person
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import kotlin.io.path.Path
import android.R.attr.scaleY

import android.R.attr.scaleX
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import java.util.*
import kotlin.collections.HashMap


class _Request(ctx : AppCompatActivity){
    var contex : AppCompatActivity
    val API_URL : String = "http://172.22.8.68:4000"
    val queue  = Volley.newRequestQueue(ctx);
    init{
        contex = ctx;
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun loginRequest(username: String, password: String, token: String) : Boolean{
        try{
            val stringRequest: StringRequest = object : StringRequest( Method.POST, API_URL + "/login",
                    Response.Listener { response ->
                        try {
                            val res = response.toString()
                            if(res == "200"){
                                Toast.makeText(contex, "Sikeresen bejelentkeztél",Toast.LENGTH_LONG).show()
                            }
                            else{
                                Toast.makeText(contex, "Valamit nagyon elbasztál ", Toast.LENGTH_LONG).show()
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(contex, error.toString(), Toast.LENGTH_LONG).show()
                    }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    //Change with your post params
                    params["username"] = username
                    params["password"] = password
                    return params
                }
            }
            queue.add(stringRequest)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        return false

    }
    fun registrateRequest(username: String, password: String, token: String, email : String){
        try {
            val stringRequest: StringRequest = object : StringRequest( Method.POST, API_URL + "/registrate",
                    Response.Listener { response ->
                        Toast.makeText(contex, response, Toast.LENGTH_LONG).show()
                        try {

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(contex, error.toString(), Toast.LENGTH_LONG).show()
                    }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    //Change with your post params
                    params["username"] = username
                    params["password"] = password
                    return params
                }
            }
            queue.add(stringRequest)

        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
}
class _Color(alpha: Int, colorCode : String){
    var alpha : Int;
    var colorCode : String;
    init {
        this.alpha = alpha;
        this.colorCode = colorCode;
    }
}
var isDone = false;

@SuppressLint("Recycle")
private fun scaleView(tv : TextView, startScale : Float, endScale : Float) : ValueAnimator{
    val anim = ValueAnimator.ofInt(tv.getMeasuredHeight(), -100)
    anim.addUpdateListener { valueAnimator ->
        val `val` = valueAnimator.animatedValue as Int
        val layoutParams: ViewGroup.LayoutParams = tv.layoutParams
        layoutParams.height = `val` + 1
        tv.layoutParams = layoutParams
    }
    anim.duration = 10
    return anim
}
@RequiresApi(Build.VERSION_CODES.M)
fun checkForInternet(cm: ConnectivityManager) : Boolean{
    val activeNetwork = cm.activeNetwork;
    return  activeNetwork != null
}
fun createToken() : String{
    val TOKEN_LENGTH = 128;
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
fun readTokenFromFile(ctx : Context) : Boolean{
    try {
        //
        //default path for token : assets/LOCAL_TOKEN
        val inputStream: InputStream = ctx.openFileInput("LOCAL_TOKEN.dat")
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        //kiolvasssuk a tokent a filebol (FLAG)
        //ha van benne valami vagy egyátalán létezik a cucc,
        //akkor visszatérünk trueval, minden más esetben falseal (true -> regisztrált már vagy loginolt valaha)

        var strLine : String = ""
        var READ_TOKEN : String = ""
        strLine = bufferedReader.readLine()
        READ_TOKEN = strLine
        return READ_TOKEN != ""
    }
    catch(e: IOException){
        Log.i("Essentials","FUCKED UP")
        return  false
    }
}
fun saveTokenToDevice(token: String,ctx : Context){
    try {
        Log.i("Essentials", ctx.filesDir.parent.toString())
        val outputStreamWriter =
            OutputStreamWriter(ctx.openFileOutput("LOCAL_TOKEN.dat", Context.MODE_PRIVATE))
        outputStreamWriter.write(token)
        outputStreamWriter.close()
    }
    catch (e: IOException){
        e.printStackTrace()
    }
}


fun getDrawableByName(ctx: Context,name : String) : Int{
    return ctx.resources.getIdentifier(name,"drawable",ctx.packageName);
}


@SuppressLint("ResourceType")
fun createNewLayerInGlass(
    context: Context, id: Int, color: String?, parentLayout: LinearLayout, alpha: Float,
) : TextView{

    val DEFAULT_HEIGHT = 50;
    val newDrink = TextView(context);
    newDrink.id = id;
    newDrink.alpha = alpha
    newDrink.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100)
    newDrink.requestLayout()
    newDrink.setBackgroundColor(Color.parseColor(color))
    return newDrink;

}

fun MaxOfArray(arr : List<Int>?) : Int{
    var m = 0;
    if (arr != null) {
        for(i in arr.indices){
            if (arr[i] > m){
                m = arr[i]
            }
        }
    }
    return m
}
fun MinOfArray(arr : List<Int>?) : Int{
    var m = Int.MAX_VALUE;
    if (arr != null) {
        for(i in arr.indices){
            if (arr[i] < m){
                m = arr[i]
            }
        }
    }
    return m
}
fun rotateArrayAccordingToDirection(newPosition : Int, positionArray : MutableList<Int>) : MutableList<Int>{
    //[0,1,2] //3
    //[1,2,3]


    //check if it swiped left or right
    if(newPosition > MaxOfArray(positionArray)){ //Left swipe
        positionArray.removeAt(0)
        positionArray.add(newPosition)



    }
    else if(newPosition < MinOfArray(positionArray)){ //Right swipe
        positionArray.removeAt(positionArray.size-1)
        positionArray.add(0,newPosition)
    }

    return  positionArray

}
fun getSwipeDirectionFromPosition(newPosition: Int, positionArray: MutableList<Int>) : Boolean{
    return MaxOfArray(positionArray) < newPosition
}
fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}
fun loadColorsFromJson(context: Context): MutableList<_Color> {
    var colors = mutableListOf<_Color>()
    val gson = Gson()
    val listColorType = object : TypeToken<List<_Color>>() {}.type

    colors = gson.fromJson(getJsonDataFromAsset(context,"colors.json"),listColorType)
    colors.forEachIndexed { idx, person -> Log.wtf("data", "> Item $idx:\n$person") }
    return colors
}


