package bigbigdw.joaradw.main

import android.app.Activity
import android.os.Bundle
import bigbigdw.joaradw.R
import android.os.Looper
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.TextView
import bigbigdw.joaradw.BuildConfig
import bigbigdw.joaradw.login.LoginMain
import bigbigdw.joaradw.util.CheckTokenResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Splash : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        val tviewTitle: TextView? = findViewById(R.id.tview_title)

        if (BuildConfig.IS_NOVEL) {
            tviewTitle!!.text = "ⓒ 작품관리-DW 2021-2022"
        } else {
            tviewTitle!!.text = "ⓒ JOARA-DW 2021-2022"
        }

        if (BuildConfig.IS_DEV) {
            val pref = getSharedPreferences("HELPER", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("API", "https://api.joara.com")
            editor.apply()
        } else {
            val pref = getSharedPreferences("HELPER", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("API", "https://api-dev1.joara.com:7443")
            editor.apply()
        }
    }

    private fun startLoading() {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                if (BuildConfig.IS_NOVEL) {
                    //작품관리 진입
                    val intent = Intent(applicationContext, LoginMain::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivityIfNeeded(intent, 0)
                    finish()
                } else {
                    //소설 진입
                    val intent = Intent(this, Main::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivityIfNeeded(intent, 0)
                    finish()
                }
            },
            1000
        )
    }

    override fun onResume() {
        super.onResume()

        RetrofitMain.loginCheck(
            getSharedPreferences("LOGIN", MODE_PRIVATE).getString(
                "TOKEN",
                ""
            )
        )!!.enqueue(object : Callback<CheckTokenResult?> {
            override fun onResponse(
                call: Call<CheckTokenResult?>,
                response: Response<CheckTokenResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val status = it.status
                        if (status != 1) {
                            val editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit()
                            editor.clear()
                            editor.apply()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CheckTokenResult?>, t: Throwable) {
                Log.d("Splash: onResponse", "실패")
            }
        })

        startLoading()
    }
}