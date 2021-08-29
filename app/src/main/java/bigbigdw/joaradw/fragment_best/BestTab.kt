package bigbigdw.joaradw.fragment_best

import bigbigdw.joaradw.main.TabViewModel
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import android.widget.TextView
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import bigbigdw.joaradw.R
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.etc.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class BestTab : Fragment() {
    private val items1 = ArrayList<BookListDataBest?>()
    private val items2 = ArrayList<BookListDataBest?>()
    private val items3 = ArrayList<BookListDataBest?>()
    private val items4 = ArrayList<BookListDataBest?>()
    private val items5 = ArrayList<BookListDataBest?>()
    private val items6 = ArrayList<BookListDataBest?>()
    private val items7 = ArrayList<BookListDataBest?>()
    private var tabViewModel: TabViewModel? = null
    var best = "weekly"
    var token = ""
    var tabName = ""
    var etcUrl = "&orderby=cnt_best&offset=100&page=1"
    var blankLayout: LinearLayout? = null
    var loadingLayout: LinearLayout? = null
    var footer: LinearLayout? = null
    var contentsLayout: NestedScrollView? = null
    var gotoBest: TextView? = null
    var gotoNobless: TextView? = null
    var gotoPremium: TextView? = null
    var gotoFree: TextView? = null
    var gotoNew: TextView? = null
    var gotoFinish: TextView? = null
    var gotoNoblessClassic: TextView? = null
    var index = 0

    private var allAdapter: AdapterBookListBest? = null
    private var noblessAdapter: AdapterBookListBest? = null
    private var premiumAdapter: AdapterBookListBest? = null
    private var freeAdapter: AdapterBookListBest? = null
    private var seriesAdapter: AdapterBookListBest? = null
    private var finishAdapter: AdapterBookListBest? = null
    private var noblessClassicAdapter: AdapterBookListBest? = null
    lateinit var root : View
    var type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabViewModel = ViewModelProvider(this).get(TabViewModel::class.java)
        index = 1
        if (arguments != null) {
            index = requireArguments().getInt(ARG_SECTION_NUMBER)
        }
        tabViewModel!!.setIndex(index)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_best_tab, container, false)
        blankLayout = root.findViewById(R.id.BlankLayout)
        loadingLayout = root.findViewById(R.id.LoadingLayout)
        footer = root.findViewById(R.id.footer)
        contentsLayout = root.findViewById(R.id.ContentsLayout)
        gotoBest = root.findViewById(R.id.GotoBest)
        gotoNobless = root.findViewById(R.id.GotoNobless)
        gotoPremium = root.findViewById(R.id.GotoPremium)
        gotoFree = root.findViewById(R.id.GotoFree)
        gotoNew = root.findViewById(R.id.GotoNew)
        gotoFinish = root.findViewById(R.id.GotoFinish)
        gotoNoblessClassic = root.findViewById(R.id.GotoNoblessClassic)

        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            blankLayout!!.visibility = View.GONE
            contentsLayout!!.visibility = View.VISIBLE
        }, 500)

        token = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "").toString()

        contentsLayout!!.setOnScrollChangeListener(commentScrollListener)

        allAdapter = AdapterBookListBest(requireContext(),items1)
        noblessAdapter = AdapterBookListBest(requireContext(),items2)
        premiumAdapter = AdapterBookListBest(requireContext(),items3)
        freeAdapter = AdapterBookListBest(requireContext(),items4)
        seriesAdapter = AdapterBookListBest(requireContext(),items5)
        finishAdapter = AdapterBookListBest(requireContext(),items6)
        noblessClassicAdapter = AdapterBookListBest(requireContext(),items7)

        setLayout(root)
        return root
    }

    fun setLayout(root: View) {


        tabViewModel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            when (tabNum) {
                "TAB1" -> {
                    best = "realtime"
                    tabName = "실시간"
                }
                "TAB2" -> {
                    best = "today"
                    tabName = "투데이"
                }
                "TAB3" -> {
                    best = "weekly"
                    tabName = "주간"
                }
                else -> {
                    best = "monthly"
                    tabName = "월간"
                }
            }

            val resultEtc = etcUrl + token
            val bestParam = "&best=$best"

            getBookBest(
                root,
                "",
                R.id.Best_Tab_AllList,
                allAdapter!!,
                R.id.Best_Tab_All,
                items1
            )

            gotoBest!!.setOnClickListener { v: View? ->
                goToBookPageEtc(
                    "$tabName 전체 베스트",
                    API.BEST_BOOK_JOA,
                    "$bestParam&store=$resultEtc"
                )
            }

            gotoNobless!!.setOnClickListener { v: View? ->
                goToBookPageEtc(
                    tabName + "노블레스 베스트",
                    API.BEST_BOOK_JOA,
                    "$bestParam&store=nobless$resultEtc"
                )
            }

            gotoPremium!!.setOnClickListener { v: View? ->
                goToBookPageEtc(
                    tabName + "프리미엄 베스트",
                    API.BEST_BOOK_JOA,
                    "$bestParam&store=premium$resultEtc"
                )
            }

            gotoFree!!.setOnClickListener {
                goToBookPageEtc(
                    tabName + "무료 베스트",
                    API.BEST_BOOK_JOA,
                    "$bestParam&store=series$resultEtc"
                )
            }

            gotoNew!!.setOnClickListener { v: View? ->
                goToBookPageEtc(
                    tabName + "최신 베스트",
                    API.BEST_BOOK_JOA,
                    "$bestParam&store=lately$resultEtc"
                )
            }

            gotoFinish!!.setOnClickListener { v: View? ->
                goToBookPageEtc(
                    tabName + "완결 베스트",
                    API.BEST_BOOK_JOA,
                    "$bestParam&store=finish$resultEtc"
                )
            }

            gotoNoblessClassic!!.setOnClickListener { v: View? ->
                goToBookPageEtc(
                    tabName + "노블레스 클래식 베스트",
                    API.BEST_BOOK_JOA,
                    "$bestParam&store=nobless_classic$resultEtc"
                )
            }
        })
    }

    private fun getBookBest(
        root: View,
        store: String?,
        recylerView: Int?,
        adapter: AdapterBookListBest,
        integerWrap: Int?,
        items : ArrayList<BookListDataBest?>?
    ) {
        val recyclerView: RecyclerView = root.findViewById(recylerView!!)
        val wrap : LinearLayout = root.findViewById(integerWrap!!)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        RetrofitBookList.getBookBest(token, best ,store)!!.enqueue(object :
            Callback<BookListBestResult?> {
            override fun onResponse(
                call: Call<BookListBestResult?>,
                response: Response<BookListBestResult?>
            ) {

                if (response.isSuccessful) {
                    blankLayout!!.visibility = View.GONE
                    loadingLayout!!.visibility = View.GONE
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap.visibility = View.VISIBLE

                            if(books.isEmpty()){
                                wrap.visibility = View.GONE
                                loadingLayout!!.visibility = View.GONE
                            }

                            for (i in books.indices) {
                                val writerName = books[i].writerName
                                val subject = books[i].subject
                                val bookImg = books[i].bookImg
                                val isAdult = books[i].isAdult
                                val isFinish = books[i].isFinish
                                val isPremium = books[i].isPremium
                                val isNobless = books[i].isNobless
                                val intro = books[i].intro
                                val isFavorite = books[i].isFavorite
                                val bookCode = books[i].bookCode
                                val categoryKoName = books[i].categoryKoName
                                val cntChapter = books[i].cntChapter
                                val cntFavorite = books[i].cntFavorite
                                val cntRecom = books[i].cntRecom
                                val cntPageRead = books[i].cntPageRead

                                if(i < 9){
                                    items!!.add(
                                        BookListDataBest(
                                            writerName,
                                            subject,
                                            bookImg,
                                            isAdult,
                                            isFinish,
                                            isPremium,
                                            isNobless,
                                            intro,
                                            isFavorite,
                                            bookCode,
                                            categoryKoName,
                                            cntChapter,
                                            cntFavorite,
                                            cntRecom,
                                            cntPageRead
                                        )
                                    )
                                }


                            }
                        } else {
//                            loadingLayout!!.visibility = View.VISIBLE
                            wrap!!.visibility = View.GONE
                        }
                    }
                    adapter.notifyDataSetChanged()
                    recyclerView.layoutManager = linearLayoutManager
                    recyclerView.adapter = adapter
                    type++
                }
            }

            override fun onFailure(call: Call<BookListBestResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    private fun goToBookPageEtc(title: String?, apiUrl: String?, etcURL: String?) {
        //TODO: 작업 필요
//        val intent = Intent(requireContext().applicationContext, BookPageEtc::class.java)
//        intent.putExtra("Title", String.format("%s", title))
//        intent.putExtra("API_URL", String.format("%s", apiUrl))
//        intent.putExtra("ETC_URL", String.format("%s", etcURL))
//        startActivity(intent)
    }

    private var commentScrollListener =
        NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {

                loadingLayout!!.visibility = View.VISIBLE

                Log.d("@@@@", type.toString())

                when (type) {
                    1 -> {
                        getBookBest(
                            root,
                            "nobless",
                            R.id.Best_Tab_NoblessList,
                            noblessAdapter!!,
                            R.id.Best_Tab_Nobless,
                            items2
                        )
                    }
                    2 -> {
                        getBookBest(
                            root,
                            "premium",
                            R.id.Best_Tab_PremiumList,
                            premiumAdapter!!,
                            R.id.Best_Tab_Premium,
                            items3
                        )
                    }
                    3 -> {
                        getBookBest(
                            root,
                            "series",
                            R.id.Best_Tab_FreeList,
                            freeAdapter!!,
                            R.id.Best_Tab_Free,
                            items4
                        )
                    }
                    4 -> {
                        getBookBest(
                            root,
                            "lately",
                            R.id.Best_Tab_SeriesList,
                            seriesAdapter!!,
                            R.id.Best_Tab_Series,
                            items5
                        )
                    }
                    5 -> {
                        getBookBest(
                            root,
                            "finish",
                            R.id.Best_Tab_FinishList,
                            finishAdapter!!,
                            R.id.Best_Tab_Finish,
                            items6
                        )
                    }
                    6 -> {
                        getBookBest(
                            root,
                            "nobless_classic",
                            R.id.Best_Tab_NoblessClassicList,
                            noblessClassicAdapter!!,
                            R.id.Best_Tab_NoblessClassic,
                            items7
                        )
                    }
                    7 -> {
                        footer!!.visibility = View.VISIBLE
                        loadingLayout!!.visibility = View.GONE
                    }
                }
            }
        }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): BestTab {
            val fragment = BestTab()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}