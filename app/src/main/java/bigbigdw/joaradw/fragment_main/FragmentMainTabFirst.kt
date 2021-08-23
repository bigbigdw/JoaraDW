package bigbigdw.joaradw.fragment_main

import bigbigdw.joaradw.base.BookBaseFragment
import bigbigdw.joaradw.main.MainBookListAdapterA
import bigbigdw.joaradw.main.MainBookListAdapterB
import bigbigdw.joaradw.main.MainBookListData
import com.synnapps.carouselview.CarouselView
import android.widget.TextView
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import bigbigdw.joaradw.R
import bigbigdw.joaradw.JOARADW
import android.view.View
import com.android.volley.toolbox.Volley
import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.etc.BookList
import bigbigdw.joaradw.main.MainBookListAdapterC
import bigbigdw.joaradw.main.MainBookListAdapterD
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import java.util.ArrayList

class FragmentMainTabFirst : BookBaseFragment(), InterfaceMainBannerAPI {
    private val historyAdapter = MainBookListAdapterA()
    private val hobbyAdapter = MainBookListAdapterA()
    private val mdNovelAdapter = MainBookListAdapterA()
    private val mdWebtoonAdapter = MainBookListAdapterA()
    private val festivalAdapter = MainBookListAdapterB()
    private val promiseAdapter = MainBookListAdapterB()
    private val kidamuAdapter = MainBookListAdapterB()
    private val items = ArrayList<MainBookListData>()
    var mainBanner: CarouselView? = null
    var mainBannerMid: CarouselView? = null
    var mainBannerURLs: List<String> = ArrayList()
    var mainBannerMidURLs: List<String> = ArrayList()
    var userToken = ""
    var userStatus = ""
    var paramToken: String? = null
    var etc = "&page=1&offset=10"
    var showType = "&show_type=home"
    var userNameCategory: TextView? = null
    var goToHistory: TextView? = null
    var goToFes: TextView? = null
    var goToPromised: TextView? = null
    var goToKidamu: TextView? = null
    var goToNoty: TextView? = null
    var bookSnipe: TextView? = null
    var userPicked: TextView? = null
    var bookRecommend: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_tab_first, container, false)
        userNameCategory = root.findViewById(R.id.UserNameCategory)
        bookSnipe = root.findViewById(R.id.BookSnipe)
        userPicked = root.findViewById(R.id.UserPicked)
        bookRecommend = root.findViewById(R.id.BookRecommend)
        mainBanner = root.findViewById(R.id.Carousel_MainBanner)
        mainBannerMid = root.findViewById(R.id.Carousel_MainBanner_Mid)
        goToHistory = root.findViewById(R.id.GoToHistory)
        goToFes = root.findViewById(R.id.GoToFes)
        goToPromised = root.findViewById(R.id.GoToPromised)
        goToKidamu = root.findViewById(R.id.GoToKidamu)
        goToNoty = root.findViewById(R.id.GoToNoty)
        checkToken()
        val app = requireActivity().applicationContext as JOARADW
        if (app.isLogined) {
            userToken = app.token
            userStatus = app.status
            userNameCategory!!.setText(app.name)
            paramToken = "&token=$userToken"
        }
        setLayout(root)
        return root
    }

    fun setLayout(root: View?) {
        val assetManager = requireActivity().assets
        val queue = Volley.newRequestQueue(requireActivity())
        val bookC = "$paramToken&section_mode=contest_free_award$etc$showType"
        val resultEtcUrl = "$paramToken&book_code=&offset=50"
        val resultEtcUrlSection = paramToken + API.SECTION_MODE + "&page=1&offset=50" + showType
        InterfaceMainBannerAPI.setBanner(
            mainBannerMid,
            mainBannerMidURLs,
            queue,
            paramToken,
            "&page=&banner_type=app_main2016_event"
        )
        InterfaceMainBannerAPI.setBanner(
            mainBanner,
            mainBannerURLs,
            queue,
            paramToken,
            "&page=0&banner_type=app_home_top_banner"
        )
        mainBanner!!.setImageListener(imageListener)
        mainBannerMid!!.setImageListener(imageListenerMid)
        if (userStatus == "1") {
            bookListA(
                root,
                API.USER_HISTORYBOOKS_JOA,
                "$paramToken&mem_time=0$etc",
                R.id.Main_HistoryBookList,
                historyAdapter,
                R.id.main_booklist_history
            )
            bookListA(
                root,
                API.BOOK_RECOMMEND_LIST_API_JOA,
                "$paramToken&book_code=",
                R.id.Main_HobbyBookList,
                hobbyAdapter,
                R.id.main_booklist_hobby
            )
        }
        bookListA(
            root,
            API.HOME_LIST_JOA,
            "$paramToken&page=1&section_mode=recommend_book$etc",
            R.id.Main_MDNovelList,
            mdNovelAdapter,
            R.id.main_booklist_mdnovel
        )
        BookList.bookListAWebToon(
            root,
            API.HOME_WEBTOON_LIST_JOA,
            userToken,
            R.id.Main_MDWebtoonList,
            mdWebtoonAdapter,
            queue,
            R.id.main_booklist_mdwebtoon
        )
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
        val userPickedAdapter = MainBookListAdapterC(items)
        val notyAdapter = MainBookListAdapterC(items)
        val recommendAdapter = MainBookListAdapterC(items)
        bookListC(
            root,
            API.HOME_LIST_JOA,
            "$paramToken&section_mode=contest_free_award$etc$showType",
            R.id.Main_UserPickedList,
            userPickedAdapter,
            R.id.main_booklist_userpicked
        )
        bookListC(
            root,
            API.HOME_LIST_JOA,
            "$paramToken&section_mode=noty$etc$showType",
            R.id.Main_NotyList,
            notyAdapter,
            R.id.main_booklist_noty
        )
        bookListC(
            root,
            API.HOME_LIST_JOA,
            paramToken + API.SECTION_MODE + etc + showType,
            R.id.Main_RecommendedList,
            recommendAdapter,
            R.id.main_booklist_recommeded
        )
        val noblessTodayBestAdapter = MainBookListAdapterD(items)
        val premiumToadyBestAdapter = MainBookListAdapterD(items)
        val couponToadyBestAdapter = MainBookListAdapterD(items)
        bookListD(
            root,
            API.HOME_LIST_JOA,
            "$paramToken&section_mode=todaybest&store=nobless&orderby=cnt_best$etc$showType",
            R.id.Main_NoblessTodayBestList,
            noblessTodayBestAdapter,
            R.id.main_nobelsstodaybest
        )
        bookListD(
            root,
            API.HOME_LIST_JOA,
            "$paramToken&section_mode=todaybest&store=premium&orderby=cnt_best$etc$showType",
            R.id.Main_PremiumTodayBestList,
            premiumToadyBestAdapter,
            R.id.main_premiumtodaybest
        )
        bookListD(
            root,
            API.HOME_LIST_JOA,
            "$paramToken&section_mode=support_coupon&orderby=cnt_best$etc$showType",
            R.id.Main_CouponTodayBestList,
            couponToadyBestAdapter,
            R.id.main_coupontodaybest
        )

        goToHistory!!.setOnClickListener { v: View? ->
            gotoMore(
                1,
                R.id.action_Fragment_Main_to_Fragment_Fav,
                this@FragmentMainTabFirst
            )
        }
        goToFes!!.setOnClickListener { v: View? ->
            gotoMore(
                1,
                R.id.action_Fragment_Main_to_Fragment_New,
                this@FragmentMainTabFirst
            )
        }
        goToPromised!!.setOnClickListener { v: View? ->
            gotoMore(
                4,
                R.id.action_Fragment_Main_to_Fragment_New,
                this@FragmentMainTabFirst
            )
        }
        goToKidamu!!.setOnClickListener { v: View? ->
            gotoMore(
                2,
                R.id.action_Fragment_Main_to_Fragment_New,
                this@FragmentMainTabFirst
            )
        }
        goToNoty!!.setOnClickListener { v: View? ->
            gotoMore(
                3,
                R.id.action_Fragment_Main_to_Fragment_New,
                this@FragmentMainTabFirst
            )
        }
        bookSnipe!!.setOnClickListener { v: View? ->
            goToBookPageEtc(
                "취향 저격",
                API.BOOK_RECOMMEND_LIST_API_JOA,
                resultEtcUrl
            )
        }
        userPicked!!.setOnClickListener { v: View? ->
            goToBookPageEtc(
                "수상작",
                API.HOME_LIST_JOA,
                resultEtcUrlSection
            )
        }
        bookRecommend!!.setOnClickListener { v: View? ->
            goToBookPageEtc(
                "천만 인증",
                API.HOME_LIST_JOA,
                resultEtcUrlSection
            )
        }
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