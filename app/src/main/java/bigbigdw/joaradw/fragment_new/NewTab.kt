package bigbigdw.joaradw.fragment_new

import android.content.Intent
import bigbigdw.joaradw.base.BookBaseFragment
import bigbigdw.joaradw.main.OLD_MainBookListAdapterC
import bigbigdw.joaradw.main.OLD_MainBookListData
import bigbigdw.joaradw.main.TabViewModel
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.ViewGroup
import bigbigdw.joaradw.R
import bigbigdw.joaradw.JOARADW
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.book_detail.BookDetailCover
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class NewTab : Fragment() {
    private var adapter: AdapterBookListC? = null
    private val items = ArrayList<BookListDataC?>()
    private var tabviewmodel: TabViewModel? = null

    var wrap: LinearLayout? = null
    var cover: LinearLayout? = null
    var blank: LinearLayout? = null
    var token: String? = null
    var etc = ""
    var store = ""
    var linearLayoutManager: LinearLayoutManager? = null
    var recyclerView: RecyclerView? = null
    var page = 1

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
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_new_tab, container, false)
        val titleValues =
            requireContext()!!.getSharedPreferences("MAIN_MENU", AppCompatActivity.MODE_PRIVATE)
                .getString("NEW_TITLE", "")!!.replace("[", "").replace("]", "")
        val titleList: List<String> = titleValues!!.split(',').toList()

        token = requireContext()!!.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

        recyclerView = root.findViewById(R.id.Main_NewBookList)
        wrap = root.findViewById(R.id.TabWrap)
        cover = root.findViewById(R.id.LoadingLayout)
        blank = root.findViewById(R.id.BlankLayout)

        adapter = AdapterBookListC(requireContext(), items)
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        tabviewmodel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            when (titleList[tabNum!!.replace("TAB", "").replace(" ", "").toInt()]) {
                "전체" -> {
                    store = ""
                    newBookList(page)
                }
                " 77FES" -> {
                    store = "festival"
                    newBookList(page)
                }
                " BORN" -> {
                    store = "joaraborn"
                    newBookList(page)
                }
                " 노블성실" -> {
                    store = "promise"
                    newBookList(page)
                }
                " 노블레스" -> {
                    store = "nobless"
                    newBookList(page)
                }
                " 노블X프리" -> {
                    store = "noblepre"
                    newBookList(page)
                }
                " 프리미엄" -> {
                    store = "premium"
                    newBookList(page)
                }
                " 무료" -> {
                    store = "series"
                    newBookList(page)
                }
                " 완결" -> {
                    store = "finish"
                    newBookList(page)
                }
                else -> {
                    store = "gidamu_event"
                    newBookList(page)
                }
            }
        })

        recyclerView!!.addOnScrollListener(recyclerViewScroll)

        return root
    }

    private fun newBookList(page : Int?) {
        RetrofitBookList.getNewBook(token, store, page)!!.enqueue(object : Callback<BookListResultC?> {
            override fun onResponse(
                call: Call<BookListResultC?>,
                response: Response<BookListResultC?>
            ) {

                if (response.isSuccessful) {
                    cover!!.visibility = View.GONE
                    blank!!.visibility = View.GONE
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap!!.visibility = View.VISIBLE
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

                                items.add(
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
                        } else {
                            blank!!.visibility = View.VISIBLE
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                    if(page == 1){
                        recyclerView!!.layoutManager = linearLayoutManager
                        recyclerView!!.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<BookListResultC?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

    }

    private var recyclerViewScroll: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(!recyclerView.canScrollVertically(1)) {
                cover!!.visibility = View.VISIBLE
                page++
                newBookList(page)
            }

        }
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): NewTab {
            val fragment = NewTab()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}