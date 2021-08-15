package bigbigdw.joaradw.joara_post

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
import bigbigdw.joaradw.main.MainBookListAdapterC
import bigbigdw.joaradw.main.MainBookListData
import bigbigdw.joaradw.main.TabViewModel
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
    var orderBy = ""
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
                    orderBy = "redate"
                    getPostData()
                }
                "TAB2" -> {
                    orderBy = "cnt_recom"
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
                orderBy,
                "10"
                ,"1")

        call!!.enqueue(object : Callback<PostTabResult?> {
            override fun onResponse(call: Call<PostTabResult?>, response: Response<PostTabResult?>) {
                if (response.isSuccessful) {

                    response.body()?.let { it ->
                        val offset = it.offset
                        val page = it.page
                        val posts = it.posts
                        val status = it.status
                        val totalCnt = it.totalCnt

                        Log.d("@@@@",posts.toString())

                        if (posts != null) {
                            for (i in posts.indices) {
                                var postId = posts[i].postId
                                var nickname = posts[i].nickname
                                var categoryName = posts[i].categoryName
                                var title = posts[i].title
                                var thumbnailImage = posts[i].thumbnailImage
                                var redate = posts[i].redate
                                var isHtml = posts[i].isHtml
                                var isComment = posts[i].isComment
                                var cntRead = posts[i].cntRead
                                var cntRecom = posts[i].cntRecom
                                var cntComment = posts[i].cntComment

                                items.add(PostListData(categoryName,title,thumbnailImage))

                                cover!!.visibility = View.GONE
                                wrap!!.visibility = View.VISIBLE
                            }
                        }
                    }
                    recyclerView!!.layoutManager = linearLayoutManager
                    recyclerView!!.adapter = adapter

//                    adapter!!.setOnItemClickListener(AdapterPostList.OnItemClickListener { v: View?, position: Int, value: String? -> Log.d("@@@@", "DSADSAD") })
                } else {
                    blank!!.visibility = View.VISIBLE
                    wrap!!.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<PostTabResult?>, t: Throwable) {
                blank!!.visibility = View.VISIBLE
                wrap!!.visibility = View.GONE
            }
        })
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