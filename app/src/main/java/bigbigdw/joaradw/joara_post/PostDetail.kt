package bigbigdw.joaradw.joara_post


import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebSettings
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import bigbigdw.joaradw.R
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.util.Util
import com.ahmadnemati.clickablewebview.ClickableWebView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
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
    var wcontents: ClickableWebView? = null
    var carouselPostBanner: CarouselView? = null
    var postBannerURLs: MutableList<String> = ArrayList()
    private var dialogPost: DialogPost? = null
    private var mContext: Context? = null

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
        carouselPostBanner = findViewById(R.id.Carousel_PostBanner)

        mContext = this

        setlayout()
    }

    fun setlayout() {
        toolbarTitle!!.text = "조아라 포스트"

        carouselPostBanner!!.setImageListener(imageListener)
        carouselPostBanner?.setImageClickListener {
            dialogPost = DialogPost(mContext!!, postBannerURLs, "", "CAROUSEL")
            dialogPost!!.show()
            val window: Window? = dialogPost?.window
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

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
                        vRedate!!.text = Util.changeDateType(redate, "")

                        //웹뷰 세팅
                        val mws: WebSettings = wcontents!!.settings
                        mws.loadWithOverviewMode = true
                        wcontents!!.setInitialScale(200)
                        mws.useWideViewPort = true

                        wcontents!!.setOnWebViewClickListener { url: String? ->
                            dialogPost = DialogPost(mContext!!, postBannerURLs, url!!, "IMAGE")
                            dialogPost!!.show()
//                            val window = dialogPost!!.window
//                            LinearLayout.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.MATCH_PARENT
//                            )
//                            window!!.setLayout(
//                                ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.MATCH_PARENT
//                            )
//                            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        }

                        // 다크 모드 판별
                        val currentNightMode =
                            resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                        //다크 모드가 아닐때
                        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
                            Util.loadPostHTMLData(wcontents!!, "#ffffff", content)
                        }
                        //다크 모드가 일때
                        else if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                            Util.loadPostHTMLData(wcontents!!, "#333333", content)
                        }

                        //캐러셀 세팅
                        if (isSlideshow.equals("Y")) {
                            if (slideshowData != null) {
                                for (i in slideshowData.indices) {
                                    val slideshowImage = slideshowData[i].slideshowImage
                                    postBannerURLs.add(slideshowImage!!)
                                }
                                carouselPostBanner!!.pageCount = postBannerURLs.size
                                carouselPostBanner?.visibility = View.VISIBLE


                            }
                        } else {
                            carouselPostBanner?.visibility = View.GONE
                        }


                    }
                } else {
                    Log.d("onResponse", "실패")
                }
            }

            override fun onFailure(call: Call<PostDetailResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    var imageListener = ImageListener { position: Int, imageView: ImageView ->
        imageView.adjustViewBounds = true
        val vto = imageView.viewTreeObserver
        vto.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                imageView.viewTreeObserver.removeOnPreDrawListener(this)
                val layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    imageView.measuredWidth
                )
                carouselPostBanner?.layoutParams = layoutParams
                return true
            }
        })
        Glide.with(applicationContext).load(postBannerURLs[position]).into(imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}