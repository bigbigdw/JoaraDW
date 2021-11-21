package bigbigdw.joaradw.fragment_best

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
import bigbigdw.joaradw.novel.MainBookData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class AdapterBookTabBest(
    private val mContext: Context,
    items: List<MainBookData?>?,
    bestValue: String?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<MainBookData?>?
    private val bookListItemsBest1 = ArrayList<BookListDataBest?>()
    private val bookListItemsBest2 = ArrayList<BookListDataBest?>()
    private val bookListItemsBest3 = ArrayList<BookListDataBest?>()
    private val bookListItemsBest4 = ArrayList<BookListDataBest?>()
    private val bookListItemsBest5 = ArrayList<BookListDataBest?>()
    private val bookListItemsBest6 = ArrayList<BookListDataBest?>()
    private val bookListItemsBest7 = ArrayList<BookListDataBest?>()
    val best = bestValue

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

            if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("All")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "전체"
                holder.titleSecond.text = " 베스트"
                getBookListBest(
                    token,
                    "bestAll",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("Nobless")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "노블레스"
                holder.titleSecond.text = " 베스트"
                getBookListBest(
                    token,
                    "bestNobless",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("Premium")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "프리미엄"
                holder.titleSecond.text = " 베스트"
                getBookListBest(
                    token,
                    "bestPremium",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("Series")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "무료"
                holder.titleSecond.text = " 베스트"
                getBookListBest(
                    token,
                    "bestSeries",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("Lately")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "신규"
                holder.titleSecond.text = " 베스트"
                getBookListBest(
                    token,
                    "bestLately",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("Finish")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "완결"
                holder.titleSecond.text = " 베스트"
                getBookListBest(
                    token,
                    "bestFinish",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionType.equals("best")
                && listData!![position]!!.sectionSubType.equals("Nobless_Classic")
            ) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "노블레스 클래식"
                holder.titleSecond.text = " 베스트"
                getBookListBest(
                    token,
                    "bestNobless_Classic",
                    holder.recylerView,
                    holder.wrap
                )
            }
        }
    }

    private fun getBookListBest(
        token: String?,
        type: String?,
        recyclerView: RecyclerView?,
        wrap: LinearLayout?
    ) {
        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        val call: Call<BookListBestResult?>? =
            when {
                type.equals("bestAll") -> {
                    RetrofitBookList.getBookBest(token, best, "", "0")
                }
                type.equals("bestNobless") -> {
                    RetrofitBookList.getBookBest(token, best, "nobless", "0")
                }
                type.equals("bestPremium") -> {
                    RetrofitBookList.getBookBest(token, best, "premium", "0")
                }
                type.equals("bestSeries") -> {
                    RetrofitBookList.getBookBest(token, best, "series", "0")
                }
                type.equals("bestLately") -> {
                    RetrofitBookList.getBookBest(token, best, "lately", "0")
                }
                type.equals("bestFinish") -> {
                    RetrofitBookList.getBookBest(token, best, "finish", "0")
                }
                else -> {
                    RetrofitBookList.getBookBest(token, best, "nobless_classic", "0")
                }
            }

        val bookListItemsBest: ArrayList<BookListDataBest?> =
            when {
                type.equals("bestAll") -> {
                    bookListItemsBest1
                }
                type.equals("bestNobless") -> {
                    bookListItemsBest2
                }
                type.equals("bestPremium") -> {
                    bookListItemsBest3
                }
                type.equals("bestSeries") -> {
                    bookListItemsBest4
                }
                type.equals("bestLately") -> {
                    bookListItemsBest5
                }
                type.equals("bestFinish") -> {
                    bookListItemsBest6
                }
                else -> bookListItemsBest7
            }

        val adapter: AdapterBookListBest?
        adapter = AdapterBookListBest(mContext, bookListItemsBest)

        call!!.enqueue(object : Callback<BookListBestResult?> {
            override fun onResponse(
                call: Call<BookListBestResult?>,
                response: Response<BookListBestResult?>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val books = it.books

                        if (books!!.isEmpty()) {
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

                            if (i < 9) {
                                bookListItemsBest!!.add(
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
                    }
                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView.adapter = adapter
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BookListBestResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

        adapter!!.setOnItemClickListener(object : AdapterBookListBest.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                val item: BookListDataBest? = adapter!!.getItem(position)
                if (value == "FAV") {
                    RetrofitBookList.postFav(item!!.bookCode, mContext, item!!.title)
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