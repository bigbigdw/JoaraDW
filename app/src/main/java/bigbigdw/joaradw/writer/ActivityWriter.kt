package bigbigdw.joaradw.writer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import bigbigdw.joaradw.R
import bigbigdw.joaradw.test.ActivityTest
import bigbigdw.joaradw.test.RetrofitService
import bigbigdw.joaradw.test.Test_PostResult
import bigbigdw.joaradw.test.VerticalViewPager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityWriter : AppCompatActivity() {

    var mAdapter: ActivityTest.MyAdapter? = null
    var mPager: VerticalViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrtier)

    }
}