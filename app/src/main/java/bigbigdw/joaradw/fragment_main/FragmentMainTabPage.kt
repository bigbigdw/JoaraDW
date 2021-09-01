package bigbigdw.joaradw.fragment_main

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.book_detail.BookDetailCover
import bigbigdw.joaradw.main.TabViewModel
import com.bumptech.glide.Glide
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ViewListener
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList

class FragmentMainTabPage : Fragment() {

    private var tabviewmodel: TabViewModel? = null
    var category = "0"
    var token: String? = null

    private var adapterFirst: AdapterMainBookTabs? = null
    private var adapterSecond: AdapterMainBookTabs? = null
    private val items1 = ArrayList<MainBookData?>()
    private val items2 = ArrayList<MainBookData?>()

    var Carousel_Best: CarouselView? = null
    var RecyclerView_Best: RecyclerView? = null
    var Carousel_BestArray: MutableList<JSONObject> = ArrayList()

    var Carousel_New: CarouselView? = null
    var Carousel_NewArray: MutableList<JSONObject> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main_tab, container, false)
        RecyclerView_Best = root.findViewById(R.id.RecyclerView_Best)
        Carousel_Best = root.findViewById(R.id.Carousel_Best)

        Carousel_New = root.findViewById(R.id.Carousel_New)

        token = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

        tabviewmodel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            when (tabNum) {
                "TAB1" -> {
                    category = "1"
                }
                "TAB2" -> {
                    category = "2"
                }
                "TAB3" -> {
                    category = "3"
                }
                "TAB4" -> {
                    category = "4"
                }
                "TAB5" -> {
                    category = "5"
                }
                "TAB6" -> {
                    category = "6"
                }
                "TAB7" -> {
                    category = "7"
                }
                "TAB8" -> {
                    category = "8"
                }
                "TAB9" -> {
                    category = "9"
                }
                "TAB10" -> {
                    category = "10"
                }
                "TAB11" -> {
                    category = "11"
                }
                "TAB12" -> {
                    category = "12"
                }
                "TAB13" -> {
                    category = "13"
                }
                else -> {
                    category = "14"
                }
            }
            setLayout()
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

    override fun onDestroyView() {
        super.onDestroyView()
        Carousel_Best!!.removeAllViews()
        Carousel_BestArray = ArrayList()
    }

    fun setLayout() {
        items1.clear()
        val assetManager = requireActivity().assets

        adapterFirst = AdapterMainBookTabs(requireContext(),items1, category)
        adapterSecond = AdapterMainBookTabs(requireContext(),items2, category)

        getMainBookData(assetManager, "Main_Tab.json","1")
        getMainBookData(assetManager, "Main_Tab.json","2")

        getBookListBestCarousel()

        Carousel_Best!!.setViewListener(viewListenerBest)

        Carousel_Best!!.setImageClickListener { position ->
            val intent = Intent(
                requireContext().applicationContext,
                BookDetailCover::class.java
            )
            intent.putExtra("BookCode", String.format("%s", Carousel_BestArray[position].getString("bookCode")))
            intent.putExtra("token", String.format("%s", token))
            requireContext().startActivity(intent)
        }

        getBookListCCarousel("1",Carousel_New,Carousel_NewArray)

        Carousel_New!!.setViewListener(viewListenerNew)

        Carousel_New!!.setImageClickListener { position ->
            val intent = Intent(
                requireContext().applicationContext,
                BookDetailCover::class.java
            )
            intent.putExtra("BookCode", String.format("%s", Carousel_NewArray[position].getString("bookCode")))
            intent.putExtra("token", String.format("%s", token))
            requireContext().startActivity(intent)
        }
    }

    //메인 북 데이터
    private fun getMainBookData(assetManager: AssetManager, BookType: String?, type: String?) {

        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        try {
            val `is` = assetManager.open(BookType!!)
            val isr = InputStreamReader(`is`)
            val reader = BufferedReader(isr)
            val buffer = StringBuilder()
            var line = reader.readLine()
            while (line != null) {
                buffer.append(line).append("\n")
                line = reader.readLine()
            }
            val jsonData = buffer.toString()
            val jsonObject = JSONObject(jsonData)
            val flag = jsonObject.getJSONArray("main_info")

            for (i in 0 until flag.length()) {
                val jo = flag.getJSONObject(i)
                val sectionType = jo.getString("section_type")
                val sectionCategory = jo.getString("section_category")
                val sectionSubType = jo.getString("section_sub_type")

                if(type.equals("1")){
                    if(i in 1..3){
                        items1.add(
                            MainBookData(
                                sectionCategory,
                                sectionSubType,
                                sectionType
                            )
                        )
                    }
                    RecyclerView_Best!!.layoutManager = linearLayoutManager
                    RecyclerView_Best!!.adapter = adapterFirst
                    adapterFirst!!.notifyDataSetChanged()

                } else {
                    if(i in 4..6){
                        items2.add(
                            MainBookData(
                                sectionCategory,
                                sectionSubType,
                                sectionType
                            )
                        )
                    }
                }
                RecyclerView_Best!!.layoutManager = linearLayoutManager
                RecyclerView_Best!!.adapter = adapterFirst
                adapterFirst!!.notifyDataSetChanged()
            }



        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    //베스트 캐러셀
    private fun getBookListBestCarousel() {

        RetrofitBookList.getBookBest(token, "weekly" , "", category)!!.enqueue(object :
            Callback<BookListBestResult?> {
            override fun onResponse(
                call: Call<BookListBestResult?>,
                response: Response<BookListBestResult?>
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
                                val cntFavorite = books[i].cntFavorite
                                val cntRecom = books[i].cntRecom
                                val cntPageRead = books[i].cntPageRead

                                val bestCarousel = JSONObject()
                                try {
                                    bestCarousel.put("writerName", writerName)
                                    bestCarousel.put("subject", subject)
                                    bestCarousel.put("bookImg", bookImg)
                                    bestCarousel.put("isAdult", isAdult)
                                    bestCarousel.put("isAdult", isAdult)
                                    bestCarousel.put("isFinish", isFinish)
                                    bestCarousel.put("isPremium", isPremium)
                                    bestCarousel.put("isNobless", isNobless)
                                    bestCarousel.put("intro", intro)
                                    bestCarousel.put("isFavorite", isFavorite)
                                    bestCarousel.put("bookCode", bookCode)
                                    bestCarousel.put("categoryKoName", categoryKoName)
                                    bestCarousel.put("cntChapter", cntChapter)
                                    bestCarousel.put("cntFavorite", cntFavorite)
                                    bestCarousel.put("cntRecom", cntRecom)
                                    bestCarousel.put("cntPageRead", cntPageRead)
                                } catch (e: JSONException) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace()
                                }

                                if(i < 10){
                                    Carousel_BestArray.add(bestCarousel)
                                }
                            }
                            Carousel_Best!!.pageCount = Carousel_BestArray.size
                            Carousel_Best!!.slideInterval = 4000
                            Carousel_Best!!.visibility = View.VISIBLE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BookListBestResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

        private val viewListenerBest =
        ViewListener { position ->
            val customView: View = layoutInflater.inflate(R.layout.item_booklist_best, null)

            val image: ImageView = customView!!.findViewById(R.id.Img_BookBest)
            val bestRankImage: ImageView = customView.findViewById(R.id.BestRankImg)
            val title: TextView = customView.findViewById(R.id.Text_Title_Best)
            val writer: TextView = customView.findViewById(R.id.Text_Writer_Best)
            val intro: TextView = customView.findViewById(R.id.Text_Intro_Best)
            val bestViewCount: TextView = customView.findViewById(R.id.Text_BestViewed)
            val bestFav: TextView = customView.findViewById(R.id.Text_BestFav)
            val bestRecommend: TextView = customView.findViewById(R.id.Text_BestRecommend)
            val bookCode: TextView = customView.findViewById(R.id.BookCodeText)
            val topText: TextView = customView.findViewById(R.id.TopText)
            val category: TextView = customView.findViewById(R.id.Category)
            val textCntChapter: TextView = customView.findViewById(R.id.Text_CntChapter)
            val bookLabel: LinearLayout = customView.findViewById(R.id.BookLabel)

            Glide.with(requireContext().applicationContext).load(Carousel_BestArray[position].getString("bookImg"))
                .into(image)

            when (position) {
                0 -> {
                    bestRankImage.setImageResource(R.drawable.icon_best_1)
                }
                1 -> {
                    bestRankImage.setImageResource(R.drawable.icon_best_2)
                }
                2 -> {
                    bestRankImage.setImageResource(R.drawable.icon_best_3)
                }
                3 -> {
                    bestRankImage.setImageResource(R.drawable.icon_best_4)
                }
                4 -> {
                    bestRankImage.setImageResource(R.drawable.icon_best_5)
                }
                else -> {
                    Log.d("bestRankImage","NO_IMAGE")
                }
            }

            title.text = Carousel_BestArray[position].getString("subject")
            writer.text = Carousel_BestArray[position].getString("writerName")
            intro.text = Carousel_BestArray[position].getString("intro")
            bestViewCount.text = Carousel_BestArray[position].getString("cntPageRead")
            bestFav.text = Carousel_BestArray[position].getString("cntFavorite")
            bestRecommend.text = Carousel_BestArray[position].getString("cntRecom")
            bookCode.text = Carousel_BestArray[position].getString("bookCode")
            category.text = Carousel_BestArray[position].getString("categoryKoName")
            textCntChapter.text = Carousel_BestArray[position].getString("cntChapter")

            if (Carousel_BestArray[position].getString("isNobless") == "TRUE" && Carousel_BestArray[position].getString("isAdult") == "FALSE") {
                textSetting(topText, bookLabel, R.string.NOBLESS, -0x555a3b00)
            } else if (Carousel_BestArray[position].getString("isPremium") == "TRUE" && Carousel_BestArray[position].getString("isAdult") == "FALSE") {
                textSetting(topText, bookLabel, R.string.PREMIUM, -0x55b68e11)
            } else if (Carousel_BestArray[position].getString("isFinish") == "TRUE" && Carousel_BestArray[position].getString("isAdult") == "FALSE") {
                textSetting(topText, bookLabel, R.string.FINISH, -0x555a3b00)
            } else if (Carousel_BestArray[position].getString("isNobless") == "TRUE" && Carousel_BestArray[position].getString("isAdult") == "TRUE") {
                textSetting(topText, bookLabel, R.string.ADULT_NOBLESS, -0x550bbcca)
            } else if (Carousel_BestArray[position].getString("isPremium") == "TRUE" && Carousel_BestArray[position].getString("isAdult") == "TRUE") {
                textSetting(topText, bookLabel, R.string.ADULT_PREMIUM, -0x550bbcca)
            } else if (Carousel_BestArray[position].getString("isFinish") == "TRUE" && Carousel_BestArray[position].getString("isAdult") == "TRUE") {
                textSetting(topText, bookLabel, R.string.ADULT_FINISH, -0x550bbcca)
            }


            Carousel_Best!!.indicatorGravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            customView
        }

    private fun textSetting(topText: TextView, bookLabel: LinearLayout, title: Int, color: Int) {
        topText.setText(title)
        bookLabel.setBackgroundColor(color)
    }

    //베스트 캐러셀
    private fun getBookListCCarousel(type: String?, carouselview :CarouselView?, CarouselArray : MutableList<JSONObject>?) {

        val call: Call<BookListResultC?>? =
            when {
                type.equals("1") -> {
                    RetrofitBookList.getNewBook(token, "", 1, category)
                }
                else -> {
                    RetrofitBookList.getBookListC(token, "page_read_book", "home","")
                }
            }

        call!!.enqueue(object :
            Callback<BookListResultC?> {
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

                                val booklistCCarousel = JSONObject()
                                try {
                                    booklistCCarousel.put("writerName", writerName)
                                    booklistCCarousel.put("subject", subject)
                                    booklistCCarousel.put("bookImg", bookImg)
                                    booklistCCarousel.put("isAdult", isAdult)
                                    booklistCCarousel.put("isAdult", isAdult)
                                    booklistCCarousel.put("isFinish", isFinish)
                                    booklistCCarousel.put("isPremium", isPremium)
                                    booklistCCarousel.put("isNobless", isNobless)
                                    booklistCCarousel.put("intro", intro)
                                    booklistCCarousel.put("isFavorite", isFavorite)
                                    booklistCCarousel.put("bookCode", bookCode)
                                    booklistCCarousel.put("categoryKoName", categoryKoName)
                                    booklistCCarousel.put("cntChapter", cntChapter)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                                if(i < 10){
                                    CarouselArray!!.add(booklistCCarousel)
                                }
                            }
                            carouselview!!.pageCount = CarouselArray!!.size
                            carouselview!!.slideInterval = 4000
                            carouselview!!.visibility = View.VISIBLE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BookListResultC?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })
    }

    private val viewListenerNew =
        ViewListener { position ->
            val itemView: View = layoutInflater.inflate(R.layout.item_booklist_c, null)

            val image: ImageView = itemView.findViewById(R.id.Img_Book)
            val favon: ImageView = itemView.findViewById(R.id.FavON)
            val favoff: ImageView = itemView.findViewById(R.id.FavOff)
            val title: TextView = itemView.findViewById(R.id.Text_Title)
            val writer: TextView = itemView.findViewById(R.id.Text_Writer)
            val intro: TextView = itemView.findViewById(R.id.Text_Intro)
            val topText: TextView = itemView.findViewById(R.id.TopText)
            val bookCodeWrap: TextView = itemView.findViewById(R.id.BookCodeText)
            val textCntChapter: TextView = itemView.findViewById(R.id.Text_CntChapter)
            val bar: TextView = itemView.findViewById(R.id.Bar)
            val category: TextView = itemView.findViewById(R.id.Category)

            favoff.visibility = View.GONE
            favon.visibility = View.GONE

            Glide.with(requireContext().applicationContext).load(Carousel_NewArray[position].getString("bookImg"))
                .into(image)

            title.text = Carousel_NewArray[position].getString("subject")
            writer.text = Carousel_NewArray[position].getString("writerName")
            intro.text = Carousel_NewArray[position].getString("intro")
            category.text = Carousel_NewArray[position].getString("categoryKoName")
            textCntChapter.text = Carousel_NewArray[position].getString("cntChapter")
            bookCodeWrap.text = Carousel_NewArray[position].getString("bookCode")

            if (Carousel_NewArray[position].getString("isNobless") == "TRUE" && Carousel_NewArray[position].getString("isAdult") == "FALSE") {
                textSettingC(R.string.NOBLESS, -0x555a3b00, topText, bar, category)
            } else if (Carousel_NewArray[position].getString("isPremium") == "TRUE" && Carousel_NewArray[position].getString("isAdult") == "FALSE") {
                textSettingC(R.string.FINISH, -0x5589898a, topText, bar, category)
            } else if (Carousel_NewArray[position].getString("isFinish") == "TRUE" && Carousel_NewArray[position].getString("isAdult") == "FALSE") {
                textSettingC(R.string.ADULT_NOBLESS, -0x550bbcca, topText, bar, category)
            } else if (Carousel_NewArray[position].getString("isNobless") == "TRUE" && Carousel_NewArray[position].getString("isAdult") == "TRUE") {
                textSettingC(R.string.ADULT_PREMIUM, -0x55b68e11, topText, bar, category)
            } else if (Carousel_NewArray[position].getString("isPremium") == "TRUE" && Carousel_NewArray[position].getString("isAdult") == "TRUE") {
                textSettingC(R.string.ADULT_PREMIUM, -0x55b68e11, topText, bar, category)
            } else if (Carousel_NewArray[position].getString("isFinish") == "TRUE" && Carousel_NewArray[position].getString("isAdult") == "TRUE") {
                textSettingC(R.string.ADULT_FINISH, -0x5589898a, topText, bar, category)
            } else {
                topText.text = "무료"
                textSettingC(R.string.FREE, -0x56000000, topText, bar, category)
            }

            Carousel_New!!.indicatorGravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            itemView
        }

    fun textSettingC(title: Int, color: Int, topText: TextView, bar: TextView, category: TextView) {
        topText.setText(title)
        topText.setTextColor(color)
        bar.setTextColor(color)
        category.setTextColor(color)
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