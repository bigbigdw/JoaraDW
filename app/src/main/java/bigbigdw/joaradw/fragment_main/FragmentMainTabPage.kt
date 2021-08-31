package bigbigdw.joaradw.fragment_main

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.main.TabViewModel
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList

class FragmentMainTabPage : Fragment() {

    private var tabviewmodel: TabViewModel? = null
    var category = "0"
    var token: String? = null

    private var adapter: AdapterMainBookTabs? = null
    var linearLayoutManager: LinearLayoutManager? = null
    private val items = ArrayList<MainBookData?>()

    var MainBookList: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_main_tab, container, false)
        MainBookList = root.findViewById(R.id.MainBookList)


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

    fun setLayout() {
        items.clear()
        val assetManager = requireActivity().assets

//        val bestCarousel = JSONObject()
//        try {
//            bestCarousel.put("writerName", writerName)
//            bestCarousel.put("subject", subject)
//            bestCarousel.put("bookImg", bookImg)
//            bestCarousel.put("isAdult", isAdult)
//            bestCarousel.put("isAdult", isAdult)
//            bestCarousel.put("isFinish", isFinish)
//            bestCarousel.put("isPremium", isPremium)
//            bestCarousel.put("isNobless", isNobless)
//            bestCarousel.put("intro", intro)
//            bestCarousel.put("isFavorite", isFavorite)
//            bestCarousel.put("bookCode", bookCode)
//            bestCarousel.put("categoryKoName", categoryKoName)
//            bestCarousel.put("cntChapter", cntChapter)
//            bestCarousel.put("cntFavorite", cntFavorite)
//            bestCarousel.put("cntRecom", cntRecom)
//            bestCarousel.put("cntPageRead", cntPageRead)
//        } catch (e: JSONException) {
//            // TODO Auto-generated catch block
//            e.printStackTrace()
//        }
//
//        if(i < 5){
//            bestCarouselURL.add(bestCarousel)
//        }

        adapter = AdapterMainBookTabs(requireContext(),items, category)
        linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        getMainBookData(assetManager, "Main_Tab.json")
    }

//    val viewListener =
//        ViewListener { position ->
//
//            val image: ImageView = customViewValue!!.findViewById(R.id.Img_BookBest)
//            val bestRankImage: ImageView = customViewValue.findViewById(R.id.BestRankImg)
//            val title: TextView = customViewValue.findViewById(R.id.Text_Title_Best)
//            val writer: TextView = customViewValue.findViewById(R.id.Text_Writer_Best)
//            val intro: TextView = customViewValue.findViewById(R.id.Text_Intro_Best)
//            val bestViewCount: TextView = customViewValue.findViewById(R.id.Text_BestViewed)
//            val bestFav: TextView = customViewValue.findViewById(R.id.Text_BestFav)
//            val bestRecommend: TextView = customViewValue.findViewById(R.id.Text_BestRecommend)
//            val bookCode: TextView = customViewValue.findViewById(R.id.BookCodeText)
//            val topText: TextView = customViewValue.findViewById(R.id.TopText)
//            val category: TextView = customViewValue.findViewById(R.id.Category)
//            val textCntChapter: TextView = customViewValue.findViewById(R.id.Text_CntChapter)
//            val bookLabel: LinearLayout = customViewValue.findViewById(R.id.BookLabel)
//
//            Glide.with(mContext.applicationContext).load(bestCarouselURL[position].getString("bookImg"))
//                .into(image)
//
//            when (position) {
//                0 -> {
//                    bestRankImage.setImageResource(R.drawable.icon_best_1)
//                }
//                1 -> {
//                    bestRankImage.setImageResource(R.drawable.icon_best_2)
//                }
//                2 -> {
//                    bestRankImage.setImageResource(R.drawable.icon_best_3)
//                }
//                3 -> {
//                    bestRankImage.setImageResource(R.drawable.icon_best_4)
//                }
//                4 -> {
//                    bestRankImage.setImageResource(R.drawable.icon_best_5)
//                }
//                else -> {
//                    Log.d("bestRankImage","NO_IMAGE")
//                }
//            }
//
//            title.text = bestCarouselURL[position].getString("subject")
//            writer.text = bestCarouselURL[position].getString("writerName")
//            intro.text = bestCarouselURL[position].getString("intro")
//            bestViewCount.text = bestCarouselURL[position].getString("cntPageRead")
//            bestFav.text = bestCarouselURL[position].getString("cntFavorite")
//            bestRecommend.text = bestCarouselURL[position].getString("cntRecom")
//            bookCode.text = bestCarouselURL[position].getString("bookCode")
//            category.text = bestCarouselURL[position].getString("categoryKoName")
//            textCntChapter.text = bestCarouselURL[position].getString("cntChapter")
//
//            if (bestCarouselURL[position].getString("isNobless") == "TRUE" && bestCarouselURL[position].getString("isAdult") == "FALSE") {
//                textSetting(topText, bookLabel, R.string.NOBLESS, -0x555a3b00)
//            } else if (bestCarouselURL[position].getString("isPremium") == "TRUE" && bestCarouselURL[position].getString("isAdult") == "FALSE") {
//                textSetting(topText, bookLabel, R.string.PREMIUM, -0x55b68e11)
//            } else if (bestCarouselURL[position].getString("isFinish") == "TRUE" && bestCarouselURL[position].getString("isAdult") == "FALSE") {
//                textSetting(topText, bookLabel, R.string.FINISH, -0x555a3b00)
//            } else if (bestCarouselURL[position].getString("isNobless") == "TRUE" && bestCarouselURL[position].getString("isAdult") == "TRUE") {
//                textSetting(topText, bookLabel, R.string.ADULT_NOBLESS, -0x550bbcca)
//            } else if (bestCarouselURL[position].getString("isPremium") == "TRUE" && bestCarouselURL[position].getString("isAdult") == "TRUE") {
//                textSetting(topText, bookLabel, R.string.ADULT_PREMIUM, -0x550bbcca)
//            } else if (bestCarouselURL[position].getString("isFinish") == "TRUE" && bestCarouselURL[position].getString("isAdult") == "TRUE") {
//                textSetting(topText, bookLabel, R.string.ADULT_FINISH, -0x550bbcca)
//            }
//
//
//            holder.carousel!!.indicatorGravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP
//            customViewValue
//        }

    //메인 북 데이터
    private fun getMainBookData(assetManager: AssetManager, BookType: String?) {
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

                if(i < 4){
                    items.add(
                        MainBookData(
                            sectionCategory,
                            sectionSubType,
                            sectionType
                        )
                    )
                }
            }

            MainBookList!!.layoutManager = linearLayoutManager
            MainBookList!!.adapter = adapter
            adapter!!.notifyDataSetChanged()

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
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