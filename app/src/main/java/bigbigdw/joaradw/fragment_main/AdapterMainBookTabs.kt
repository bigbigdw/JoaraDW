package bigbigdw.joaradw.fragment_main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.book_detail.BookDetailCover
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ViewListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import org.json.JSONException

import org.json.JSONObject




class AdapterMainBookTabs(private val mContext: Context, items: List<MainBookData?>?, category : String?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<MainBookData?>?
    private val bookListItemsBest1 = ArrayList<BookListDataBest?>()
    private val bookListItemsA1 = ArrayList<BookListDataABD?>()
    private val bookListItemsA2 = ArrayList<BookListDataABD?>()
    private val bookListItemsA3 = ArrayList<BookListDataABD?>()
    private val bookListItemsA4 = ArrayList<BookListDataABD?>()
    private val bookEventListItem = ArrayList<BookEventListData?>()
    private val bookListItemsB1 = ArrayList<BookListDataABD?>()
    private val bookListItemsB2 = ArrayList<BookListDataABD?>()
    private val bookListItemsC1 = ArrayList<BookListDataC?>()
    private val bookListItemsC2 = ArrayList<BookListDataC?>()
    private val bookListItemsC3 = ArrayList<BookListDataC?>()
    private val bookListItemsD1 = ArrayList<BookListDataABD?>()
    private val bookListItemsD2 = ArrayList<BookListDataABD?>()
    private val bookListItemsD3 = ArrayList<BookListDataABD?>()
    private val bookListItemsD4 = ArrayList<BookListDataABD?>()
    private val bookListItemsD5 = ArrayList<BookListDataABD?>()
    private val bookListItemsD6 = ArrayList<BookListDataABD?>()
    private val bookListItemsD7 = ArrayList<BookListDataABD?>()
    private val bookListItemsD8 = ArrayList<BookListDataABD?>()
    private val bookListItemsD9 = ArrayList<BookListDataABD?>()
    private val bookListItemsD10 = ArrayList<BookListDataABD?>()
    private val bookListItemsD11 = ArrayList<BookListDataABD?>()
    private val bookListItemsD12 = ArrayList<BookListDataABD?>()
    private val bookListItemsD13 = ArrayList<BookListDataABD?>()
    private val bookListItemsD14 = ArrayList<BookListDataABD?>()
    private val bookListItemsD15 = ArrayList<BookListDataABD?>()
    private val bookListItemsD16 = ArrayList<BookListDataABD?>()
    private val bookListItemsD17 = ArrayList<BookListDataABD?>()
    private val bookListItemsD18 = ArrayList<BookListDataABD?>()

    val token = mContext.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
        .getString("TOKEN", "")
    var categoryValue = category

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fragment_main_book, parent, false)
        return MainBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainBookViewHolder) {

            holder.sectionCategory = listData!![position]!!.sectionCategory
            holder.sectionSubType = listData!![position]!!.sectionSubType
            holder.sectionType = listData!![position]!!.sectionType

            if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("all")) {

                holder.wrap.visibility = View.VISIBLE

                holder.titleFirst.text = "베스트"
                holder.titleSecond.text = " 주간 전체"
                getBookListBestVertical(
                    token,
                    holder.wrap,
                    holder.recyclerView
                )

            }
            else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("nobless")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "베스트"
                holder.titleSecond.text = " 주간 노블레스"
                getBookListTypeA(
                    token,
                    "bestNobless",
                    holder.recyclerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("premium")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "베스트"
                holder.titleSecond.text = " 주간 프리미엄"
                getBookListTypeA(
                    token,
                    "bestPremium",
                    holder.recyclerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("series")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "베스트"
                holder.titleSecond.text = " 주간 무료"
                getBookListTypeA(
                    token,
                    "bestSeries",
                    holder.recyclerView,
                    holder.wrap
                )
            }
        }
    }

    private fun getBookListBestVertical(
        token: String?,
        wrap : LinearLayout?,
        recyclerView: RecyclerView?,
    ) {

        RetrofitBookList.getBookBest(token, "weekly" , "", categoryValue)!!.enqueue(object :
            Callback<BookListBestResult?> {
            override fun onResponse(
                call: Call<BookListBestResult?>,
                response: Response<BookListBestResult?>
            ) {

                val linearLayoutManager =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                val adapter: AdapterBookListBest?
                adapter = AdapterBookListBest(mContext, bookListItemsBest1)

                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap!!.visibility = View.VISIBLE

                            if(books.isEmpty()){
                                wrap!!.visibility = View.GONE
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
                                    bookListItemsBest1!!.add(
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
                        recyclerView!!.layoutManager = linearLayoutManager
                        recyclerView.adapter = adapter
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<BookListBestResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

    }



    private fun getBookListTypeA(
        token: String?,
        type: String?,
        recyclerView: RecyclerView?,
        wrap : LinearLayout?
    ) {
        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

        val call: Call<BookListResult?>? =
            when {
                type.equals("bestNobless") -> {
                    RetrofitBookList.getBookBestA(token, "weekly" , "nobless", categoryValue)
                }
                type.equals("bestPremium") -> {
                    RetrofitBookList.getBookBestA(token, "weekly" , "premium", categoryValue)
                }
                else -> {
                    RetrofitBookList.getBookBestA(token, "weekly" , "series", categoryValue)
                }
            }

        val bookListItemsABD: ArrayList<BookListDataABD?> =
            when {
                type.equals("bestNobless") -> {
                    bookListItemsA1
                }
                type.equals("bestPremium") -> {
                    bookListItemsA2
                }
                else -> bookListItemsA3
            }

        val adapter: AdapterBookListA?
        adapter = AdapterBookListA(mContext, bookListItemsABD)

        call!!.enqueue(object : Callback<BookListResult?> {
            override fun onResponse(
                call: Call<BookListResult?>,
                response: Response<BookListResult?>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap!!.visibility = View.VISIBLE

                            if(books.isEmpty()){
                                wrap!!.visibility = View.GONE
                            }

                            for (i in books.indices) {

                                val bookCode = books[i].bookCode
                                val bookImg = books[i].bookImg
                                val historySortno = books[i].historySortno
                                val subject = books[i].subject
                                val writerName = books[i].writerName
                                val isAdult = books[i].is_adult

                                bookListItemsABD!!.add(
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
                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView.adapter = adapter
                    adapter!!.notifyDataSetChanged()
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
                    mContext.applicationContext,
                    BookDetailCover::class.java
                )
                intent.putExtra("BookCode", String.format("%s", item!!.bookCode))
                intent.putExtra("token", String.format("%s", token))
                mContext.startActivity(intent)
            }
        })
    }


    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var titleFirst: TextView
        var titleSecond: TextView
        var gotoMore: TextView
        var recyclerView: RecyclerView
        var wrap: LinearLayout

        var sectionApiUrl: String? = null
        var sectionCategory: String? = null
        var sectionSubType: String? = null
        var sectionType: String? = null

        init {
            titleFirst = itemView.findViewById(R.id.Title_First)
            titleSecond = itemView.findViewById(R.id.Title_Second)
            gotoMore = itemView.findViewById(R.id.GotoMore)
            recyclerView = itemView.findViewById(R.id.RecyclerView)
            wrap = itemView.findViewById(R.id.Wrap)
        }
    }

    fun setItems(items: List<MainBookData?>?) {
        listData = items as ArrayList<MainBookData?>?
    }

    fun getItem(position: Int): MainBookData? {
        return listData!![position]
    }

    init {
        listData = items as ArrayList<MainBookData?>?
    }
}