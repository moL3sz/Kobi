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
import android.content.Context
import kotlin.io.path.Path


class _Request(ctx : AppCompatActivity){
    var contex : AppCompatActivity
    val API_URL : String = "http://google.com"
    val queue  = Volley.newRequestQueue(ctx);
    init{
        contex = ctx;
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun loginRequest(username: String, password: String, token: String){
        try{
            //valamiért ez a geci nem működik, majd akkor kitalálunk valamit rá
            //alapvetőleg külső szerverre küld requestet de saját LAN serverre nem, idk miér
            val req = StringRequest(Request.Method.GET,API_URL, { response ->
                Toast.makeText(contex,"Response is ${response.substring(0,500)}",Toast.LENGTH_LONG).show();
            },
                {
                    Toast.makeText(contex, "Nope",Toast.LENGTH_LONG).show();
                })
            queue.add(req);
        }
        catch (e: Exception){
            e.printStackTrace()
        }

    }
    fun registrateRequest(username: String, password: String, token: String, email : String){
        try {
            val req = StringRequest(
                Request.Method.POST, API_URL,
                { response ->
                    {
                        //TODO handle response (JSON) from the API

                        //ide kell majd valamit csinálunk
                        //azaz ha jön a szervertől válasz -> fasza vagy benne vagy az adatbázisban
                        //akkor vissza kerül a login layoutba ahol be tud jelentkezni
                        //talán meg lehetne csinálni automatication a logint, regisztáció után is

                    }
                },
                {
                    Toast.makeText(contex, "ELbasztunk valamit", Toast.LENGTH_SHORT)
                },
                );

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
fun readTokenFromFile() : Boolean{
    try {
        //
        //default path for token : assets/LOCAL_TOKEN
        val TOKEN_FILE : File = File("/data/user/0/com.example.myapplication/LOCAL_TOKEN.dat")
        Log.i("Essentials","JUJUUUU")
        val fis : FileInputStream = FileInputStream(TOKEN_FILE)
        val in_ : DataInputStream = DataInputStream(fis)
        val br : BufferedReader = BufferedReader( InputStreamReader(in_))
        Log.i("Essentials","JUJUUUU")
        //kiolvasssuk a tokent a filebol (FLAG)
        //ha van benne valami vagy egyátalán létezik a cucc,
        //akkor visszatérünk trueval, minden más esetben falseal (true -> regisztrált már vagy loginolt valaha)

        var strLine : String = ""
        var READ_TOKEN : String = ""
        while (true){
            strLine = br.readLine()
            if(strLine == null){
                break;
            }
            READ_TOKEN = strLine
        }
        Log.i("Essentials",READ_TOKEN)
        br.close()
        in_.close()
        fis.close()
        Log.i("Essentials",READ_TOKEN)
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
