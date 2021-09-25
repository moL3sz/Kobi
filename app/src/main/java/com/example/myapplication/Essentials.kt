package com.example.essentials
import android.R.attr
import android.app.DownloadManager
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.io.path.Path
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
    return false
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


fun detectCollsionFromTwoImages(img1 : ImageView, img2 : ImageView) : Boolean{

    val rect1 : Rect = Rect()
    img1.getDrawingRect(rect1)
    val rect2 : Rect = Rect()
    img2.getDrawingRect(rect2)
    return Rect.intersects(rect1,rect2);
}


fun getDrawableByName(ctx: Context,name : String) : Int{
    return ctx.resources.getIdentifier(name,"drawable",ctx.packageName);
}


@SuppressLint("ResourceType")
fun createNewLayerInGlass(context : Context, id : Int,color : Int, parentLayout : LinearLayout) : TextView{

    val DEFAULT_HEIGHT = 50;
    val newDrink = TextView(context);
    newDrink.id = id;
    newDrink.width = parentLayout.width
    newDrink.height = DEFAULT_HEIGHT
    newDrink.setBackgroundColor(color);
    return newDrink;

}



