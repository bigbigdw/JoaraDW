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
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.book_detail.BookDetailCover
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class AdapterMainBookData(private val mContext: Context, items: List<MainBookData?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<MainBookData?>?
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

            val token = mContext.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
                .getString("TOKEN", "")
            val nickName = mContext.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
                .getString("NICKNAME", "")

            Log.d(
                "@@@@",
                "listData!![position]!!.sectionSubType  = " + listData!![position]!!.sectionSubType
            )


            if (!token.equals("")) {
                if (listData!![position]!!.sectionSubType.equals("favoriteList")) {

                    val linearLayoutManagerA1: LinearLayoutManager?
                    val adapterA1: AdapterBookListA?
                    adapterA1 = AdapterBookListA(mContext, bookListItemsA1)
                    linearLayoutManagerA1 =
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                    holder.wrap.visibility = View.VISIBLE
                    holder.titleFirst.text = nickName
                    holder.titleSecond.text = "님의 선호작 이어보기"
                    getBookListTypeA(
                        adapterA1,
                        linearLayoutManagerA1,
                        token,
                        "favoriteList",
                        holder.mainBookList
                    )
                } else if (listData!![position]!!.sectionSubType.equals("recomList2")) {
                    holder.wrap.visibility = View.VISIBLE

                    val adapterA2: AdapterBookListA?
                    val linearLayoutManagerA2: LinearLayoutManager?

                    adapterA2 = AdapterBookListA(mContext, bookListItemsA2)
                    linearLayoutManagerA2 =
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                    holder.titleFirst.text = "당신의 취향"
                    holder.titleSecond.text = "을 저격할 작품"
                    getBookListTypeA(
                        adapterA2,
                        linearLayoutManagerA2,
                        token,
                        "recomList2",
                        holder.mainBookList
                    )
                }
            }

            if (listData!![position]!!.sectionSubType.equals("mdList")) {
                holder.wrap.visibility = View.VISIBLE
                val linearLayoutManagerA3: LinearLayoutManager?
                val adapterA3: AdapterBookListA?
                adapterA3 = AdapterBookListA(mContext, bookListItemsA3)
                linearLayoutManagerA3 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "조아라"
                holder.titleSecond.text = "가 추천하는 소설"
                getBookListTypeA(
                    adapterA3,
                    linearLayoutManagerA3,
                    token,
                    "mdList",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("webtoon")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerA4: LinearLayoutManager?
                val adapterA4: AdapterBookListA?
                adapterA4 = AdapterBookListA(mContext, bookListItemsA4)
                linearLayoutManagerA4 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "조아라"
                holder.titleSecond.text = "가 추천하는 웹툰"
                getBookListTypeA(
                    adapterA4,
                    linearLayoutManagerA4,
                    token,
                    "webtoon",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("mdList2")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerBookEventList: LinearLayoutManager?
                val adapterBookEventList: AdapterBookEventList?
                adapterBookEventList = AdapterBookEventList(mContext, bookEventListItem)
                linearLayoutManagerBookEventList =
                    LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

                holder.titleFirst.text = "조아라"
                holder.titleSecond.text = "가 추천하는 이벤트"
                getBookEventList(
                    adapterBookEventList,
                    linearLayoutManagerBookEventList,
                    token,
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("festivalList")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerB1: LinearLayoutManager?
                val adapterB1: AdapterBookListB?
                adapterB1 = AdapterBookListB(mContext, bookListItemsB1)
                linearLayoutManagerB1 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "77페스티벌"
                holder.titleSecond.text = "최신작품"
                getBookListTypeB(
                    adapterB1,
                    linearLayoutManagerB1,
                    token,
                    "festivalList",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("joaraBornList")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerB2: LinearLayoutManager?
                val adapterB2: AdapterBookListB?
                adapterB2 = AdapterBookListB(mContext, bookListItemsB2)
                linearLayoutManagerB2 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "JOARA"
                holder.titleSecond.text = " BORN"
                getBookListTypeB(
                    adapterB2,
                    linearLayoutManagerB2,
                    token,
                    "joaraBornList",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("contestFreeAwardList")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerC1: LinearLayoutManager?
                val adapterC1: AdapterBookListC?
                adapterC1 = AdapterBookListC(mContext, bookListItemsC1)
                linearLayoutManagerC1 =
                    LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

                holder.titleFirst.text = "독자가 픽한"
                holder.titleSecond.text = " 공모전 수상작"
                getBookListTypeC(
                    adapterC1,
                    linearLayoutManagerC1,
                    token,
                    "contestFreeAwardList",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("notyList")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerC2: LinearLayoutManager?
                val adapterC2: AdapterBookListC?
                adapterC2 = AdapterBookListC(mContext, bookListItemsC2)
                linearLayoutManagerC2 =
                    LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

                holder.titleFirst.text = "역대 조아라 NOTY"
                holder.titleSecond.text = " 수상작"
                getBookListTypeC(
                    adapterC2,
                    linearLayoutManagerC2,
                    token,
                    "notyList",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("pageReadList")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerC3: LinearLayoutManager?
                val adapterC3: AdapterBookListC?
                adapterC3 = AdapterBookListC(mContext, bookListItemsC3)
                linearLayoutManagerC3 =
                    LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

                holder.titleFirst.text = "천만명의 독자"
                holder.titleSecond.text = "가 이미 본 작품"
                getBookListTypeC(
                    adapterC3,
                    linearLayoutManagerC3,
                    token,
                    "pageReadList",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("noblessBestList")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD1: LinearLayoutManager?
                val adapterD1: AdapterBookListD?
                adapterD1 = AdapterBookListD(mContext, bookListItemsD1)
                linearLayoutManagerD1 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "노블레스"
                holder.titleSecond.text = " 베스트"

                getBookListTypeD(
                    adapterD1,
                    linearLayoutManagerD1,
                    token,
                    "noblessBestList",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionSubType.equals("premiumBestList")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD2: LinearLayoutManager?
                val adapterD2: AdapterBookListD?
                adapterD2 = AdapterBookListD(mContext, bookListItemsD2)
                linearLayoutManagerD2 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "프리미엄"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD2,
                    linearLayoutManagerD2,
                    token,
                    "premiumBestList",
                    holder.mainBookList
                )
            } else if (listData!![position]!!.sectionCategory.equals("1")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD3: LinearLayoutManager?
                val adapterD3: AdapterBookListD?
                adapterD3 = AdapterBookListD(mContext, bookListItemsD3)
                linearLayoutManagerD3 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "판타지"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD3,
                    linearLayoutManagerD3,
                    token,
                    "1",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("2")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD4: LinearLayoutManager?
                val adapterD4: AdapterBookListD?
                adapterD4 = AdapterBookListD(mContext, bookListItemsD4)
                linearLayoutManagerD4 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "무협"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD4,
                    linearLayoutManagerD4,
                    token,
                    "2",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("5")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD5: LinearLayoutManager?
                val adapterD5: AdapterBookListD?
                adapterD5 = AdapterBookListD(mContext, bookListItemsD5)
                linearLayoutManagerD5 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "로맨스"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD5,
                    linearLayoutManagerD5,
                    token,
                    "5",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("3")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD6: LinearLayoutManager?
                val adapterD6: AdapterBookListD?
                adapterD6 = AdapterBookListD(mContext, bookListItemsD6)
                linearLayoutManagerD6 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "퓨전"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD6,
                    linearLayoutManagerD6,
                    token,
                    "3",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("4")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD7: LinearLayoutManager?
                val adapterD7: AdapterBookListD?
                adapterD7 = AdapterBookListD(mContext, bookListItemsD7)
                linearLayoutManagerD7 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "게임"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD7,
                    linearLayoutManagerD7,
                    token,
                    "4",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("22")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD8: LinearLayoutManager?
                val adapterD8: AdapterBookListD?
                adapterD8 = AdapterBookListD(mContext, bookListItemsD8)
                linearLayoutManagerD8 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "로맨스판타지"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD8,
                    linearLayoutManagerD8,
                    token,
                    "22",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("20")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD9: LinearLayoutManager?
                val adapterD9: AdapterBookListD?
                adapterD9 = AdapterBookListD(mContext, bookListItemsD9)
                linearLayoutManagerD9 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "BL"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD9,
                    linearLayoutManagerD9,
                    token,
                    "20",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("23")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD10: LinearLayoutManager?
                val adapterD10: AdapterBookListD?
                adapterD10 = AdapterBookListD(mContext, bookListItemsD10)
                linearLayoutManagerD10 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "GL"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD10,
                    linearLayoutManagerD10,
                    token,
                    "23",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("21")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD11: LinearLayoutManager?
                val adapterD11: AdapterBookListD?
                adapterD11 = AdapterBookListD(mContext, bookListItemsD11)
                linearLayoutManagerD11 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "스포츠"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD11,
                    linearLayoutManagerD11,
                    token,
                    "21",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("12")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD12: LinearLayoutManager?
                val adapterD12: AdapterBookListD?
                adapterD12 = AdapterBookListD(mContext, bookListItemsD12)
                linearLayoutManagerD12 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "역사"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD12,
                    linearLayoutManagerD12,
                    token,
                    "12",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("9")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD13: LinearLayoutManager?
                val adapterD13: AdapterBookListD?
                adapterD13 = AdapterBookListD(mContext, bookListItemsD13)
                linearLayoutManagerD13 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "팬픽"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD13,
                    linearLayoutManagerD13,
                    token,
                    "9",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("6")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD14: LinearLayoutManager?
                val adapterD14: AdapterBookListD?
                adapterD14 = AdapterBookListD(mContext, bookListItemsD14)
                linearLayoutManagerD14 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "라이트노벨"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD14,
                    linearLayoutManagerD14,
                    token,
                    "6",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("19")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD15: LinearLayoutManager?
                val adapterD15: AdapterBookListD?
                adapterD15 = AdapterBookListD(mContext, bookListItemsD15)
                linearLayoutManagerD15 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "패러디"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD15,
                    linearLayoutManagerD15,
                    token,
                    "19",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("60")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD16: LinearLayoutManager?
                val adapterD16: AdapterBookListD?
                adapterD16 = AdapterBookListD(mContext, bookListItemsD16)
                linearLayoutManagerD16 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "일반작품"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD16,
                    linearLayoutManagerD16,
                    token,
                    "60",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionCategory.equals("50")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD17: LinearLayoutManager?
                val adapterD17: AdapterBookListD?
                adapterD17 = AdapterBookListD(mContext, bookListItemsD17)
                linearLayoutManagerD17 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "문학작품"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD17,
                    linearLayoutManagerD17,
                    token,
                    "50",
                    holder.mainBookList
                )
            }else if (listData!![position]!!.sectionSubType.equals("couponBestList")) {
                holder.wrap.visibility = View.VISIBLE

                val linearLayoutManagerD18: LinearLayoutManager?
                val adapterD18: AdapterBookListD?
                adapterD18 = AdapterBookListD(mContext, bookListItemsD18)
                linearLayoutManagerD18 =
                    LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                holder.titleFirst.text = "후원쿠폰"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    adapterD18,
                    linearLayoutManagerD18,
                    token,
                    "couponBestList",
                    holder.mainBookList
                )
            }
        }
    }

    //MD 추천
    private fun getBookEventList(
        adapter: AdapterBookEventList?,
        linearLayoutManager: LinearLayoutManager?,
        token: String?,
        recyclerView: RecyclerView?
    ) {

        RetrofitBookList.getMDEventList(token)!!.enqueue(object : Callback<BookEventListResult?> {
            override fun onResponse(
                call: Call<BookEventListResult?>,
                response: Response<BookEventListResult?>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val mdTheme = it.mdTheme

                        if (mdTheme != null) {
                            for (i in mdTheme.indices) {
                                val content = mdTheme[i].content
                                val enddate = mdTheme[i].enddate
                                val eventImg = mdTheme[i].eventImg
                                val idx = mdTheme[i].idx
                                val themeSubTitle = mdTheme[i].themeSubTitle
                                val title = mdTheme[i].title


                                bookEventListItem.add(
                                    BookEventListData(
                                        content,
                                        eventImg,
                                        enddate,
                                        idx,
                                        themeSubTitle,
                                        title
                                    )
                                )

                                recyclerView!!.layoutManager = linearLayoutManager
                                recyclerView.adapter = adapter
                            }
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BookEventListResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    //이어보기, 추천작품, 웹툰
    private fun getBookListTypeA(
        adapter: AdapterBookListA?,
        linearLayoutManager: LinearLayoutManager?,
        token: String?,
        type: String?,
        recyclerView: RecyclerView?
    ) {

        val call: Call<BookListResult?>? =
            when {
                type.equals("favoriteList") -> {
                    RetrofitBookList.getUserHistoryBooks(token, "1", "25")
                }
                type.equals("recomList2") -> {
                    RetrofitBookList.getRecommendBooks(token, "1", "25")
                }
                type.equals("mdList") -> {
                    RetrofitBookList.getMDBooks(token, "1", "25")
                }
                else -> {
                    RetrofitBookList.getMDWebtoon(token)
                }
            }

        val bookListItemsABD: ArrayList<BookListDataABD?> =
            when {
                type.equals("favoriteList") -> {
                    bookListItemsA1
                }
                type.equals("recomList2") -> {
                    bookListItemsA2
                }
                type.equals("mdList") -> {
                    bookListItemsA3
                }
                else -> bookListItemsA4
            }

        call!!.enqueue(object : Callback<BookListResult?> {
            override fun onResponse(
                call: Call<BookListResult?>,
                response: Response<BookListResult?>
            ) {
                if (type.equals("webtoon")) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val books = it.webtoons
                            if (books != null) {
                                for (i in books.indices) {
                                    val bookImg = books[i].webtoon_img
                                    val isAdult = books[i].is_adult
                                    val subject = books[i].webtoon_title

                                    bookListItemsABD.add(
                                        BookListDataABD(
                                            "",
                                            subject,
                                            bookImg,
                                            "",
                                            "",
                                            isAdult,
                                            type
                                        )
                                    )

                                    recyclerView!!.layoutManager = linearLayoutManager
                                    recyclerView.adapter = adapter
                                }
                            }
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                } else {
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

                                    recyclerView!!.layoutManager = linearLayoutManager
                                    recyclerView.adapter = adapter
                                }
                            }
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<BookListResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

        adapter!!.setOnItemClickListener(object : AdapterBookListA.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                if (!type.equals("WEBTOON")) {
                    val item: BookListDataABD? = adapter.getItem(position)
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

    //77페스티벌, 조아라 본
    private fun getBookListTypeC(
        adapter: AdapterBookListC?,
        linearLayoutManager: LinearLayoutManager?,
        token: String?,
        type: String?,
        recyclerView: RecyclerView?
    ) {

        val call: Call<BookListResultC?>? =
            when {
                type.equals("contestFreeAwardList") -> {
                    RetrofitBookList.getBookListC(token, "contest_free_award", "home","")
                }
                type.equals("notyList") -> {
                    RetrofitBookList.getBookListC(token, "noty", "home","2019")
                }
                else -> {
                    RetrofitBookList.getBookListC(token, "page_read_book", "home","")
                }
            }

        val bookListItemsC: ArrayList<BookListDataC?> =
            when {
                type.equals("contestFreeAwardList") -> {
                    bookListItemsC1
                }
                type.equals("notyList") -> {
                    bookListItemsC2
                }
                else -> bookListItemsC3
            }

        call!!.enqueue(object : Callback<BookListResultC?> {
            override fun onResponse(
                call: Call<BookListResultC?>,
                response: Response<BookListResultC?>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
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

                                recyclerView!!.layoutManager = linearLayoutManager
                                recyclerView.adapter = adapter
                            }
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BookListResultC?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

        adapter!!.setOnItemClickListener(object : AdapterBookListC.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                if (!type.equals("WEBTOON")) {
                    val item: BookListDataC? = adapter.getItem(position)
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

    //77페스티벌, 조아라 본
    private fun getBookListTypeB(
        adapter: AdapterBookListB?,
        linearLayoutManager: LinearLayoutManager?,
        token: String?,
        type: String?,
        recyclerView: RecyclerView?
    ) {

        val call: Call<BookListResult?>? =
            when {
                type.equals("festivalList") -> {
                    RetrofitBookList.getBookListB(token, "festival", "redate", "home")
                }
                else -> {
                    RetrofitBookList.getBookListB(token, "joaraborn", "redate", "home")
                }
            }

        val bookListItemsABD: ArrayList<BookListDataABD?> =
            when {
                type.equals("festivalList") -> {
                    bookListItemsB1
                }
                else -> bookListItemsB2
            }

        call!!.enqueue(object : Callback<BookListResult?> {
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

                                recyclerView!!.layoutManager = linearLayoutManager
                                recyclerView.adapter = adapter
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

        adapter!!.setOnItemClickListener(object : AdapterBookListB.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                if (!type.equals("WEBTOON")) {
                    val item: BookListDataABD? = adapter.getItem(position)
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

    //베스트들
    private fun getBookListTypeD(
        adapter: AdapterBookListD?,
        linearLayoutManager: LinearLayoutManager?,
        token: String?,
        type: String?,
        recyclerView: RecyclerView?
    ) {

        val call: Call<BookListResult?>? =
            when {
                type.equals("noblessBestList") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "nobless", "cnt_best","home","")
                }
                type.equals("premiumBestList") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "premium", "cnt_best","home","")
                }
                type.equals("1") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","1")
                }
                type.equals("2") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","2")
                }
                type.equals("5") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","5")
                }
                type.equals("3") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","3")
                }
                type.equals("4") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","4")
                }
                type.equals("22") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","22")
                }
                type.equals("20") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","20")
                }
                type.equals("23") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","23")
                }
                type.equals("21") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","21")
                }
                type.equals("12") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","12")
                }
                type.equals("9") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","9")
                }
                type.equals("6") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","6")
                }
                type.equals("19") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","19")
                }
                type.equals("60") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","60")
                }
                type.equals("50") -> {
                    RetrofitBookList.getBookListD(token, "todaybest", "", "cnt_best","home","50")
                }
                else -> {
                    RetrofitBookList.getBookListD(token, "support_coupon", "", "","home","")
                }
            }

        val bookListItemsABD: ArrayList<BookListDataABD?> =
            when {
                type.equals("noblessBestList") -> {
                    bookListItemsD1
                }
                type.equals("premiumBestList") -> {
                    bookListItemsD2
                }
                type.equals("1") -> {
                    bookListItemsD3
                }
                type.equals("2") -> {
                    bookListItemsD4
                }
                type.equals("5") -> {
                    bookListItemsD5
                }
                type.equals("3") -> {
                    bookListItemsD6
                }
                type.equals("4") -> {
                    bookListItemsD7
                }
                type.equals("22") -> {
                    bookListItemsD8
                }
                type.equals("20") -> {
                    bookListItemsD9
                }
                type.equals("23") -> {
                    bookListItemsD10
                }
                type.equals("21") -> {
                    bookListItemsD11
                }
                type.equals("12") -> {
                    bookListItemsD12
                }
                type.equals("9") -> {
                    bookListItemsD13
                }
                type.equals("6") -> {
                    bookListItemsD14
                }
                type.equals("19") -> {
                    bookListItemsD15
                }
                type.equals("60") -> {
                    bookListItemsD16
                }
                type.equals("50") -> {
                    bookListItemsD17
                }
                else -> bookListItemsD18
            }

        call!!.enqueue(object : Callback<BookListResult?> {
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

                                recyclerView!!.layoutManager = linearLayoutManager
                                recyclerView.adapter = adapter
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

        adapter!!.setOnItemClickListener(object : AdapterBookListD.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                if (!type.equals("WEBTOON")) {
                    val item: BookListDataABD? = adapter.getItem(position)
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
            mainBookList = itemView.findViewById(R.id.MainBookListSecond)
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