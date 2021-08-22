package bigbigdw.joaradw.joara_post

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
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
import android.widget.Toast
import androidx.core.widget.NestedScrollView

import bigbigdw.joaradw.login.LoginMain
import java.text.SimpleDateFormat

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
    var nestWrap: NestedScrollView? = null

    private var postId : String? = null
    private var token : String? = null
    var linearLayoutManager: LinearLayoutManager? = null
    private var adapter: AdapterPostComment? = null
    private val items = ArrayList<PostCommentData?>()

    var postBannerURLs: MutableList<String> = ArrayList()
    var page = 1
    var writeTotalCount = 0
    var totalCnt = 0
    var getCommentId : String? = null
    var getCommentDate : String? = null

    override fun onResume() {
        super.onResume()
        token = getSharedPreferences("LOGIN", MODE_PRIVATE).getString("TOKEN", "")
    }

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
        nestWrap = findViewById(R.id.nestWrap)

        adapter = AdapterPostComment(this,items)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mContext = this

        val intent = intent
        postId = intent.getStringExtra("POSTID")

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
        adapter!!.notifyDataSetChanged()

        //댓글 리스트
        nestWrap!!.setOnScrollChangeListener(commentScrollListener)

        //등록 버튼 클릭
        commentCommit!!.setOnClickListener {v ->
            run {
                if (!token.equals("")) {
                    postWriteComment(v)
                } else {
                    val myAlertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
                    myAlertBuilder.setTitle("로그인 필요")
                    myAlertBuilder.setMessage("댓글을 작성하려면 로그인이 필요합니다")
                    myAlertBuilder.setPositiveButton(
                        "로그인"
                    ) { _, _ ->
                        Toast.makeText(applicationContext, "로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(applicationContext, LoginMain::class.java)
                        startActivityIfNeeded(intent, 0)
                    }
                    myAlertBuilder.setNegativeButton(
                        "취소"
                    ) { _, _ -> }
                    myAlertBuilder.show()
                }
            }
        }

        //클릭리스너 등록
        adapter!!.setOnItemClickListener(object : AdapterPostComment.OnItemClickListener {

            override fun onItemClick(v: View?, position: Int, value: String?) {
                val item: PostCommentData? = adapter!!.getItem(position)
                if (item != null) {
                    if(value.equals("DELETE")){
                        if(item.commentId.equals("")){
                            //댓글 리스트 정보 최신화
                            refreshCommentData("DELETE","" , position)
                        } else {
                            postCommentDelete(item.commentId, position)
                        }
                    } else if(value.equals("APPLY")) {
                        commentEditWrap!!.visibility = View.VISIBLE
                        if(item.commentId.equals("")){
                            //댓글 리스트 정보 최신화
                            refreshCommentData("EDIT",item.comment, position)
                        } else {
                            putCommentEdit(item.comment,item.commentId, item.commentDate, position)
                        }
                    } else if(value.equals("EDIT")){
                        commentEditWrap!!.visibility = View.GONE
                    } else if(value.equals("CANCEL")){
                        commentEditWrap!!.visibility = View.VISIBLE
                    }
                }
            }
        })

        btnRecommend!!.setOnClickListener { postRecommend() }
    }

    private var commentScrollListener =
        NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight && page < ((totalCnt.toInt() / 25) + 2)) {
                page++
                getCommentData()
            }
        }

    //댓글 삭제
    fun postCommentDelete(commentId: String?, position: Int?){
        val myAlertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        myAlertBuilder.setTitle("댓글 삭제")
        myAlertBuilder.setMessage("댓글을 삭제하시겠습니까?")
        myAlertBuilder.setPositiveButton(
            "예"
        ) { _, _ ->
            val call = Retrofit.Builder()
                .baseUrl(HELPER.API)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(PostDeleteCommentService::class.java)
                .postRetrofit(
                    "2,9,6,3,4,5,19",
                    commentId,
                    HELPER.API_KEY,
                    HELPER.VER,
                    HELPER.DEVICE,
                    HELPER.DEVICE_ID,
                    HELPER.DEVICE_TOKEN,
                    token
                )

            call!!.enqueue(object : Callback<PostWriteCommentResult?> {
                override fun onResponse(call: Call<PostWriteCommentResult?>, response: Response<PostWriteCommentResult?>) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val status = it.status
                            val message = it.message
                            if (status == 1) {
                                adapter!!.deleteItem(position!!)
                                //댓글 수 관련
                                commentCount!!.text = (totalCnt - 1).toString()

                                Toast.makeText(applicationContext, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<PostWriteCommentResult?>, t: Throwable) {
                    Log.d("onFailure", "실패")
                }
            })
        }
        myAlertBuilder.setNegativeButton(
            "아니오"
        ) { _, _ -> }
        myAlertBuilder.show()
    }

    //댓글 수정
    fun putCommentEdit(comment : String?, commentId: String?, commentDate: String?, position: Int?){
        val call = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostEditCommentService::class.java)
            .putRetrofit(
                comment,
                "2,9,6,3,4,5,19",
                commentId,
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN,
                token
            )

        call!!.enqueue(object : Callback<PostWriteCommentResult?> {
            override fun onResponse(call: Call<PostWriteCommentResult?>, response: Response<PostWriteCommentResult?>) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val status = it.status
                        val message = it.message
                        if (status == 1) {

                            val data = PostCommentData(
                                getSharedPreferences("LOGIN", MODE_PRIVATE).getString("PROFILEIMG", ""),
                                getSharedPreferences("LOGIN", MODE_PRIVATE).getString("NICKNAME", ""),
                                commentDate,
                                commentId,
                                comment,
                                getSharedPreferences("LOGIN", MODE_PRIVATE).getString("MEMBERID", "")
                            )
                            adapter!!.editItem(data, position!!)

                            //댓글 수 관련
                            commentCount!!.text = (totalCnt - 1).toString()

                            Toast.makeText(applicationContext, "댓글이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostWriteCommentResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    //댓글 쓰기
    private fun postWriteComment(v : View){
        val call = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostWriteCommentService::class.java)
            .postRetrofit(
                postId,
                commentEditText!!.text.toString(),
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN,
                token
            )

        if(commentEditText!!.text.toString() == ""){
            Toast.makeText(applicationContext, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            call!!.enqueue(object : Callback<PostWriteCommentResult?> {
                override fun onResponse(
                    call: Call<PostWriteCommentResult?>,
                    response: Response<PostWriteCommentResult?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val status = it.status
                            val message = it.message
                            if(status == 1){
                                //댓글 수 관련
                                commentCount!!.text = (totalCnt + 1).toString()

                                //키보드 숨김
                                val inputMethodManager = mContext!!.getSystemService(
                                    INPUT_METHOD_SERVICE
                                ) as InputMethodManager

                                inputMethodManager.hideSoftInputFromWindow(
                                    v.windowToken,
                                    0
                                )

                                //시간 출력
                                val sdfNow = SimpleDateFormat("yy.MM.dd")
                                val time = sdfNow.format(Date(System.currentTimeMillis()))

                                Toast.makeText(applicationContext, "댓글이 작성되었습니다.", Toast.LENGTH_SHORT).show()

                                val data = PostCommentData(
                                    getSharedPreferences("LOGIN", MODE_PRIVATE).getString("PROFILEIMG", ""),
                                    getSharedPreferences("LOGIN", MODE_PRIVATE).getString("NICKNAME", ""),
                                    time,
                                    "",
                                    commentEditText!!.text.toString(),
                                    getSharedPreferences("LOGIN", MODE_PRIVATE).getString("MEMBERID", "")
                                )

                                //댓글 수 관련
                                if(totalCnt == 0){
                                    commentBlank!!.visibility = View.GONE
                                    writeTotalCount++
                                    commentCount!!.text = writeTotalCount.toString()
                                    adapter!!.changeItem(data, 0)
                                } else {
                                    adapter!!.changeItem(data, 0)
                                }

                                // 상위로 이동
                                Handler().postDelayed({
                                    recyclerView?.smoothScrollToPosition(0)
                                }, 200)
                                commentEditText!!.setText("");

                            } else {
                                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                }
                override fun onFailure(call: Call<PostWriteCommentResult?>, t: Throwable) {
                    Log.d("onFailure", "실패")
                }
            })
        }
    }

    //게시물 추천
    private fun postRecommend(){
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
    private fun getPostDetailData() {

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
                token,
                page.toString()
            )

        retrofit!!.enqueue(object : Callback<PostCommentListResult?> {
            override fun onResponse(
                call: Call<PostCommentListResult?>,
                response: Response<PostCommentListResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val comments = it.comments
                        totalCnt = it.totalCnt

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
                                val created = Util.changeDateType(comments[i].created ,"00.00.00")
                                val memberName = comments[i].member_name
                                val profile = comments[i].profile
                                val memberId = comments[i].memberId

                                items.add(
                                    PostCommentData(
                                        profile,
                                        memberName,
                                        created,
                                        commentId,
                                        comment,
                                        memberId
                                    )
                                )

                                commentBlank!!.visibility = View.GONE
                                recyclerView!!.visibility = View.VISIBLE
                            }
                        }
                    }

                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView!!.adapter = adapter
                    adapter!!.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<PostCommentListResult?>, t: Throwable) {
                commentBlank!!.visibility = View.VISIBLE
                recyclerView!!.visibility = View.GONE
            }
        })

    }

    //댓글 최신화
    private fun refreshCommentData(type : String?, comment : String?, position : Int?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PostCommentListService::class.java)
            .getRetrofit(
                postId,
                token,
                page.toString()
            )

        retrofit!!.enqueue(object : Callback<PostCommentListResult?> {
            override fun onResponse(
                call: Call<PostCommentListResult?>,
                response: Response<PostCommentListResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val comments = it.comments
                        totalCnt = it.totalCnt

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
                                getCommentId = comments[position!!].comment_id
                                getCommentDate = Util.changeDateType(comments[i].created ,"00.00.00")
                            }
                        }

                        if(type.equals("DELETE")){
                            postCommentDelete(getCommentId, position)
                        } else {
                            putCommentEdit(comment,getCommentId,getCommentDate,position)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostCommentListResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
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