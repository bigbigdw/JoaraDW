package bigbigdw.joaradw.novel

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.content.Intent
import android.os.Handler
import android.util.Log
import bigbigdw.joaradw.BuildConfig
import bigbigdw.joaradw.databinding.ActivitySplashBinding
import bigbigdw.joaradw.login.ActivityLogin
import bigbigdw.joaradw.util.CheckTokenResult
import bigbigdw.joaradw.writer.ActivityWriter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivitySplash : Activity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            if (BuildConfig.IS_WRITER) {
                tviewTitle.text = "ⓒ 이벤트-DW 2022"
            } else if (BuildConfig.IS_WRITER) {
                tviewTitle.text = "ⓒ 작품관리-DW 2021-2022"
            } else {
                tviewTitle.text = "ⓒ JOARA-DW 2021-2022"
            }

            if (BuildConfig.IS_DEV) {
                Log.d("IS_DEV", "DEV")
                val pref = getSharedPreferences("HELPER", MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("API", "https://api-dev1.joara.com:7443")
                editor.apply()
            } else {
                Log.d("IS_DEV", "STG")
                val pref = getSharedPreferences("HELPER", MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("API", "https://api.joara.com")
                editor.apply()
            }
        }

    }

    private fun startLoading() {
        Looper.myLooper()?.let {
            Handler(it).postDelayed(
                {
                    if (BuildConfig.IS_WRITER) {
                        Log.d("IS_WRITER", "IS_WRITER")

                        RetrofitNovel.loginCheck(
                            getSharedPreferences("LOGIN", MODE_PRIVATE).getString(
                                "TOKEN",
                                ""
                            ),
                            this
                        )?.enqueue(object : Callback<CheckTokenResult?> {
                            override fun onResponse(
                                call: Call<CheckTokenResult?>,
                                response: Response<CheckTokenResult?>
                            ) {
                                if (response.isSuccessful) {
                                    Log.d("IS_WRITER", "isSuccessful")
                                    response.body()?.let { it ->
                                        val status = it.status
                                        Log.d("IS_WRITER", status.toString())
                                        if (status != 1) {
                                            val editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit()
                                            editor.clear()
                                            editor.apply()

                                            val intent = Intent(applicationContext, ActivityLogin::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                                            startActivityIfNeeded(intent, 0)
                                            finish()
                                        } else {
                                            Log.d("IS_WRTIER", "NOVEL")
                                            //작품관리 진입
                                            val intent = Intent(applicationContext, ActivityWriter::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                                            startActivityIfNeeded(intent, 0)
                                            finish()
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<CheckTokenResult?>, t: Throwable) {
                                Log.d("Splash: onResponse", "실패")
                            }
                        })


                    } else if (BuildConfig.IS_LABS){
                        //소설 진입
                        val novelIntent = Intent(applicationContext, ActivityLogin::class.java)
                        novelIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        startActivityIfNeeded(novelIntent, 0)
                        finish()
                    } else {
                        //소설 진입
                        val novelIntent = Intent(applicationContext, ActivityNovel::class.java)
                        novelIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        startActivityIfNeeded(novelIntent, 0)
                        finish()
                    }
                },
                1000
            )
        }
    }

    override fun onResume() {
        super.onResume()

        RetrofitNovel.loginCheck(
            getSharedPreferences("LOGIN", MODE_PRIVATE).getString(
                "TOKEN",
                ""
            ),
            this
        )?.enqueue(object : Callback<CheckTokenResult?> {
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