package bigbigdw.joaradw.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.util.LoginResult
import bigbigdw.joaradw.util.LoginService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitLogin {

    fun postLogin(idCheck : String?, pwCheck: String?, mContext: Context?): Call<LoginResult?>? {

        val API = mContext!!.getSharedPreferences("HELPER", AppCompatActivity.MODE_PRIVATE).getString("API", "")

        val call = Retrofit.Builder()
            .baseUrl(API!!)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(LoginService::class.java)
            .postRetrofit(
                idCheck,
                pwCheck,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN
            )
        return call
    }
}