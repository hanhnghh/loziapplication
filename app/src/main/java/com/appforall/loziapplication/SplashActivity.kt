package com.appforall.loziapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Created by Hanh Nguyen on 8/13/2017.
 */
class SplashActivity : AppCompatActivity() {

    val updateUrl = "https://mymotivationwebblog.wordpress.com/2017/08/13/lozi-configuration/"
    lateinit var okHttpClient : OkHttpClient
    lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val decorView = window.decorView
        // Hide the status bar.
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        okHttpClient = initOkHttpClient()
        getUpdateInfo()
    }

    fun initOkHttpClient() : OkHttpClient {
        var builder = OkHttpClient.Builder()
        builder.connectTimeout(60*1000, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000, TimeUnit.MILLISECONDS)
        return builder.build()
    }

    fun getUpdateInfo(){
        var observable = Observable.fromCallable(object : Callable<UpdateInfoEntity> {
            override fun call(): UpdateInfoEntity {
                val doc = Jsoup.connect(updateUrl).get()
                val bodyContent = doc.body().toString()
                val start = bodyContent.indexOf("[abc]")
                val end = bodyContent.indexOf("[xyz]")
                val link = bodyContent.substring(start + 5, end)

                Log.d("han.hanh", link)
                var request = Request.Builder()
                        .url(link)
                        .build()
                var gson = GsonBuilder().create()
                var response = okHttpClient.newCall(request).execute()
                var inputStream = response.body()?.byteStream()
                var reader = BufferedReader(InputStreamReader(inputStream))
                var line : String? = null
                var result : String? = ""
                while({line = reader.readLine(); line}() != null){
                    result += line
                }
                var entity = gson.fromJson(result, UpdateInfoEntity::class.java)
                Log.d("han.hanh", entity.update_link)
                Log.d("han.hanh", entity.interstitial_ads_1)
                return entity
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(object : Observer<UpdateInfoEntity> {
            override fun onNext(t: UpdateInfoEntity?) {
                saveUpdateInfo(t)
                if(t?.update == true){
                    showUpdateDialog()
                }

            }

            override fun onError(e: Throwable?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onCompleted() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        })
    }

    inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.func()
        editor.apply()
    }

    fun SharedPreferences.Editor.put(pair: Pair<String?, Any?>) {
        val key = pair.first
        val value = pair.second
        when(value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }


    fun saveUpdateInfo(t: UpdateInfoEntity?) {
        prefs.edit {
            put(getString(R.string.interstitial_ads_1) to t?.interstitial_ads_1)
            put(getString(R.string.interstitial_ads_2) to t?.interstitial_ads_2)
            put(getString(R.string.interstitial_ads_3) to t?.interstitial_ads_3)
            put(getString(R.string.banner_ads) to t?.banner_ads)
            put(getString(R.string.update_link_key) to t?.update_link)
            put(getString(R.string.update_title_key) to t?.update_title)
            put(getString(R.string.update_message_key) to t?.update_message)
        }
    }

    fun showUpdateDialog(){

    }

}