package bigbigdw.joaradw.fragment_finish

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.book_detail.BookDetailCover
import bigbigdw.joaradw.fragment_main.MainBookData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class AdapterBookTabFinish(
    private val mContext: Context,
    items: List<MainBookData?>?,
    finishValue: String?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<MainBookData?>?
    private val bookListItemsC1 = ArrayList<BookListDataC?>()
    private val bookListItemsC2 = ArrayList<BookListDataC?>()
    private val bookListItemsC3 = ArrayList<BookListDataC?>()
    val finish = finishValue

    val token = mContext.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
        .getString("TOKEN", "")

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fragment_main, parent, false)
        return MainBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainBookViewHolder) {

            holder.sectionCategory = listData!![position]!!.sectionCategory
            holder.sectionSubType = listData!![position]!!.sectionSubType
            holder.sectionType = listData!![position]!!.sectionType

            if (listData!![position]!!.sectionType.equals("finish")
                && listData!![position]!!.sectionSubType.equals("All")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "완결"
                holder.titleSecond.text = " 전체"
                getBookListTypeC(
                    token,
                    "finishAll",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionType.equals("finish")
                && listData!![position]!!.sectionSubType.equals("Nobless")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "완결"
                holder.titleSecond.text = " 노블레스"
                getBookListTypeC(
                    token,
                    "finishNobless",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionType.equals("finish")
                && listData!![position]!!.sectionSubType.equals("Premium")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "완결"
                holder.titleSecond.text = " 프리미엄"
                getBookListTypeC(
                    token,
                    "finishPremium",
                    holder.recylerView,
                    holder.wrap
                )
            }
        }
    }

    private fun getBookListTypeC(
        token: String?,
        type: String?,
        recyclerView: RecyclerView?,
        wrap: LinearLayout?
    ) {

        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        val call: Call<BookListResultC?>? =
            when {
                type.equals("finishAll") -> {
                    RetrofitBookList.getBookFinish(token ,"finish", "redate","0")
                }
                type.equals("finishNobless") -> {
                    RetrofitBookList.getBookFinish(token ,"nobless_finish", "redate","0")
                }
                else -> {
                    RetrofitBookList.getBookFinish(token ,"premium_finish", "redate","0")
                }
            }

        val bookListItemsC: ArrayList<BookListDataC?> =
            when {
                type.equals("finishAll") -> {
                    bookListItemsC1
                }
                type.equals("finishNobless") -> {
                    bookListItemsC2
                }
                else -> bookListItemsC3
            }

        val adapter: AdapterBookListC?
        adapter = AdapterBookListC(mContext, bookListItemsC)

        call!!.enqueue(object : Callback<BookListResultC?> {
            override fun onResponse(
                call: Call<BookListResultC?>,
                response: Response<BookListResultC?>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val books = it.books

                        if(books!!.isEmpty()){
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

                            if(i < 11){
                                bookListItemsC.add(
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
                    }

                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BookListResultC?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

        adapter!!.setOnItemClickListener(object : AdapterBookListC.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                val item: BookListDataC? = adapter!!.getItem(position)
                if (value == "FAV") {
                    RetrofitBookList.postFav(item!!.bookCode, mContext,item!!.title)
                } else if (value == "BookDetail") {
                    val intent = Intent(
                        mContext.applicationContext,
                        BookDetailCover::class.java
                    )
                    intent.putExtra("BookCode", String.format("%s", item!!.bookCode))
                    intent.putExtra("token", String.format("%s", token))
                    mContext.startActivity(intent)
                }
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
        var recylerView: RecyclerView
        var wrap: LinearLayout

        var sectionApiUrl: String? = null
        var sectionCategory: String? = null
        var sectionSubType: String? = null
        var sectionType: String? = null

        init {
            titleFirst = itemView.findViewById(R.id.Title_First)
            titleSecond = itemView.findViewById(R.id.Title_Second)
            gotoMore = itemView.findViewById(R.id.GotoMore)
            recylerView = itemView.findViewById(R.id.RecyclerView)
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