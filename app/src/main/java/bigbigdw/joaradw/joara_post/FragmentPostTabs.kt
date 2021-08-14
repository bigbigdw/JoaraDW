package bigbigdw.joaradw.joara_post

import android.content.res.AssetManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.BookPagination
import bigbigdw.joaradw.JOARADW
import bigbigdw.joaradw.R
import bigbigdw.joaradw.base.BookBaseFragment
import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.fragment_new.NewTab
import bigbigdw.joaradw.main.MainBookDataJSON
import bigbigdw.joaradw.main.TabViewModel
import bigbigdw.joaradw.model.BookInfo
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class FragmentPostTabs : BookBaseFragment() {
    private var adapter: AdapterPostList? = null
    private val items = ArrayList<PostListData>()
    private var tabviewmodel: TabViewModel? = null
    var wrap: LinearLayout? = null
    var cover: LinearLayout? = null
    var blank: LinearLayout? = null
    var store = ""
    var userToken: String? = null
    var etc = ""
    var classes = "&class="
    var linearLayoutManager: LinearLayoutManager? = null
    var recyclerView: RecyclerView? = null
    var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabviewmodel = ViewModelProvider(this).get(TabViewModel::class.java)
        var index = 1
        if (arguments != null) {
            index = requireArguments().getInt(ARG_SECTION_NUMBER)
        }
        tabviewmodel!!.setIndex(index)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_post_tabs, container, false)
        queue = Volley.newRequestQueue(requireActivity())
        recyclerView = root.findViewById(R.id.Main_NewBookList)
        wrap = root.findViewById(R.id.TabWrap)
        cover = root.findViewById(R.id.LoadingLayout)
        blank = root.findViewById(R.id.BlankLayout)
        adapter = AdapterPostList(items)
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        checkToken()
        val app = requireActivity().applicationContext as JOARADW
        userToken = app.token
        val assetManager = requireActivity().assets
        tabviewmodel!!.text.observe(viewLifecycleOwner) { tabNum: String? ->
            when (tabNum) {
                "TAB1" -> {
                    store = ""
//                    getPostData()
                }
                "TAB2" -> newBookListJSON(root, assetManager, "Main_Tab_Best_77FES.json")
            }
        }
        return root
    }

//    fun getPostData() {
//        etc = "&store=$store&orderby=redate&offset=25&page=1&token=$userToken$classes"
//        BookPagination.populateData(API.BOOK_LIST_JOA, etc, queue, wrap, items, cover, blank)
//        recyclerView!!.layoutManager = linearLayoutManager
//        adapter!!.notifyDataSetChanged()
//        recyclerView!!.adapter = adapter
//        recyclerView?.let {
//            adapter?.let { it1 ->
//                wrap?.let { it2 ->
//                    scrollListener(
//                        API.BOOK_LIST_JOA,
//                        queue,
//                        it2,
//                        items,
//                        it1,
//                        it,
//                        etc
//                    )
//                }
//            }
//        }
//
//        adapter!!.setOnItemClickListener { v: View?, position: Int, value: String? ->
//            val item = adapter!!.getItem(position)
//            adapterListener(item, value, queue)
//        }
//    }

    fun scrollListener(
        api: String,
        queue: RequestQueue?,
        wrap: LinearLayout,
        items: ArrayList<PostListData>,
        adpater: AdapterPostList,
        recyclerView: RecyclerView,
        etc: String
    ) {
        val isLoading = booleanArrayOf(false)
        val page = intArrayOf(2)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading[0] && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == items.size - 1) {
                    val resultURL = HELPER.API + api + HELPER.ETC + etc + "&page=" + page[0]
                    items.clear()
                    adpater.notifyItemInserted(items.size - 1)
                    val jsonRequest = JsonObjectRequest(
                        Request.Method.GET, resultURL, null,
                        { response: JSONObject ->
                            val handler =
                                Handler(Looper.myLooper()!!)
                            handler.postDelayed({
                                items.removeAt(items.size - 1)
                                val scrollPosition = items.size
                                adpater.notifyItemRemoved(scrollPosition)
                                try {
                                    val flag = response.getJSONArray("books")
                                    for (i in 0 until flag.length()) {
                                        val jo = flag.getJSONObject(i)
                                        val tempBookInfo = BookInfo.getParseData(jo)
                                        items.add(
                                                PostListData(
                                                tempBookInfo.writer,
                                                tempBookInfo.title,
                                                tempBookInfo.bookImg,
                                                tempBookInfo.isAdult,
                                                tempBookInfo.isFinish,
                                                tempBookInfo.isPremium,
                                                tempBookInfo.isNobless,
                                                tempBookInfo.intro,
                                                tempBookInfo.isFavorite,
                                                "",
                                                "",
                                                "",
                                                0,
                                                "",
                                                tempBookInfo.bookCode,
                                                tempBookInfo.categoryKoName,
                                                tempBookInfo.ctnChapter
                                            )
                                        )
                                        wrap.visibility = View.VISIBLE
                                    }
                                    adpater.notifyDataSetChanged()
                                    isLoading[0] = false
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }, 2000)
                        }
                    ) { error: VolleyError? ->
                        Log.d(
                            "scrollListener",
                            "에러!"
                        )
                    }
                    queue?.add(jsonRequest)
                    isLoading[0] = true
                    page[0]++
                }
            }
        })
    }


    fun newBookListJSON(root: View, assetManager: AssetManager?, bookType: String?) {
        wrap!!.visibility = View.VISIBLE
        cover!!.visibility = View.GONE
        blank!!.visibility = View.GONE
        val recyclerViewJSON: RecyclerView = root.findViewById(R.id.Main_NewBookList)
        val linearLayoutManagerJSON =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerViewJSON.layoutManager = linearLayoutManagerJSON
        recyclerViewJSON.adapter = adapter
        adapter!!.setItems(MainBookDataJSON().getData(assetManager, bookType))
        adapter!!.notifyDataSetChanged()
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): NewTab {
            val fragment = NewTab()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}