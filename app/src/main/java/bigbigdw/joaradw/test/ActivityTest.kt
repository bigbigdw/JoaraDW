package bigbigdw.joaradw.test

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import bigbigdw.joaradw.R

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityTest : AppCompatActivity() {

    var mAdapter: MyAdapter? = null
    var mPager: VerticalViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        mAdapter = MyAdapter(supportFragmentManager)
        mPager = findViewById(R.id.viewpager)
        mPager!!.adapter = mAdapter

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

    class MyAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
        override fun getCount(): Int {
            return NUMBER_OF_PAGES
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> FragmentOne.newInstance(0, Color.WHITE)
                1 ->                     // return a different Fragment class here
                    // if you want want a completely different layout
                    FragmentOne.newInstance(1, Color.CYAN)
                else -> null!!
            }
        }
    }

    class FragmentOne : Fragment() {
        private var mNum = 0
        private var mColor = 0
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            mNum = if (arguments != null) requireArguments().getInt(MY_NUM_KEY) else 0
            mColor =
                if (arguments != null) requireArguments().getInt(MY_COLOR_KEY) else Color.BLACK
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val v: View = inflater.inflate(R.layout.test_fragment, container, false)
            v.setBackgroundColor(mColor)
            val textView: TextView = v.findViewById(R.id.textview)
            textView.text = "Page $mNum"
            return v
        }

        companion object {
            private const val MY_NUM_KEY = "num"
            private const val MY_COLOR_KEY = "color"

            // You can modify the parameters to pass in whatever you want
            fun newInstance(num: Int, color: Int): FragmentOne {
                val f = FragmentOne()
                val args = Bundle()
                args.putInt(MY_NUM_KEY, num)
                args.putInt(MY_COLOR_KEY, color)
                f.arguments = args
                return f
            }
        }
    }

    companion object {
        const val NUMBER_OF_PAGES = 2
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