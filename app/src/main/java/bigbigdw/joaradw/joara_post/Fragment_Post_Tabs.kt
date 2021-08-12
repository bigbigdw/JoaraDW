package bigbigdw.joaradw.joara_post

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.BookPagination
import bigbigdw.joaradw.JOARADW
import bigbigdw.joaradw.R
import bigbigdw.joaradw.base.BookBaseFragment
import bigbigdw.joaradw.etc.API
import bigbigdw.joaradw.etc.BookList
import bigbigdw.joaradw.fragment_new.NewTab
import bigbigdw.joaradw.main.MainBookDataJSON
import bigbigdw.joaradw.main.MainBookListAdapterC
import bigbigdw.joaradw.main.MainBookListData
import bigbigdw.joaradw.main.TabViewModel
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.util.ArrayList

class Fragment_Post_Tabs : BookBaseFragment() {
    private var adapter: MainBookListAdapterC? = null
    private val items = ArrayList<MainBookListData>()
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
        val root = inflater.inflate(R.layout.fragment_new_tab, container, false)
        queue = Volley.newRequestQueue(requireActivity())
        recyclerView = root.findViewById(R.id.Main_NewBookList)
        wrap = root.findViewById(R.id.TabWrap)
        cover = root.findViewById(R.id.LoadingLayout)
        blank = root.findViewById(R.id.BlankLayout)
        adapter = MainBookListAdapterC(items)
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        checkToken()
        val app = requireActivity().applicationContext as JOARADW
        userToken = app.token
        val assetManager = requireActivity().assets
        tabviewmodel!!.text.observe(viewLifecycleOwner, { tabNum: String? ->
            when (tabNum) {
                "TAB1" -> {
                    store = ""
                    newBookList()
                }
                "TAB2" -> newBookListJSON(root, assetManager, "Main_Tab_Best_77FES.json")
                "TAB3" -> newBookListJSON(root, assetManager, "Main_KidamuBookList.json")
                "TAB4" -> newBookListJSON(root, assetManager, "Main_PromisedBookList.json")
                "TAB5" -> {
                    store = "nobless"
                    newBookList()
                }
                "TAB6" -> {
                    store = "premium"
                    newBookList()
                }
                "TAB7" -> {
                    store = "series"
                    newBookList()
                }
                "TAB8" -> {
                    store = "finish"
                    newBookList()
                }
                else -> {
                    classes = "&class=short"
                    newBookList()
                }
            }
        })
        return root
    }

    fun newBookList() {
        etc = "&store=$store&orderby=redate&offset=25&page=1&token=$userToken$classes"
        BookPagination.populateData(API.BOOK_LIST_JOA, etc, queue, wrap, items, cover, blank)
        BookList.initAdapterC(recyclerView, linearLayoutManager, adapter)
        BookPagination.scrollListener(
            API.BOOK_LIST_JOA,
            queue,
            wrap,
            items,
            adapter,
            recyclerView,
            etc
        )
        adapter!!.setOnItemClickListener { v: View?, position: Int, value: String? ->
            val item = adapter!!.getItem(position)
            adapterListener(item, value, queue)
        }
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