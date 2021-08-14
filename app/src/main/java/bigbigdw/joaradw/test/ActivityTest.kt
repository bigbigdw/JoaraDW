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

class ActivityTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val service1 = retrofit.create(RetrofitService::class.java)

        val call = service1.getPosts("1")

        call!!.enqueue(object : Callback<PostResult?> {
            override fun onResponse(call: Call<PostResult?>, response: Response<PostResult?>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("@@@@", result.toString())
                }
            }

            override fun onFailure(call: Call<PostResult?>, t: Throwable) {
                Log.d("@@@@", "실패")
            }
        })
    }
}