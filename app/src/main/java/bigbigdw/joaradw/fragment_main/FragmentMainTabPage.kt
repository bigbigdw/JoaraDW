package bigbigdw.joaradw.fragment_main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.main.TabViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FragmentMainTabPage : Fragment() {

    private var tabviewmodel: TabViewModel? = null

    private var tab1Title1: TextView? = null
    private var tab1More: TextView? = null
    private var tab1RecyclerView: RecyclerView? = null
    private var Wrap1: LinearLayout? = null

    var Tab2_Title1: TextView? = null
    var Tab2_More: TextView? = null
    var Tab2_RecyclerView: RecyclerView? = null
    private var Wrap2: LinearLayout? = null

    var Tab3_Title1: TextView? = null
    var Tab3_More: TextView? = null
    var Tab3_RecyclerView: RecyclerView? = null
    private var Wrap3: LinearLayout? = null

    var Tab4_Title1: TextView? = null
    var Tab4_More: TextView? = null
    var Tab4_RecyclerView: RecyclerView? = null
    private var Wrap4: LinearLayout? = null

    var Tab5_Title1: TextView? = null
    var Tab5_Title2: TextView? = null
    var Tab5_More: TextView? = null
    var Tab5_RecyclerView: RecyclerView? = null
    private var Wrap5: LinearLayout? = null

    var Tab6_Title1: TextView? = null
    var Tab6_Title2: TextView? = null
    var Tab6_More: TextView? = null
    var Tab6_RecyclerView: RecyclerView? = null
    private var Wrap6: LinearLayout? = null

    var Tab7_Title1: TextView? = null
    var Tab7_Title2: TextView? = null
    var Tab7_More: TextView? = null
    var Tab7_RecyclerView: RecyclerView? = null
    private var Wrap7: LinearLayout? = null

    var Tab8_Title1: TextView? = null
    var Tab8_Title2: TextView? = null
    var Tab8_More: TextView? = null
    var Tab8_RecyclerView: RecyclerView? = null
    private var Wrap8: LinearLayout? = null

    var Tab9_Title1: TextView? = null
    var Tab9_Title2: TextView? = null
    var Tab9_More: TextView? = null
    var Tab9_RecyclerView: RecyclerView? = null
    private var Wrap9: LinearLayout? = null

    var Tab10_Title1: TextView? = null
    var Tab10_Title2: TextView? = null
    var Tab10_More: TextView? = null
    var Tab10_RecyclerView: RecyclerView? = null
    private var Wrap10: LinearLayout? = null

    var Tab11_Title1: TextView? = null
    var Tab11_Title2: TextView? = null
    var Tab11_More: TextView? = null
    var Tab11_RecyclerView: RecyclerView? = null
    private var Wrap11: LinearLayout? = null

    var Tab12_Title1: TextView? = null
    var Tab12_Title2: TextView? = null
    var Tab12_More: TextView? = null
    var Tab12_RecyclerView: RecyclerView? = null
    private var Wrap12: LinearLayout? = null

    private var adapterBest: AdapterBookListBest? = null
    private val itemsBest1 = ArrayList<BookListDataBest?>()
    private val itemsBest2 = ArrayList<BookListDataBest?>()
    private val itemsBest3 = ArrayList<BookListDataBest?>()
    private val itemsBest4 = ArrayList<BookListDataBest?>()

    var token = ""
    var type = 0
    var category = "1"

    var loadingLayout: LinearLayout? = null
    lateinit var root : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_main_tab, container, false)

        tab1Title1 = root.findViewById(R.id.Tab1_Title1)
        tab1More = root.findViewById(R.id.Tab1_More)
        tab1RecyclerView = root.findViewById(R.id.Tab1_RecyclerView)
        Wrap1 = root.findViewById(R.id.Wrap1)

        Tab2_Title1 = root.findViewById(R.id.Tab2_Title1)
        Tab2_More = root.findViewById(R.id.Tab2_More)
        Tab2_RecyclerView = root.findViewById(R.id.Tab2_RecyclerView)
        Wrap2 = root.findViewById(R.id.Wrap2)

        Tab3_Title1 = root.findViewById(R.id.Tab3_Title1)
        Tab3_More = root.findViewById(R.id.Tab3_More)
        Tab3_RecyclerView = root.findViewById(R.id.Tab3_RecyclerView)
        Wrap3 = root.findViewById(R.id.Wrap3)

        Tab4_Title1 = root.findViewById(R.id.Tab4_Title1)
        Tab4_More = root.findViewById(R.id.Tab4_More)
        Tab4_RecyclerView = root.findViewById(R.id.Tab4_RecyclerView)
        Wrap4 = root.findViewById(R.id.Wrap4)

        Tab5_Title1 = root.findViewById(R.id.Tab5_Title1)
        Tab5_Title2 = root.findViewById(R.id.Tab5_Title2)
        Tab5_More = root.findViewById(R.id.Tab5_More)
        Tab5_RecyclerView = root.findViewById(R.id.Tab5_RecyclerView)
        Wrap5 = root.findViewById(R.id.Wrap5)

        Tab6_Title1 = root.findViewById(R.id.Tab6_Title1)
        Tab6_Title2 = root.findViewById(R.id.Tab6_Title2)
        Tab6_More = root.findViewById(R.id.Tab6_More)
        Tab6_RecyclerView = root.findViewById(R.id.Tab6_RecyclerView)
        Wrap6 = root.findViewById(R.id.Wrap6)

        Tab7_Title1 = root.findViewById(R.id.Tab7_Title1)
        Tab7_Title2 = root.findViewById(R.id.Tab7_Title2)
        Tab7_More = root.findViewById(R.id.Tab7_More)
        Tab7_RecyclerView = root.findViewById(R.id.Tab7_RecyclerView)
        Wrap7 = root.findViewById(R.id.Wrap7)

        Tab8_Title1 = root.findViewById(R.id.Tab8_Title1)
        Tab8_Title2 = root.findViewById(R.id.Tab8_Title2)
        Tab8_More = root.findViewById(R.id.Tab8_More)
        Tab8_RecyclerView = root.findViewById(R.id.Tab8_RecyclerView)
        Wrap8 = root.findViewById(R.id.Wrap8)

        Tab9_Title1 = root.findViewById(R.id.Tab9_Title1)
        Tab9_Title2 = root.findViewById(R.id.Tab9_Title2)
        Tab9_More = root.findViewById(R.id.Tab9_More)
        Tab9_RecyclerView = root.findViewById(R.id.Tab9_RecyclerView)
        Wrap9 = root.findViewById(R.id.Wrap9)

        Tab10_Title1 = root.findViewById(R.id.Tab10_Title1)
        Tab10_Title2 = root.findViewById(R.id.Tab10_Title2)
        Tab10_More = root.findViewById(R.id.Tab10_More)
        Tab10_RecyclerView = root.findViewById(R.id.Tab10_RecyclerView)
        Wrap10 = root.findViewById(R.id.Wrap10)

        Tab11_Title1 = root.findViewById(R.id.Tab11_Title1)
        Tab11_Title2 = root.findViewById(R.id.Tab11_Title2)
        Tab11_More = root.findViewById(R.id.Tab11_More)
        Tab11_RecyclerView = root.findViewById(R.id.Tab11_RecyclerView)
        Wrap11 = root.findViewById(R.id.Wrap11)

        Tab12_Title1 = root.findViewById(R.id.Tab12_Title1)
        Tab12_Title2 = root.findViewById(R.id.Tab12_Title2)
        Tab12_More = root.findViewById(R.id.Tab12_More)
        Tab12_RecyclerView = root.findViewById(R.id.Tab12_RecyclerView)
        Wrap12 = root.findViewById(R.id.Wrap12)

        loadingLayout = root.findViewById(R.id.LoadingLayout)

        tabviewmodel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            when (tabNum) {
                "TAB2" -> {
                    category = "1"
                    tab1Title1!!.text = "판타지"
                    setLayout()
                }
                "TAB3" -> {
                    category = "2"
                    tab1Title1!!.text = "무협"
                    setLayout()
                }
                "TAB4" -> {
                    category = "3"
                    tab1Title1!!.text = "퓨전"
                    setLayout()
                }
                "TAB5" -> {
                    category = "4"
                    tab1Title1!!.text = "게임"
                    setLayout()
                }
                "TAB6" -> {
                    category = "5"
                    tab1Title1!!.text = "로맨스"
                    setLayout()
                }
                "TAB7" -> {
                    category = "6"
                    tab1Title1!!.text = "로맨스 판타지"
                    setLayout()
                }
                "TAB8" -> {
                    category = "7"
                    tab1Title1!!.text = "BL"
                    setLayout()
                }
                "TAB9" -> {
                    category = "8"
                    tab1Title1!!.text = "GL"
                    setLayout()
                }
                "TAB10" -> {
                    category = "9"
                    tab1Title1!!.text = "스포츠"
                    setLayout()
                }
                "TAB11" -> {
                    category = "10"
                    tab1Title1!!.text = "패러디"
                    setLayout()
                }
                "TAB12" -> {
                    category = "11"
                    tab1Title1!!.text = "팬픽"
                    setLayout()
                }
                "TAB13" -> {
                    category = "12"
                    tab1Title1!!.text = "라이트노벨"
                    setLayout()
                }
                "TAB14" -> {
                    category = "13"
                    tab1Title1!!.text = "일반작품"
                    setLayout()
                }
                else -> {
                    category = "14"
                    tab1Title1!!.text = "문학작품"
                    setLayout()
                }
            }
        })

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabviewmodel = ViewModelProvider(this).get(TabViewModel::class.java)
        var index = 1
        if (arguments != null) {
            index = requireArguments().getInt(ARG_SECTION_NUMBER)
        }
        tabviewmodel!!.setIndex(index)
    }

    fun setLayout() {

        token = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "").toString()

        getBookBest(
            tab1RecyclerView,
            Wrap1!!,
            itemsBest1,
            "weekly",
            ""
        )

//        getBookBest(
//            Tab2_RecyclerView,
//            Wrap2!!,
//            itemsBest2,
//            "weekly",
//            "premium"
//        )
//
//        getBookBest(
//            Tab3_RecyclerView,
//            Wrap3!!,
//            itemsBest3,
//            "weekly",
//            "nobless"
//        )
//
//        getBookBest(
//            Tab4_RecyclerView,
//            Wrap4!!,
//            itemsBest4,
//            "weekly",
//            "series"
//        )

    }

    private fun getBookBest(
        recyclerView: RecyclerView?,
        wrap: LinearLayout,
        items : ArrayList<BookListDataBest?>?,
        best: String?,
        store : String?
    ) {
        val adapter: AdapterBookListBest?
        adapter = AdapterBookListBest(requireContext(), items)
        items!!.clear()
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        RetrofitBookList.getBookBest(token, best , store, category)!!.enqueue(object :
            Callback<BookListBestResult?> {
            override fun onResponse(
                call: Call<BookListBestResult?>,
                response: Response<BookListBestResult?>
            ) {

                if (response.isSuccessful) {
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

                                if(i < 5){
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
                            wrap!!.visibility = View.GONE
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView!!.adapter = adapter
                    type++
                }
            }

            override fun onFailure(call: Call<BookListBestResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): FragmentMainTabPage {
            val fragment = FragmentMainTabPage()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}