package bigbigdw.joaradw.fragment_main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.base.BookBaseFragment
import bigbigdw.joaradw.main.RetrofitMain
import bigbigdw.joaradw.main.TabViewModel
import com.bumptech.glide.Glide
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FragmentMainTabFirst : BookBaseFragment() {

    private var tabviewmodel: TabViewModel? = null

    var mainBanner: CarouselView? = null
    var mainBannerMid: CarouselView? = null
    var mainBannerURLs: MutableList<String> = ArrayList()
    var mainBannerMidURLs: MutableList<String> = ArrayList()

    private var mainBookAdapterFirst: AdapterMainBookData? = null
    var linearLayoutManagerFirst: LinearLayoutManager? = null
    private val mainBookItemsFirst = ArrayList<MainBookData?>()

    private var mainBookAdapterSecond: AdapterMainBookData? = null
    var linearLayoutManagerSecond: LinearLayoutManager? = null
    private val mainBookItemsSecond = ArrayList<MainBookData?>()

    var RecyclerViewFirst: RecyclerView? = null
    var RecyclerViewSecond: RecyclerView? = null

    var token: String? = null
    var userStatus: String? = null
    var paramToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabviewmodel = ViewModelProvider(this).get(TabViewModel::class.java)
        var index = 1
        if (arguments != null) {
            index = requireArguments().getInt(ARG_SECTION_NUMBER)
        }
        tabviewmodel!!.setIndex(index)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_tab_first, container, false)
        RecyclerViewSecond = root.findViewById(R.id.MainBookListSecond)
        RecyclerViewFirst = root.findViewById(R.id.MainBookListFirst)

        mainBanner = root.findViewById(R.id.Carousel_MainBanner)
        mainBannerMid = root.findViewById(R.id.Carousel_MainBanner_Mid)

        token = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")
        userStatus = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("STATUS", "")
        paramToken = "&token=$token"

        setLayout()
        return root
    }

    fun setLayout() {

        mainBookItemsFirst.clear()
        mainBookItemsSecond.clear()

        //메인 북 데이터
        mainBookAdapterFirst = AdapterMainBookData(requireContext(),mainBookItemsFirst)
        linearLayoutManagerFirst = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        getMainBookData(mainBookAdapterFirst, linearLayoutManagerFirst,RecyclerViewFirst,"FIRST")

        mainBookAdapterSecond = AdapterMainBookData(requireContext(),mainBookItemsSecond)
        linearLayoutManagerSecond = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        getMainBookData(mainBookAdapterSecond, linearLayoutManagerSecond,RecyclerViewSecond,"SECOND")

        //배너
        getMainBanner("app_home_top_banner",mainBanner!!,mainBannerURLs)
        getMainBanner("app_main2016_event",mainBannerMid!!,mainBannerMidURLs)

        mainBanner!!.setImageListener(imageListener)
        mainBannerMid!!.setImageListener(imageListenerMid)

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
    private fun getMainBookData(adapter: AdapterMainBookData?, linearLayoutManager : LinearLayoutManager?, recyclerView: RecyclerView?, type:String?) {
        RetrofitMain.getMainBookData(token)!!.enqueue(object : Callback<MainBookResult?> {
            override fun onResponse(
                call: Call<MainBookResult?>,
                response: Response<MainBookResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val mainInfo = it.mainInfo

                        if (mainInfo != null) {
                            for (i in mainInfo.indices) {

                                val sectionApiUrl = mainInfo[i].sectionApiUrl
                                val sectionCategory = mainInfo[i].sectionCategory
                                val sectionSubType = mainInfo[i].sectionSubType
                                val sectionType = mainInfo[i].sectionType

                                if(type.equals("FIRST")){
                                    if(i in 2..6 && !sectionApiUrl.equals("")){
                                        mainBookItemsFirst.add(
                                            MainBookData(
                                                sectionCategory,
                                                sectionSubType,
                                                sectionType
                                            )
                                        )
                                    }
                                } else {
                                    //뒤에꺼만 들고옴 + 맨 마지막 빈거 제거
                                    if(i > 7 && !sectionApiUrl.equals("")){
                                        mainBookItemsSecond.add(
                                            MainBookData(
                                                sectionCategory,
                                                sectionSubType,
                                                sectionType
                                            )
                                        )
                                    }
                                }

                                recyclerView!!.layoutManager = linearLayoutManager
                                recyclerView!!.adapter = adapter
                            }
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MainBookResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    private var recyclerViewScroll: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(!recyclerView.canScrollVertically(1)) {
                mainBookAdapterSecond = AdapterMainBookData(requireContext(),mainBookItemsSecond)
                linearLayoutManagerSecond = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                getMainBookData(mainBookAdapterSecond, linearLayoutManagerSecond,RecyclerViewSecond,"SECOND")
            }

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
        mainBannerMid!!.removeAllViews()
        mainBannerMidURLs = ArrayList()
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): FragmentMainTabFirst {
            val fragment = FragmentMainTabFirst()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}