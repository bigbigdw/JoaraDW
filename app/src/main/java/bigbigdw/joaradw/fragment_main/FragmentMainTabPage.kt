package bigbigdw.joaradw.fragment_main

import android.content.Intent
import android.os.Bundle
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
import bigbigdw.joaradw.book_detail.BookDetailCover
import bigbigdw.joaradw.main.TabViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FragmentMainTabPage : Fragment() {

    private var tabviewmodel: TabViewModel? = null

    private var tab1RecyclerView: RecyclerView? = null
    private var Wrap1: LinearLayout? = null

    var tab2RecyclerView: RecyclerView? = null
    private var Wrap2: LinearLayout? = null

    var Tab3_RecyclerView: RecyclerView? = null
    private var Wrap3: LinearLayout? = null

    var Tab4_RecyclerView: RecyclerView? = null
    private var Wrap4: LinearLayout? = null

    var Tab5_More: TextView? = null
    var Tab5_RecyclerView: RecyclerView? = null
    private var Wrap5: LinearLayout? = null

    var Tab6_More: TextView? = null
    var Tab6_RecyclerView: RecyclerView? = null
    private var Wrap6: LinearLayout? = null

    var Tab7_More: TextView? = null
    var Tab7_RecyclerView: RecyclerView? = null
    private var Wrap7: LinearLayout? = null

    var Tab8_More: TextView? = null
    var Tab8_RecyclerView: RecyclerView? = null
    private var Wrap8: LinearLayout? = null

    var Tab9_More: TextView? = null
    var Tab9_RecyclerView: RecyclerView? = null
    private var Wrap9: LinearLayout? = null

    var Tab10_More: TextView? = null
    var Tab10_RecyclerView: RecyclerView? = null
    private var Wrap10: LinearLayout? = null

    var Tab11_More: TextView? = null
    var Tab11_RecyclerView: RecyclerView? = null
    private var Wrap11: LinearLayout? = null

    var Tab12_More: TextView? = null
    var Tab12_RecyclerView: RecyclerView? = null
    private var Wrap12: LinearLayout? = null

    private val itemsBest1 = ArrayList<BookListDataBest?>()
    private val bookListItemsA1 = ArrayList<BookListDataABD?>()
    private val bookListItemsA2 = ArrayList<BookListDataABD?>()
    private val bookListItemsA3 = ArrayList<BookListDataABD?>()

    var token = ""
    var pageCount = 0
    var category = "1"

    var loadingLayout: LinearLayout? = null
    var Wrap : NestedScrollView? = null
    lateinit var root : View

    override fun onResume() {
        super.onResume()
        setLayout()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_main_tab, container, false)

        Wrap = root.findViewById(R.id.Wrap)
        Wrap!!.setOnScrollChangeListener(scrollListener)

        tab1RecyclerView = root.findViewById(R.id.Tab1_RecyclerView)
        Wrap1 = root.findViewById(R.id.Wrap1)

        tab2RecyclerView = root.findViewById(R.id.Tab2_RecyclerView)
        Wrap2 = root.findViewById(R.id.Wrap2)

        Tab3_RecyclerView = root.findViewById(R.id.Tab3_RecyclerView)
        Wrap3 = root.findViewById(R.id.Wrap3)

        Tab4_RecyclerView = root.findViewById(R.id.Tab4_RecyclerView)
        Wrap4 = root.findViewById(R.id.Wrap4)

        Tab5_More = root.findViewById(R.id.Tab5_More)
        Tab5_RecyclerView = root.findViewById(R.id.Tab5_RecyclerView)
        Wrap5 = root.findViewById(R.id.Wrap5)

        Tab6_More = root.findViewById(R.id.Tab6_More)
        Tab6_RecyclerView = root.findViewById(R.id.Tab6_RecyclerView)
        Wrap6 = root.findViewById(R.id.Wrap6)

        Tab7_More = root.findViewById(R.id.Tab7_More)
        Tab7_RecyclerView = root.findViewById(R.id.Tab7_RecyclerView)
        Wrap7 = root.findViewById(R.id.Wrap7)

        Tab8_More = root.findViewById(R.id.Tab8_More)
        Tab8_RecyclerView = root.findViewById(R.id.Tab8_RecyclerView)
        Wrap8 = root.findViewById(R.id.Wrap8)

        Tab9_More = root.findViewById(R.id.Tab9_More)
        Tab9_RecyclerView = root.findViewById(R.id.Tab9_RecyclerView)
        Wrap9 = root.findViewById(R.id.Wrap9)

        Tab10_More = root.findViewById(R.id.Tab10_More)
        Tab10_RecyclerView = root.findViewById(R.id.Tab10_RecyclerView)
        Wrap10 = root.findViewById(R.id.Wrap10)

        Tab11_More = root.findViewById(R.id.Tab11_More)
        Tab11_RecyclerView = root.findViewById(R.id.Tab11_RecyclerView)
        Wrap11 = root.findViewById(R.id.Wrap11)

        Tab12_More = root.findViewById(R.id.Tab12_More)
        Tab12_RecyclerView = root.findViewById(R.id.Tab12_RecyclerView)
        Wrap12 = root.findViewById(R.id.Wrap12)

        loadingLayout = root.findViewById(R.id.LoadingLayout)

        tabviewmodel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            when (tabNum) {
                "TAB1" -> {
                    category = "1"
                    setLayout()
                }
                "TAB2" -> {
                    category = "2"
                    setLayout()
                }
                "TAB3" -> {
                    category = "3"
                    setLayout()
                }
                "TAB4" -> {
                    category = "4"
                    setLayout()
                }
                "TAB5" -> {
                    category = "5"
                    setLayout()
                }
                "TAB6" -> {
                    category = "6"
                    setLayout()
                }
                "TAB7" -> {
                    category = "7"
                    setLayout()
                }
                "TAB8" -> {
                    category = "8"
                    setLayout()
                }
                "TAB9" -> {
                    category = "9"
                    setLayout()
                }
                "TAB10" -> {
                    category = "10"
                    setLayout()
                }
                "TAB11" -> {
                    category = "11"
                    setLayout()
                }
                "TAB12" -> {
                    category = "12"
                    setLayout()
                }
                "TAB13" -> {
                    category = "13"
                    setLayout()
                }
                else -> {
                    category = "14"
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

        getBookListBest(
            tab1RecyclerView,
            Wrap1!!,
            itemsBest1,
            "weekly",
            ""
        )
    }

    private fun getBookListBest(
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
                    pageCount++
                }
            }

            override fun onFailure(call: Call<BookListBestResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    private fun getBookListA(
        recyclerView: RecyclerView?,
        wrap: LinearLayout,
        items : ArrayList<BookListDataABD?>?,
        best: String?,
        store : String?
    ) {
        val adapter: AdapterBookListA?
        adapter = AdapterBookListA(requireContext(), items)
        items!!.clear()
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        RetrofitBookList.getBookBestA(token, best , store, category)!!.enqueue(object : Callback<BookListResult?> {
            override fun onResponse(
                call: Call<BookListResult?>,
                response: Response<BookListResult?>
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

                                val bookCode = books[i].bookCode
                                val bookImg = books[i].bookImg
                                val historySortno = books[i].historySortno
                                val subject = books[i].subject
                                val writerName = books[i].writerName
                                val isAdult = books[i].is_adult

                                items!!.add(
                                    BookListDataABD(
                                        writerName,
                                        subject,
                                        bookImg,
                                        bookCode,
                                        historySortno,
                                        isAdult,
                                        ""
                                    )
                                )
                            }
                        } else {
                            wrap!!.visibility = View.GONE
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView!!.adapter = adapter
                    pageCount++
                }
            }

            override fun onFailure(call: Call<BookListResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

        adapter!!.setOnItemClickListener(object : AdapterBookListA.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                val item: BookListDataABD? = adapter.getItem(position)
                val intent = Intent(
                    requireContext().applicationContext,
                    BookDetailCover::class.java
                )
                intent.putExtra("BookCode", String.format("%s", item!!.bookCode))
                intent.putExtra("token", String.format("%s", token))
                requireContext().startActivity(intent)
            }
        })
    }

    private var scrollListener =
        NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                loadingLayout!!.visibility = View.VISIBLE

                when (pageCount) {
                    1 -> {
                        getBookListA(
                            tab2RecyclerView,
                            Wrap2!!,
                            bookListItemsA1,
                            "weekly",
                            "premium"
                        )

                    }
                    2 -> {
                        getBookListA(
                            Tab3_RecyclerView,
                            Wrap3!!,
                            bookListItemsA2,
                            "weekly",
                            "nobless"
                        )
                    }
                    3 -> {
                        getBookListA(
                            Tab4_RecyclerView,
                            Wrap4!!,
                            bookListItemsA3,
                            "weekly",
                            "series"
                        )
                    }
                    else -> {
                        loadingLayout!!.visibility = View.GONE
                    }
                }
            }
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