package bigbigdw.joaradw.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import bigbigdw.joaradw.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.SharedPreferences




class ActivityTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val service1 = retrofit.create(RetrofitService::class.java)

        val call = service1.getPosts("1")

        call!!.enqueue(object : Callback<Test_PostResult?> {
            override fun onResponse(call: Call<Test_PostResult?>, response: Response<Test_PostResult?>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("@@@@", result.toString())
                }
            }

            override fun onFailure(call: Call<Test_PostResult?>, t: Throwable) {
                Log.d("@@@@", "실패")
            }
        })
    }

    // 값 불러오기
    private fun getPreferences() {
        val pref = getSharedPreferences("pref", MODE_PRIVATE)
        pref.getString("hi", "")
    }


    // 값 저장하기
    fun savePreferences(token: String) {
        val pref = getSharedPreferences("pref", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("hi", token)
        editor.apply()
    }

    // 값(Key Data) 삭제하기
    private fun removePreferences() {
        val pref = getSharedPreferences("pref", MODE_PRIVATE)
        val editor = pref.edit()
        editor.remove("hi")
        editor.apply()
    }

    // 값(ALL Data) 삭제하기
    private fun removeAllPreferences() {
        val pref = getSharedPreferences("pref", MODE_PRIVATE)
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }
}