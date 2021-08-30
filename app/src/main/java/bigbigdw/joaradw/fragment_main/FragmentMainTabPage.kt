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
    var category = "0"
    var token: String? = null


    override fun onResume() {
        super.onResume()
        setLayout()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_main_tab, container, false)

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
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap.visibility = View.VISIBLE

                            if(books.isEmpty()){
                                wrap.visibility = View.GONE
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
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap.visibility = View.VISIBLE

                            if(books.isEmpty()){
                                wrap.visibility = View.GONE
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