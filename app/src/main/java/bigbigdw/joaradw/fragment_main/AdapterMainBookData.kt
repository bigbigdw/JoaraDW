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
            .inflate(R.layout.item_fragment_main, parent, false)
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

            if (!token.equals("")) {
                if (listData!![position]!!.sectionSubType.equals("favoriteList")) {

                    holder.wrap.visibility = View.VISIBLE
                    holder.titleFirst.text = nickName
                    holder.titleSecond.text = "님의 선호작 이어보기"
                    getBookListTypeA(
                        token,
                        "favoriteList",
                        holder.recylerView,
                        holder.wrap
                    )
                } else if (listData!![position]!!.sectionSubType.equals("recomList2")) {

                    holder.wrap.visibility = View.VISIBLE
                    holder.titleFirst.text = "당신의 취향"
                    holder.titleSecond.text = "을 저격할 작품"
                    getBookListTypeA(
                        token,
                        "recomList2",
                        holder.recylerView,
                        holder.wrap
                    )
                }
            }

            if (listData!![position]!!.sectionSubType.equals("mdList")) {

                holder.wrap.visibility = View.VISIBLE
                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "조아라"
                holder.titleSecond.text = "가 추천하는 소설"
                getBookListTypeA(
                    token,
                    "mdList",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionSubType.equals("webtoon")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "조아라"
                holder.titleSecond.text = "가 추천하는 웹툰"
                getBookListTypeA(
                    token,
                    "webtoon",
                    holder.recylerView,
                    holder.wrap
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
                    holder.recylerView
                )
            } else if (listData!![position]!!.sectionSubType.equals("festivalList")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "77페스티벌"
                holder.titleSecond.text = "최신작품"
                getBookListTypeB(
                    token,
                    "festivalList",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionSubType.equals("joaraBornList")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "JOARA"
                holder.titleSecond.text = " BORN"
                getBookListTypeB(
                    token,
                    "joaraBornList",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionSubType.equals("contestFreeAwardList")) {
                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "독자가 픽한"
                holder.titleSecond.text = " 공모전 수상작"
                getBookListTypeC(
                    token,
                    "contestFreeAwardList",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionSubType.equals("notyList")) {
                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "역대 조아라 NOTY"
                holder.titleSecond.text = " 수상작"
                getBookListTypeC(
                    token,
                    "notyList",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionSubType.equals("pageReadList")) {
                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "천만명의 독자"
                holder.titleSecond.text = "가 이미 본 작품"
                getBookListTypeC(
                    token,
                    "pageReadList",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionSubType.equals("noblessBestList")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "노블레스"
                holder.titleSecond.text = " 베스트"

                getBookListTypeD(
                    token,
                    "noblessBestList",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionSubType.equals("premiumBestList")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "프리미엄"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "premiumBestList",
                    holder.recylerView,
                    holder.wrap
                )
            } else if (listData!![position]!!.sectionCategory.equals("1")) {
                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "판타지"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "1",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("2")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "무협"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "2",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("5")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "로맨스"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "5",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("3")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "퓨전"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "3",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("4")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "게임"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "4",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("22")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "로맨스판타지"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "22",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("20")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "BL"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "20",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("23")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "GL"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "23",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("21")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "스포츠"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "21",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("12")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "역사"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "12",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("9")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "팬픽"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "9",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("6")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "라이트노벨"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "6",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("19")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "패러디"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "19",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("60")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "일반작품"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "60",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionCategory.equals("50")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "문학작품"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "50",
                    holder.recylerView,
                    holder.wrap
                )
            }else if (listData!![position]!!.sectionSubType.equals("couponBestList")) {

                holder.wrap.visibility = View.VISIBLE
                holder.titleFirst.text = "후원쿠폰"
                holder.titleSecond.text = " 베스트"
                getBookListTypeD(
                    token,
                    "couponBestList",
                    holder.recylerView,
                    holder.wrap
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
                            }
                        }
                    }
                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView.adapter = adapter
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
        token: String?,
        type: String?,
        recyclerView: RecyclerView?,
        wrap: LinearLayout?
    ) {

        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

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

        val adapter: AdapterBookListA?
        adapter = AdapterBookListA(mContext, bookListItemsABD)

        call!!.enqueue(object : Callback<BookListResult?> {
            override fun onResponse(
                call: Call<BookListResult?>,
                response: Response<BookListResult?>
            ) {
                if (type.equals("webtoon")) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val books = it.webtoons

                            if(books!!.isEmpty()){
                                wrap!!.visibility = View.GONE
                            }

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
                            }
                        }
                        recyclerView!!.layoutManager = linearLayoutManager
                        recyclerView.adapter = adapter
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
                                }
                            }
                        }
                        recyclerView!!.layoutManager = linearLayoutManager
                        recyclerView.adapter = adapter
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
                if(!type.equals("webtoon")){
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
        token: String?,
        type: String?,
        recyclerView: RecyclerView?,
        wrap: LinearLayout?
    ) {

        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

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

    //77페스티벌, 조아라 본
    private fun getBookListTypeB(
        token: String?,
        type: String?,
        recyclerView: RecyclerView?,
        wrap: LinearLayout?
    ) {

        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

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

        val adapter: AdapterBookListB?
        adapter = AdapterBookListB(mContext, bookListItemsABD)

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
        token: String?,
        type: String?,
        recyclerView: RecyclerView?,
        wrap: LinearLayout?
    ) {

        val linearLayoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

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