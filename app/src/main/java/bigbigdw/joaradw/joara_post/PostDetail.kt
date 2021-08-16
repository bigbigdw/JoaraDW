package bigbigdw.joaradw.joara_post


import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import bigbigdw.joaradw.R
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class PostDetail : AppCompatActivity() {

    var vtitle: TextView? = null
    var vCategoryID: TextView? = null
    var vRedate: TextView? = null
    var toolbarTitle: TextView? = null
    var wcontents: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)

        vtitle = findViewById(R.id.Title)
        vCategoryID = findViewById(R.id.CategoryID)
        vRedate = findViewById(R.id.Redate)
        toolbarTitle = findViewById(R.id.toolbarTitle)
        wcontents = findViewById(R.id.Contents)

        setlayout()
    }

    fun setlayout() {
        vtitle!!.text = "조아라 포스트"

        getPostDetailData()
    }

    fun getPostDetailData() {

        val intent = intent
        val postId = intent.getStringExtra("POSTID")

        val retrofit = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostDetailService::class.java)
            .getRetrofit(postId)

        retrofit!!.enqueue(object : Callback<PostDetailResult?> {
            override fun onResponse(
                call: Call<PostDetailResult?>,
                response: Response<PostDetailResult?>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("@@@@", result.toString())

                    result?.let { it ->
                        val categoryName = it.post?.categoryName
                        val cntComment = it.post?.cntComment
                        val cntRead = it.post?.cntRead
                        val cntRecom = it.post?.cntRecom
                        val content = it.post?.content
                        val isComment = it.post?.isComment
                        val isRecom = it.post?.isRecom
                        val redate = it.post?.redate
                        val title = it.post?.title
                        val isSlideshow = it.post?.isSlideshow
                        val slideshowData = it.post?.slideshowData

                        vtitle!!.text = title
                        vCategoryID!!.text = categoryName
                        vRedate!!.text = Util.changeDateType(redate,"")

                        val mws: WebSettings = wcontents!!.settings
                        mws.loadWithOverviewMode = true
                        wcontents!!.setInitialScale(200)
                        mws.useWideViewPort = true

                        // 다크 모드 판별
                        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                        //다크 모드가 아닐때
                        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
                            Util.loadPostHTMLData(wcontents!!, "#ffffff",content)
                        }
                        //다크 모드가 일때
                        else if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                            Util.loadPostHTMLData(wcontents!!, "#333333",content)
                        }


                        if (slideshowData != null) {
                            for (i in slideshowData.indices) {
                                var slideshowImage = slideshowData[i].slideshowImage

                            }
                        }
                    }


                } else {
                    Log.d("@@@@!", "실패")
                }
            }

            override fun onFailure(call: Call<PostDetailResult?>, t: Throwable) {
                Log.d("@@@@!!", "실패")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}