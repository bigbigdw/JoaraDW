package bigbigdw.joaradw.fragment_genre

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
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


class AdapterGenreTabs(private val mContext: Context, items: List<MainBookData?>?, category : String?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<MainBookData?>?
    private val bookListItemsA1 = ArrayList<BookListDataABD?>()
    private val bookListItemsA2 = ArrayList<BookListDataABD?>()
    private val bookListItemsA3 = ArrayList<BookListDataABD?>()
    private val bookListItemsD1 = ArrayList<BookListDataABD?>()
    private val bookListItemsD2 = ArrayList<BookListDataABD?>()
    private val bookListItemsD3 = ArrayList<BookListDataABD?>()
    private val bookListItemsD4 = ArrayList<BookListDataABD?>()
    private val bookListItemsD5 = ArrayList<BookListDataABD?>()
    private val bookListItemsD6 = ArrayList<BookListDataABD?>()
    private val bookListItemsD7 = ArrayList<BookListDataABD?>()
    private val bookListItemsD8 = ArrayList<BookListDataABD?>()
    private val bookListItemsD9 = ArrayList<BookListDataABD?>()

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
                && listData!![position]!!.sectionSubType.equals("nobless")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "베스트"
                holder.titleSecond.text = " 주간 노블레스"
                getBookListTypeA(
                    token,
                    "bestNobless",
                    holder.recylerView,
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
                    holder.recylerView,
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
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("new")
                && listData!![position]!!.sectionSubType.equals("nobless")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "최신"
                holder.titleSecond.text = " 노블레스"
                getBookListTypeD(
                    token,
                    "newNobless",
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("new")
                && listData!![position]!!.sectionSubType.equals("joaraborn")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "최신"
                holder.titleSecond.text = " 조아라본"
                getBookListTypeD(
                    token,
                    "newJoaraBorn",
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("new")
                && listData!![position]!!.sectionSubType.equals("promise")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "최신"
                holder.titleSecond.text = " 노블성실"
                getBookListTypeD(
                    token,
                    "newPromise",
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("new")
                && listData!![position]!!.sectionSubType.equals("noblepre")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "최신"
                holder.titleSecond.text = " 노블프리"
                getBookListTypeD(
                    token,
                    "newNoblepre",
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("new")
                && listData!![position]!!.sectionSubType.equals("premium")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "최신"
                holder.titleSecond.text = " 프리미엄"
                getBookListTypeD(
                    token,
                    "newPremium",
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("new")
                && listData!![position]!!.sectionSubType.equals("series")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "최신"
                holder.titleSecond.text = " 무료"
                getBookListTypeD(
                    token,
                    "newSeries",
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("new")
                && listData!![position]!!.sectionSubType.equals("finish")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "최신"
                holder.titleSecond.text = " 완결"
                getBookListTypeD(
                    token,
                    "newFinish",
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("finish")
                && listData!![position]!!.sectionSubType.equals("nobless")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "완결"
                holder.titleSecond.text = " 노블레스"
                getBookListTypeD(
                    token,
                    "finishNobless",
                    holder.recylerView,
                    holder.wrap
                )
            }
            else if (listData!![position]!!.sectionType.equals("finish")
                && listData!![position]!!.sectionSubType.equals("premium")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "완결"
                holder.titleSecond.text = " 프리미엄"
                getBookListTypeD(
                    token,
                    "finishPremium",
                    holder.recylerView,
                    holder.wrap
                )
            }
        }
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

                        if(books!!.isEmpty()){
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

    //베스트들
    private fun getBookListTypeD(
        token: String?,
        type: String?,
        recyclerView: RecyclerView?,
        wrap : LinearLayout?
    ) {

        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

        val call: Call<BookListResult?>? =
            when {
                type.equals("newJoaraBorn") -> {
                    RetrofitBookList.getNewBookD(token, "premium", 1, categoryValue)
                }
                type.equals("newPromise") -> {
                    RetrofitBookList.getNewBookD(token, "promise", 1, categoryValue)
                }
                type.equals("newNobless") -> {
                    RetrofitBookList.getNewBookD(token, "nobless", 1, categoryValue)
                }
                type.equals("newNoblepre") -> {
                    RetrofitBookList.getNewBookD(token, "noblepre", 1, categoryValue)
                }
                type.equals("newPremium") -> {
                    RetrofitBookList.getNewBookD(token, "premium", 1, categoryValue)
                }
                type.equals("newSeries") -> {
                    RetrofitBookList.getNewBookD(token, "series", 1, categoryValue)
                }
                type.equals("finishAll") -> {
                    RetrofitBookList.getBookFinishD(token ,"finish", 1, categoryValue)
                }
                type.equals("finishNobless") -> {
                    RetrofitBookList.getBookFinishD(token ,"nobless_finish", 1, categoryValue)
                }
                type.equals("finishPremium") -> {
                    RetrofitBookList.getBookFinishD(token ,"premium_finish", 1, categoryValue)
                }
                else -> {
                    RetrofitBookList.getBookFinishD(token ,"", 1, categoryValue)
                }
            }

        val bookListItemsABD: ArrayList<BookListDataABD?> =
            when {
                type.equals("newJoaraBorn") -> {
                    bookListItemsD1
                }
                type.equals("newPromise") -> {
                    bookListItemsD2
                }
                type.equals("newNobless") -> {
                    bookListItemsD3
                }
                type.equals("newNoblepre") -> {
                    bookListItemsD4
                }
                type.equals("newPremium") -> {
                    bookListItemsD5
                }
                type.equals("newSeries") -> {
                    bookListItemsD6
                }
                type.equals("finishAll") -> {
                    bookListItemsD7
                }
                type.equals("finishNobless") -> {
                    bookListItemsD8
                }
                else -> {
                    bookListItemsD9
                }
            }

        val adapter: AdapterBookListD?
        adapter = AdapterBookListD(mContext, bookListItemsABD)

        call!!.enqueue(object : Callback<BookListResult?> {
            override fun onResponse(
                call: Call<BookListResult?>,
                response: Response<BookListResult?>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val books = it.books

                        if(books!!.isEmpty()){
                            wrap!!.visibility = View.GONE
                        }

                        for (i in books.indices) {

                            val bookCode = books[i].bookCode
                            val bookImg = books[i].bookImg
                            val historySortno = books[i].historySortno
                            val subject = books[i].subject
                            val writerName = books[i].writerName
                            val isAdult = books[i].is_adult


                            bookListItemsABD.add(
                                BookListDataABD(
                                    writerName,
                                    subject,
                                    bookImg,
                                    bookCode,
                                    historySortno,
                                    isAdult,
                                    type
                                )
                            )
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

        adapter!!.setOnItemClickListener(object : AdapterBookListD.OnItemClickListener {
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