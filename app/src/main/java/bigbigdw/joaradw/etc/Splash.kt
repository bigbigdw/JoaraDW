package bigbigdw.joaradw.etc

import android.app.Activity
import android.os.Bundle
import bigbigdw.joaradw.R
import android.os.Looper
import android.content.Intent
import android.os.Handler
import android.util.Log
import bigbigdw.joaradw.Config
import bigbigdw.joaradw.main.Main
import bigbigdw.joaradw.util.CheckTokenResult
import bigbigdw.joaradw.util.CheckTokenService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Splash : Activity() {
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
    }

    private fun startLoading() {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                val intent = Intent(this, Main::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivityIfNeeded(intent, 0)
                finish()
            },
            1000
        )
    }

    override fun onResume() {
        super.onResume()

        val call = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CheckTokenService::class.java)
            .getRetrofit(token)

        call!!.enqueue(object : Callback<CheckTokenResult?> {
            override fun onResponse(call: Call<CheckTokenResult?>, response: Response<CheckTokenResult?>) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val status = it.status

                        if(!status.equals("1")){
                            Config.deleteJSON()
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