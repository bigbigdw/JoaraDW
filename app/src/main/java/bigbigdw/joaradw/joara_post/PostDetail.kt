package bigbigdw.joaradw.joara_post


import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebSettings
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var dialogPost: DialogPost? = null
    private var mContext: Context? = null
    var btnRecommend : LinearLayout? = null
    var textRecommend : TextView? = null
    var commentCount : TextView? = null
    var commentEditText : EditText? = null
    var commentEditWrap : LinearLayout? = null
    private var commentCommit : TextView? = null
    var commentBlank : LinearLayout? = null
    var tCommentBlankText : TextView? = null
    var recyclerView: RecyclerView? = null
    var commentWrap: LinearLayout? = null

    private var postId : String? = null
    private var token : String? = null
    var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: AdapterPostComment? = null
    private val items = ArrayList<PostCommentData>()

    var postBannerURLs: MutableList<String> = ArrayList()

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
        btnRecommend = findViewById(R.id.Btn_Recommend)
        textRecommend = findViewById(R.id.Text_Recommend)
        commentCount = findViewById(R.id.Comment_Count)
        commentEditText = findViewById(R.id.Comment_EditText)
        commentEditWrap = findViewById(R.id.CommentEditWrap)
        commentCommit = findViewById(R.id.Comment_Commit)
        commentBlank = findViewById(R.id.Comment_Blank)
        tCommentBlankText = findViewById(R.id.Comment_Blank_Text)
        recyclerView = findViewById(R.id.CommentList)
        commentWrap = findViewById(R.id.Comment_Wrap)

        adapter = AdapterPostComment(items)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mContext = this

        val intent = intent
        postId = intent.getStringExtra("POSTID")

        token = getSharedPreferences("LOGIN", MODE_PRIVATE).getString("LOGIN_TOKEN", "")

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
        getCommentData()

        btnRecommend!!.setOnClickListener { putRecommend() }
    }

    //게시물 추천
    private fun putRecommend(){
        val call = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostRecommendService::class.java)
            .postRetrofit(
                postId,
                "1",
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN,
                token
            )

        call!!.enqueue(object : Callback<PostRecommendResult?> {
            override fun onResponse(
                call: Call<PostRecommendResult?>,
                response: Response<PostRecommendResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val status = it.status
                        val message = it.message

                        if(status == 1){
                            btnRecommend!!.setBackgroundResource(R.drawable.bg_shape_comment_btn_gray)
                            textRecommend!!.text = "완료"
                            Toast.makeText(applicationContext, "추천이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
            override fun onFailure(call: Call<PostRecommendResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    //상세 데이터
    fun getPostDetailData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostDetailService::class.java)
            .getRetrofit(postId, token)

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

                        switchRecom(isRecom!!)

                        //웹뷰 세팅
                        val mws: WebSettings = wcontents!!.settings
                        mws.loadWithOverviewMode = true
                        wcontents!!.setInitialScale(200)
                        mws.useWideViewPort = true

                        wcontents!!.setOnWebViewClickListener { url: String? ->
                            dialogPost = DialogPost(mContext!!, postBannerURLs, url!!, "IMAGE")
                            dialogPost!!.show()
                            val window = dialogPost!!.window
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

                        //댓글 레이아웃 관련
                        if(isComment.equals("Y")){
                            commentEditWrap!!.visibility = View.VISIBLE
                            commentWrap!!.visibility = View.VISIBLE
                        } else {
                            commentEditWrap!!.visibility = View.GONE
                            commentWrap!!.visibility = View.GONE
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

    //추천 토글
    fun switchRecom(isRecommend: String?) {
        if (isRecommend == "Y") {
            btnRecommend!!.setBackgroundResource(R.drawable.bg_shape_comment_btn_gray)
            textRecommend!!.text = "완료"
        } else if (isRecommend == "N") {
            btnRecommend!!.setBackgroundResource(R.drawable.bg_shape_comment_btn)
            textRecommend!!.text = "추천"
        }
    }

    //캐러셀 이미지 리스너
    private var imageListener = ImageListener { position: Int, imageView: ImageView ->
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

    //댓글 정보
    private fun getCommentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostCommentListService::class.java)
            .getRetrofit(
                postId,
                token
            )

        retrofit!!.enqueue(object : Callback<PostCommentListResult?> {
            override fun onResponse(
                call: Call<PostCommentListResult?>,
                response: Response<PostCommentListResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val comments = it.comments
                        val totalCnt = it.totalCnt

                        //댓글 수 관련
                        commentCount!!.text = totalCnt.toString()

                        if(totalCnt.equals("0")){
                            commentBlank!!.visibility = View.VISIBLE
                            tCommentBlankText!!.text = "현재 댓글이 없습니다"
                        } else {
                            commentBlank!!.visibility = View.GONE
                        }


                        //댓글 관련
                        if (comments != null) {
                            for (i in comments.indices) {
                                val comment = comments[i].comment
                                val commentId = comments[i].comment_id
                                val created = comments[i].created
                                val memberName = comments[i].member_name
                                val profile = comments[i].profile

                                items.add(
                                    PostCommentData(
                                        profile,
                                        memberName,
                                        created,
                                        comment
                                    )
                                )

                                commentBlank!!.visibility = View.GONE
                                recyclerView!!.visibility = View.VISIBLE
                            }
                        }
                    }

                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView!!.adapter = adapter

                }
            }

            override fun onFailure(call: Call<PostCommentListResult?>, t: Throwable) {
                commentBlank!!.visibility = View.VISIBLE
                recyclerView!!.visibility = View.GONE
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