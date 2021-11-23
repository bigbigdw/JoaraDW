package bigbigdw.joaradw.writer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitWriter {

    fun getBookNum(token: String?, mContext: Context?): Call<WriterBookCountResult?>? {

        val API = mContext!!.getSharedPreferences("HELPER", AppCompatActivity.MODE_PRIVATE).getString("API", "")

        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WrtierBookCount::class.java)
            .getRetrofit(
                token
            )
    }

    fun getWriterLevel(token: String?, mContext: Context?): Call<WriterMemberLevelResult?>? {

        val API = mContext!!.getSharedPreferences("HELPER", AppCompatActivity.MODE_PRIVATE).getString("API", "")

        return Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WrtierLevel::class.java)
            .getRetrofit(
                token
            )
    }
}