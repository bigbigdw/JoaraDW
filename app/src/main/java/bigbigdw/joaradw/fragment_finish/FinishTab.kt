package bigbigdw.joaradw.fragment_finish

import bigbigdw.joaradw.main.TabViewModel
import android.widget.LinearLayout
import android.widget.TextView
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import bigbigdw.joaradw.R
import bigbigdw.joaradw.etc.API
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.book.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FinishTab : Fragment() {
    private val items1 = ArrayList<BookListDataC?>()
    private val items2 = ArrayList<BookListDataC?>()
    private val items3 = ArrayList<BookListDataC?>()
    private var tabviewmodel: TabViewModel? = null

    var wrap: LinearLayout? = null
    var contentsLayout: NestedScrollView? = null
    var blankLayout: LinearLayout? = null
    var loadingLayout: LinearLayout? = null
    var footer: LinearLayout? = null
    var token: String? = null
    var etc = ""
    var store = ""
    var linearLayoutManager: LinearLayoutManager? = null
    var recyclerView: RecyclerView? = null
    var page = 1
    var finishType = ""

    private var newAdapter: AdapterBookListC? = null
    private var noblessAdapter: AdapterBookListC? = null
    private var premiumAdapter: AdapterBookListC? = null

    var goToAll: TextView? = null
    var gotoNobless: TextView? = null
    var gotoPremium: TextView? = null
    lateinit var root : View
    var type = 0

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
        root = inflater.inflate(R.layout.fragment_finish_tab, container, false)

        token = requireContext()!!.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

        recyclerView = root.findViewById(R.id.Main_NewBookList)
        wrap = root.findViewById(R.id.TabWrap)
        blankLayout = root.findViewById(R.id.BlankLayout)
        loadingLayout = root.findViewById(R.id.LoadingLayout)
        contentsLayout = root.findViewById(R.id.ContentsLayout)
        footer = root.findViewById(R.id.footer)
        goToAll = root.findViewById(R.id.GoToAll)
        gotoNobless = root.findViewById(R.id.GotoNobless)
        gotoPremium = root.findViewById(R.id.GotoPremium)

        contentsLayout!!.setOnScrollChangeListener(scrollListener)

        newAdapter = AdapterBookListC(requireContext(), items1)
        noblessAdapter = AdapterBookListC(requireContext(), items2)
        premiumAdapter = AdapterBookListC(requireContext(), items3)


        setLayout(root)
        return root
    }

    fun setLayout(root: View?) {

        tabviewmodel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            finishType = when (tabNum) {
                "TAB1" -> "redate"
                "TAB2" -> "cnt_page_read"
                "TAB3" -> "cnt_favorite"
                else -> "cnt_recom"
            }

            getBookFinish(
                root,
                "finish",
                R.id.Finish_Tab_NewList,
                newAdapter!!,
                R.id.Finish_Tab_New,
                items1
            )

        })

        goToAll!!.setOnClickListener {
            goToBookPageEtc(
                "최신작 전체 완결",
                API.BOOK_LIST_JOA,
                ""
            )
        }
        gotoNobless!!.setOnClickListener {
            goToBookPageEtc(
                "최신작 노블레스 완결",
                API.BOOK_LIST_JOA,
                ""
            )
        }
        gotoPremium!!.setOnClickListener {
            goToBookPageEtc(
                "최신작 프리미엄 완결",
                API.BOOK_LIST_JOA,
                ""
            )
        }
    }

    private fun getBookFinish(
        root: View?,
        store: String?,
        recylerView: Int?,
        adapter: AdapterBookListC,
        integerWrap: Int?,
        items : ArrayList<BookListDataC?>?
    ) {
        val recyclerView: RecyclerView = root!!.findViewById(recylerView!!)
        val wrap : LinearLayout = root!!.findViewById(integerWrap!!)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        RetrofitBookList.getBookFinish(token ,store, finishType,"")!!.enqueue(object :
            Callback<BookListResultC?> {
            override fun onResponse(
                call: Call<BookListResultC?>,
                response: Response<BookListResultC?>
            ) {

                if (response.isSuccessful) {
                    blankLayout!!.visibility = View.GONE
                    loadingLayout!!.visibility = View.GONE

                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap!!.visibility = View.VISIBLE

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

                                if(i < 21){
                                    items!!.add(
                                        BookListDataC(
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
                                        )
                                    )
                                }
                            }
                        } else {
                            wrap!!.visibility = View.GONE
                        }
                    }
                    adapter.notifyDataSetChanged()
                    recyclerView.layoutManager = linearLayoutManager
                    recyclerView.adapter = adapter
                    type++
                }
            }

            override fun onFailure(call: Call<BookListResultC?>, t: Throwable) {
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

    private var scrollListener =
        NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                loadingLayout!!.visibility = View.VISIBLE

                when (type) {
                    1 -> {
                        getBookFinish(
                            root,
                            "nobless_finish",
                            R.id.Finish_Tab_NoblessList,
                            noblessAdapter!!,
                            R.id.Finish_Tab_Nobless,
                            items2
                        )

                    }
                    2 -> {
                        getBookFinish(
                            root,
                            "premium_finish",
                            R.id.Finish_Tab_PremiumList,
                            premiumAdapter!!,
                            R.id.Finish_Tab_Premium,
                            items3
                        )
                    }
                    3 -> {
                        footer!!.visibility = View.VISIBLE
                        loadingLayout!!.visibility = View.GONE
                    }
                }
            }
        }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): FinishTab {
            val fragment = FinishTab()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}