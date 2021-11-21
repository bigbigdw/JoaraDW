package bigbigdw.joaradw.novel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.util.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitNovel {

    fun getMainBanner(token: String?, bannerType: String?, mContext: Context?): Call<MainBannerResult?>? {

        val API = mContext!!.getSharedPreferences("HELPER", AppCompatActivity.MODE_PRIVATE).getString("API", "")

        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBannerService::class.java)
            .getRetrofit(
                token,
                bannerType
            )
    }

    fun getIndexAPI(menuVer: String?, mContext: Context?): Call<IndexAPIResult?>? {

        val API = mContext!!.getSharedPreferences("HELPER", AppCompatActivity.MODE_PRIVATE).getString("API", "")

        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(IndexAPIService::class.java)
            .getRetrofit(
                menuVer,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN
            )
    }

    fun onClickLogout(token: String?, mContext: Context?): Call<LogoutResult?>? {

        val API = mContext!!.getSharedPreferences("HELPER", AppCompatActivity.MODE_PRIVATE).getString("API", "")

        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(LogoutService::class.java)
            .getRetrofit(
                "22%2C2",
                token,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN
            )
    }

    fun loginCheck(token: String?, mContext: Context?): Call<CheckTokenResult?>? {

        val API = mContext!!.getSharedPreferences("HELPER", AppCompatActivity.MODE_PRIVATE).getString("API", "")

        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CheckTokenService::class.java)
            .getRetrofit(token)
    }

    fun getMainBookData(token: String?, mContext: Context?): Call<MainBookResult?>? {

        val API = mContext!!.getSharedPreferences("HELPER", AppCompatActivity.MODE_PRIVATE).getString("API", "")

        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookService::class.java)
            .getRetrofit(
                token
            )
    }
}

