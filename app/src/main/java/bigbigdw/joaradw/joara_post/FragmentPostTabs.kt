package bigbigdw.joaradw.joara_post

import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.base.BookBaseFragment
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.main.TabViewModel
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class FragmentPostTabs : BookBaseFragment() {
    private var adapter: AdapterPostList? = null
    private val items = ArrayList<PostListData>()
    private var tabviewmodel: TabViewModel? = null
    var wrap: LinearLayout? = null
    var cover: LinearLayout? = null
    var blank: LinearLayout? = null
    var store = ""
    var linearLayoutManager: LinearLayoutManager? = null
    var recyclerView: RecyclerView? = null

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

        recyclerView = root.findViewById(R.id.PostList)
        wrap = root.findViewById(R.id.TabWrap)
        cover = root.findViewById(R.id.LoadingLayout)
        blank = root.findViewById(R.id.BlankLayout)
        adapter = AdapterPostList(items)

        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)


        tabviewmodel!!.text.observe(viewLifecycleOwner) { tabNum: String? ->
            when (tabNum) {
                "TAB1" -> {
                    store = ""
                    getPostData()
                }
                "TAB2" -> {
                    store = ""
                    getPostData()
                }
            }
        }
        return root
    }

    fun getPostData(){
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.joara.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

        val service = retrofit.create(PostService::class.java)

        val call = service.getQuery(
                "5f3d29431deb982466d8aa50cf0fc6ab",
                "1",
                "",
                "redate",
                "10"
                ,"1")

        call!!.enqueue(object : Callback<PostTabResult?> {
            override fun onResponse(call: Call<PostTabResult?>, response: Response<PostTabResult?>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("@@@@onResponse", result.toString())
                } else {
                    Log.d("@@@@onResponse", "실패")
                }
            }

            override fun onFailure(call: Call<PostTabResult?>, t: Throwable) {
                Log.d("@@@@onFailure", "통신 실패")
            }
        })
    }


    fun newBookListJSON(root: View, assetManager: AssetManager?, bookType: String?) {
//        wrap!!.visibility = View.VISIBLE
//        cover!!.visibility = View.GONE
//        blank!!.visibility = View.GONE
//        val recyclerViewJSON: RecyclerView = root.findViewById(R.id.Main_NewBookList)
//        val linearLayoutManagerJSON =
//            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        recyclerViewJSON.layoutManager = linearLayoutManagerJSON
//        recyclerViewJSON.adapter = adapter
//        adapter!!.setItems(MainBookDataJSON().getData(assetManager, bookType))
//        adapter!!.notifyDataSetChanged()
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        fun newInstance(index: Int): FragmentPostTabs {
            val fragment = FragmentPostTabs()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}