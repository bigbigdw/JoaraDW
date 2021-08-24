package bigbigdw.joaradw.fragment_main

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
import bigbigdw.joaradw.book.AdapterBookListA
import bigbigdw.joaradw.book.BookListDataA
import bigbigdw.joaradw.book.BookListResult
import bigbigdw.joaradw.book.RetrofitBookList
import bigbigdw.joaradw.book_detail.BookDetailCover
import bigbigdw.joaradw.joara_post.PostListData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class AdapterMainBookData(private val mContext: Context, items: List<MainBookData?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<MainBookData?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_main_book, parent, false)
        return MainBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainBookViewHolder) {
//            holder.sectionApiUrl = listData!![position]!!.sectionApiUrl
            holder.sectionCategory = listData!![position]!!.sectionCategory
            holder.sectionSubType = listData!![position]!!.sectionSubType
            holder.sectionType = listData!![position]!!.sectionType

            var token = mContext.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE).getString("TOKEN", "")

            val bookListItemsA = ArrayList<BookListDataA?>()
            var adapter: AdapterBookListA? = null
            var linearLayoutManager: LinearLayoutManager? = null

            adapter = AdapterBookListA(mContext,bookListItemsA)
            linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

            RetrofitBookList.getMDBooks(token, "1", "25")!!.enqueue(object : Callback<BookListResult?> {
                override fun onResponse(
                    call: Call<BookListResult?>,
                    response: Response<BookListResult?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val books = it.books
                            if (books != null) {
                                for (i in books.indices) {

                                    val bookCode = books[i].bookCode
                                    val bookImg = books[i].bookImg
                                    val historySortno = books[i].historySortno
                                    val subject = books[i].subject
                                    val writerName = books[i].writerName
                                    val isAdult = books[i].is_adult

                                    bookListItemsA.add(
                                        BookListDataA(
                                            writerName,
                                            subject,
                                            bookImg,
                                            bookCode,
                                            historySortno,
                                            isAdult,
                                            ""
                                        )
                                    )

                                    holder.mainBookList!!.layoutManager = linearLayoutManager
                                    holder.mainBookList.adapter = adapter
                                }
                            }
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<BookListResult?>, t: Throwable) {
                    Log.d("onFailure", "실패")
                }
            })

            adapter.setOnItemClickListener(object : AdapterBookListA.OnItemClickListener {
                override fun onItemClick(v: View?, position: Int, value: String?) {
                    if(!"".equals("WEBTOON")){
                        val item: BookListDataA? = adapter.getItem(position)
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
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var titleFirst: TextView
        var titleSecond: TextView
        var gotoMore: TextView
        var mainBookList: RecyclerView
        var wrap: LinearLayout

        var sectionApiUrl: String? = null
        var sectionCategory: String? = null
        var sectionSubType: String? = null
        var sectionType: String? = null

        init {
            titleFirst = itemView.findViewById(R.id.Title_First)
            titleSecond = itemView.findViewById(R.id.Title_Second)
            gotoMore = itemView.findViewById(R.id.GotoMore)
            mainBookList = itemView.findViewById(R.id.MainBookList)
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