package bigbigdw.joaradw.fragment_main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.base.BookBaseFragment
import bigbigdw.joaradw.book.AdapterBookListA
import bigbigdw.joaradw.book.BookListResult
import bigbigdw.joaradw.book.BookListDataA
import bigbigdw.joaradw.book.RetrofitBookList
import bigbigdw.joaradw.book_detail.BookDetailCover
import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.etc.BookList
import bigbigdw.joaradw.main.*
import bigbigdw.joaradw.main.RetrofitMain
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FragmentMainTabFirst : BookBaseFragment() {
    private val historyAdapter = OLD_MainBookListAdapterA()
    private val hobbyAdapter = OLD_MainBookListAdapterA()
    private val mdNovelAdapter = OLD_MainBookListAdapterA()
    private val mdWebtoonAdapter =
        OLD_MainBookListAdapterA()
    private val festivalAdapter = MainBookListAdapterB()
    private val promiseAdapter = MainBookListAdapterB()
    private val kidamuAdapter = MainBookListAdapterB()
    private val items = ArrayList<OLD_MainBookListData>()

    var etc = "&page=1&offset=10"
    var showType = "&show_type=home"
    var goToFes: TextView? = null
    var goToPromised: TextView? = null
    var goToKidamu: TextView? = null
    var goToNoty: TextView? = null
    var userPicked: TextView? = null
    var bookRecommend: TextView? = null

    private var mainBookAdapter: AdapterMainBookData? = null
    var linearLayoutManager: LinearLayoutManager? = null
    private val mainBookItems = ArrayList<MainBookData?>()

    private val bookListItemsAHistory = ArrayList<BookListDataA?>()
    private var bookListAdapterAHistory: AdapterBookListA? = null
    var linearLayoutManagerHistory: LinearLayoutManager? = null
    var userNameCategory: TextView? = null
    var goToHistory: TextView? = null
    var mainHistoryBookList: RecyclerView? = null
    var main_booklist_history: LinearLayout? = null

    private val bookListItemsAHooby = ArrayList<BookListDataA?>()
    private var bookListAdapterARecommend: AdapterBookListA? = null
    var linearLayoutManagerRecommend: LinearLayoutManager? = null
    var bookSnipe: TextView? = null
    var main_booklist_hobby: LinearLayout? = null
    var recyclerviewHobby: RecyclerView? = null

    private val bookListItemsAMDNovel = ArrayList<BookListDataA?>()
    private var bookListAdapterAMDNovel: AdapterBookListA? = null
    var linearLayoutManagerMDNovel: LinearLayoutManager? = null
    var main_booklist_MDNovel: LinearLayout? = null
    var recyclerviewMDNovel: RecyclerView? = null

    private val bookListItemsAMDWebtoon = ArrayList<BookListDataA?>()
    private var bookListAdapterAMDWebtoon: AdapterBookListA? = null
    var linearLayoutManagerMDWebtoon: LinearLayoutManager? = null
    var main_booklist_MDWebtoon: LinearLayout? = null
    var recyclerviewMDWebtoon: RecyclerView? = null

    var mainBanner: CarouselView? = null
    var mainBannerMid: CarouselView? = null
    var mainBannerURLs: MutableList<String> = ArrayList()
    var mainBannerMidURLs: MutableList<String> = ArrayList()
    var MainBookList: RecyclerView? = null

    var token: String? = null
    var userStatus: String? = null
    var paramToken: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_tab_first, container, false)

        userPicked = root.findViewById(R.id.UserPicked)
        bookRecommend = root.findViewById(R.id.BookRecommend)
        mainBanner = root.findViewById(R.id.Carousel_MainBanner)
        mainBannerMid = root.findViewById(R.id.Carousel_MainBanner_Mid)

        goToFes = root.findViewById(R.id.GoToFes)
        goToPromised = root.findViewById(R.id.GoToPromised)
        goToKidamu = root.findViewById(R.id.GoToKidamu)
        goToNoty = root.findViewById(R.id.GoToNoty)

        MainBookList = root.findViewById(R.id.MainBookList)

        goToHistory = root.findViewById(R.id.GoToHistory)
        userNameCategory = root.findViewById(R.id.UserNameCategory)
        mainHistoryBookList = root.findViewById(R.id.Main_HistoryBookList)
        main_booklist_history = root.findViewById(R.id.main_booklist_history)

        bookSnipe = root.findViewById(R.id.BookSnipe)
        main_booklist_hobby = root.findViewById(R.id.main_booklist_hobby)
        recyclerviewHobby = root.findViewById(R.id.Main_HobbyBookList)

        main_booklist_MDNovel = root.findViewById(R.id.main_booklist_mdnovel)
        recyclerviewMDNovel = root.findViewById(R.id.Main_MDNovelList)

        main_booklist_MDWebtoon = root.findViewById(R.id.main_booklist_mdwebtoon)
        recyclerviewMDWebtoon = root.findViewById(R.id.Main_MDWebtoonList)

        userNameCategory!!.text =
            requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
                .getString("NICKNAME", "")
        token = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")
        userStatus = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("STATUS", "")
        paramToken = "&token=$token"

        setLayout(root)
        return root
    }

    fun setLayout(root: View?) {
        val assetManager = requireActivity().assets
        val queue = Volley.newRequestQueue(requireActivity())
        val bookC = "$paramToken&section_mode=contest_free_award$etc$showType"
        val resultEtcUrl = "$paramToken&book_code=&offset=50"
        val resultEtcUrlSection = paramToken + API.SECTION_MODE + "&page=1&offset=50" + showType

        //배너
        getMainBanner("app_home_top_banner",mainBanner!!,mainBannerURLs)
        getMainBanner("app_main2016_event",mainBannerMid!!,mainBannerMidURLs)
        mainBanner!!.setImageListener(imageListener)
        mainBannerMid!!.setImageListener(imageListenerMid)

        //메인 북 데이터
        mainBookAdapter = AdapterMainBookData(requireContext(),mainBookItems)
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        getMainBookData()

        if (userStatus == "1") {
            bookListAdapterAHistory = AdapterBookListA(requireContext(),bookListItemsAHistory)
            linearLayoutManagerHistory = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            getBookListTypeA(main_booklist_history, bookListAdapterAHistory, linearLayoutManagerHistory,mainHistoryBookList, "HISTORY")

            bookListAdapterARecommend = AdapterBookListA(requireContext(),bookListItemsAHooby)
            linearLayoutManagerRecommend = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            getBookListTypeA(main_booklist_hobby, bookListAdapterARecommend, linearLayoutManagerRecommend,recyclerviewHobby, "RECOMMEND")
        }

        bookListAdapterAMDNovel = AdapterBookListA(requireContext(),bookListItemsAMDNovel)
        linearLayoutManagerMDNovel = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        getBookListTypeA(main_booklist_MDNovel, bookListAdapterAMDNovel, linearLayoutManagerMDNovel,recyclerviewMDNovel, "MDNOVEL")

        bookListAdapterAMDWebtoon = AdapterBookListA(requireContext(),bookListItemsAMDWebtoon)
        linearLayoutManagerMDWebtoon = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        getBookListTypeA(main_booklist_MDWebtoon, bookListAdapterAMDWebtoon, linearLayoutManagerMDWebtoon,recyclerviewMDWebtoon, "WEBTOON")


        BookList.bookListB(
            root,
            assetManager,
            "Main_FestivalBookList.json",
            R.id.Main_FestivalBookList,
            festivalAdapter
        )
        BookList.bookListB(
            root,
            assetManager,
            "Main_PromisedBookList.json",
            R.id.Main_PromisedBookList,
            promiseAdapter
        )
        BookList.bookListB(
            root,
            assetManager,
            "Main_KidamuBookList.json",
            R.id.Main_KidamuBookList,
            kidamuAdapter
        )
//        val userPickedAdapter = MainBookListAdapterC(items)
//        val notyAdapter = MainBookListAdapterC(items)
//        val recommendAdapter = MainBookListAdapterC(items)
//        bookListC(
//            root,
//            API.HOME_LIST_JOA,
//            "$paramToken&section_mode=contest_free_award$etc$showType",
//            R.id.Main_UserPickedList,
//            userPickedAdapter,
//            R.id.main_booklist_userpicked
//        )
//        bookListC(
//            root,
//            API.HOME_LIST_JOA,
//            "$paramToken&section_mode=noty$etc$showType",
//            R.id.Main_NotyList,
//            notyAdapter,
//            R.id.main_booklist_noty
//        )
//        bookListC(
//            root,
//            API.HOME_LIST_JOA,
//            paramToken + API.SECTION_MODE + etc + showType,
//            R.id.Main_RecommendedList,
//            recommendAdapter,
//            R.id.main_booklist_recommeded
//        )
//        val noblessTodayBestAdapter = MainBookListAdapterD(items)
//        val premiumToadyBestAdapter = MainBookListAdapterD(items)
//        val couponToadyBestAdapter = MainBookListAdapterD(items)
//        bookListD(
//            root,
//            API.HOME_LIST_JOA,
//            "$paramToken&section_mode=todaybest&store=nobless&orderby=cnt_best$etc$showType",
//            R.id.Main_NoblessTodayBestList,
//            noblessTodayBestAdapter,
//            R.id.main_nobelsstodaybest
//        )
//        bookListD(
//            root,
//            API.HOME_LIST_JOA,
//            "$paramToken&section_mode=todaybest&store=premium&orderby=cnt_best$etc$showType",
//            R.id.Main_PremiumTodayBestList,
//            premiumToadyBestAdapter,
//            R.id.main_premiumtodaybest
//        )
//        bookListD(
//            root,
//            API.HOME_LIST_JOA,
//            "$paramToken&section_mode=support_coupon&orderby=cnt_best$etc$showType",
//            R.id.Main_CouponTodayBestList,
//            couponToadyBestAdapter,
//            R.id.main_coupontodaybest
//        )
//
//        goToHistory!!.setOnClickListener { v: View? ->
//            gotoMore(
//                1,
//                R.id.action_Fragment_Main_to_Fragment_Fav,
//                this
//            )
//        }
//        goToFes!!.setOnClickListener { v: View? ->
//            gotoMore(
//                1,
//                R.id.action_Fragment_Main_to_Fragment_New,
//                this
//            )
//        }
//        goToPromised!!.setOnClickListener { v: View? ->
//            gotoMore(
//                4,
//                R.id.action_Fragment_Main_to_Fragment_New,
//                this
//            )
//        }
//        goToKidamu!!.setOnClickListener { v: View? ->
//            gotoMore(
//                2,
//                R.id.action_Fragment_Main_to_Fragment_New,
//                this
//            )
//        }
//        goToNoty!!.setOnClickListener { v: View? ->
//            gotoMore(
//                3,
//                R.id.action_Fragment_Main_to_Fragment_New,
//                this
//            )
//        }
//        bookSnipe!!.setOnClickListener { v: View? ->
//            goToBookPageEtc(
//                "취향 저격",
//                API.BOOK_RECOMMEND_LIST_API_JOA,
//                resultEtcUrl
//            )
//        }
//        userPicked!!.setOnClickListener { v: View? ->
//            goToBookPageEtc(
//                "수상작",
//                API.HOME_LIST_JOA,
//                resultEtcUrlSection
//            )
//        }
//        bookRecommend!!.setOnClickListener { v: View? ->
//            goToBookPageEtc(
//                "천만 인증",
//                API.HOME_LIST_JOA,
//                resultEtcUrlSection
//            )
//        }
    }

    //이어보기, 추천작품, 웹툰
    private fun getBookListTypeA(wrap: LinearLayout?, adapter: AdapterBookListA?, linearLayoutManager : LinearLayoutManager?, recyclerView: RecyclerView?, type:String?){

        wrap!!.visibility = View.VISIBLE

        val call: Call<BookListResult?>? =
            when {
            type.equals("HISTORY") -> {
                RetrofitBookList.getUserHistoryBooks(token, "1", "25")
            }
            type.equals("RECOMMEND") -> {
                RetrofitBookList.getRecommendBooks(token, "1", "25")
            }
                type.equals("MDNOVEL") -> {
                    RetrofitBookList.getMDBooks(token, "1", "25")
                }
            else -> {
                RetrofitBookList.getMDWebtoon(token)
            }
        }

        val bookListItemsA : ArrayList<BookListDataA?> =
            when {
                type.equals("HISTORY") -> {
                    bookListItemsAHistory
                }
                type.equals("RECOMMEND") -> {
                    bookListItemsAHooby
                }
                type.equals("MDNOVEL") -> {
                    bookListItemsAMDNovel
                }
                else ->  bookListItemsAMDWebtoon
            }

        call!!.enqueue(object : Callback<BookListResult?> {
            override fun onResponse(
                call: Call<BookListResult?>,
                response: Response<BookListResult?>
            ) {
                if(type.equals("WEBTOON")){
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val books = it.webtoons
                            if (books != null) {
                                for (i in books.indices) {
                                    Log.d("@@@@","Webtoon")
                                    val bookImg = books[i].webtoon_img
                                    val isAdult = books[i].is_adult
                                    val subject = books[i].webtoon_title

                                    bookListItemsA.add(
                                        BookListDataA(
                                            isAdult,
                                            subject,
                                            bookImg,
                                            "",
                                            "",
                                            type
                                        )
                                    )

                                    recyclerView!!.layoutManager = linearLayoutManager
                                    recyclerView.adapter = adapter
                                }
                            }
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                } else{
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val books = it.books
                            if (books != null) {
                                for (i in books.indices) {

                                    val bookCode = books[i].bookCode
                                    val bookImg = books[i].bookImg
                                    val historySortno = books[i].historySortno
                                    val subject = books[i].subject
                                    val writerName = books[i].writerName

                                    bookListItemsA.add(
                                        BookListDataA(
                                            writerName,
                                            subject,
                                            bookImg,
                                            bookCode,
                                            historySortno,
                                            type
                                        )
                                    )

                                    recyclerView!!.layoutManager = linearLayoutManager
                                    recyclerView.adapter = adapter
                                }
                            }
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<BookListResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

        adapter!!.setOnItemClickListener(object : AdapterBookListA.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                if(!type.equals("WEBTOON")){
                    val item: BookListDataA? = bookListAdapterAHistory!!.getItem(position)
                    val intent = Intent(
                        requireContext().applicationContext,
                        BookDetailCover::class.java
                    )
                    intent.putExtra("BookCode", String.format("%s", item!!.bookCode))
                    intent.putExtra("token", String.format("%s", token))
                    startActivity(intent)
                }
            }
        })
    }

    //메인 배너
    private fun getMainBanner(
        bannerType: String?,
        banner: CarouselView,
        bannerURLs: MutableList<String> = ArrayList(),
    ) {

        RetrofitMain.getMainBanner(token,bannerType)!!.enqueue(object : Callback<MainBannerResult?> {
            override fun onResponse(
                call: Call<MainBannerResult?>,
                response: Response<MainBannerResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val bannerArray = it.banner

                        if (bannerArray != null) {
                            for (i in bannerArray.indices) {
                                val img = bannerArray[i].imgfile
                                bannerURLs.add(img!!)
                            }
                        }
                        banner.pageCount = bannerURLs.size
                        banner.slideInterval = 4000
                        banner.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<MainBannerResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    //메인 북 데이터
    private fun getMainBookData() {
        RetrofitMain.getMainBookData(token)!!.enqueue(object : Callback<MainBookResult?> {
            override fun onResponse(
                call: Call<MainBookResult?>,
                response: Response<MainBookResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val mainInfo = it.mainInfo
                        val homeVer = it.homeVer

                        if (mainInfo != null) {
                            for (i in mainInfo.indices) {

                                val sectionApiUrl = mainInfo[i].sectionApiUrl
                                val sectionCategory = mainInfo[i].sectionCategory
                                val sectionSubType = mainInfo[i].sectionSubType
                                val sectionType = mainInfo[i].sectionType

                                //뒤에꺼만 들고옴 + 맨 마지막 빈거 제거
                                if(i > 7 && !sectionApiUrl.equals("")){
                                    mainBookItems.add(
                                        MainBookData(
                                            sectionCategory,
                                            sectionSubType,
                                            sectionType
                                        )
                                    )
                                }

                                MainBookList!!.layoutManager = linearLayoutManager
                                MainBookList!!.adapter = mainBookAdapter
                            }
                        }
                    }
                    mainBookAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MainBookResult?>, t: Throwable) {
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
                val doubled = imageView.measuredWidth / 1.704 + 5
                val finalHeight = Math.round(doubled).toString().toInt()
                val layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    finalHeight
                )
                mainBanner!!.layoutParams = layoutParams
                return true
            }
        })
        Glide.with(requireActivity().applicationContext).load(mainBannerURLs[position])
            .into(imageView)
    }
    var imageListenerMid = ImageListener { position: Int, imageView: ImageView ->
        imageView.adjustViewBounds = true
        val vtoMid = imageView.viewTreeObserver
        vtoMid.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                imageView.viewTreeObserver.removeOnPreDrawListener(this)
                val doubled = (imageView.measuredWidth / 6).toDouble()
                val finalHeight = Math.round(doubled).toString().toInt()
                val layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    finalHeight
                )
                mainBannerMid!!.layoutParams = layoutParams
                return true
            }
        })
        Glide.with(requireActivity().applicationContext).load(mainBannerMidURLs[position])
            .into(imageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainBanner!!.removeAllViews()
        mainBannerURLs = ArrayList()
    }
}