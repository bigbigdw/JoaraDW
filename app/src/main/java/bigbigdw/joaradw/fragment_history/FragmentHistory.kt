package bigbigdw.joaradw.fragment_history

import android.content.Intent
import bigbigdw.joaradw.base.BookBaseFragment
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.main.OLD_MainBookListData
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.RequestQueue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.os.Handler
import bigbigdw.joaradw.R
import com.android.volley.toolbox.Volley
import bigbigdw.joaradw.JOARADW
import bigbigdw.joaradw.BookPagination
import bigbigdw.joaradw.etc.API
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import bigbigdw.joaradw.etc.HELPER
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.book_detail.BookDetail
import org.json.JSONArray
import bigbigdw.joaradw.model.BookInfo
import com.android.volley.Request
import org.json.JSONException
import com.android.volley.VolleyError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FragmentHistory : BookBaseFragment() {
    private var adapter: AdapterBookListHistory? = null
    private val items = ArrayList<BookListDataABD?>()

    var wrap: LinearLayout? = null
    var cover: LinearLayout? = null
    var blank: LinearLayout? = null
    var login : LinearLayout? = null
    var token: String? = null
    var linearLayoutManager: LinearLayoutManager? = null
    var recyclerView: RecyclerView? = null
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = root.findViewById(R.id.rview_History)
        wrap = root.findViewById(R.id.Wrap)
        cover = root.findViewById(R.id.LoadingLayout)
        blank = root.findViewById(R.id.BlankLayout)
        login = root.findViewById(R.id.LoginLayout)
        adapter = AdapterBookListHistory(items)
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        token = requireContext()!!.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

        if(token.equals("")){
            login!!.visibility = View.VISIBLE
        } else{
            login!!.visibility = View.GONE
        }

        recyclerView!!.addOnScrollListener(recyclerViewScroll)

        favBookList(page)

        return root
    }


    private fun favBookList(page : Int?) {
        RetrofitBookList.getBookHistory(token, page)!!.enqueue(object :
            Callback<BookListResult?> {
            override fun onResponse(
                call: Call<BookListResult?>,
                response: Response<BookListResult?>
            ) {

                if (response.isSuccessful) {
                    cover!!.visibility = View.GONE
                    blank!!.visibility = View.GONE
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap!!.visibility = View.VISIBLE
                            for (i in books.indices) {

                                val bookCode = books[i].bookCode
                                val bookImg = books[i].bookImg
                                val historySortno = books[i].historySortno
                                val subject = books[i].subject
                                val writerName = books[i].writerName
                                val isAdult = books[i].is_adult

                                items.add(
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
                        } else {
                            blank!!.visibility = View.VISIBLE
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                    if(page == 1){
                        val dividerItemDecoration = DividerItemDecoration(
                            recyclerView!!.context,
                            linearLayoutManager!!.orientation
                        )
                        recyclerView!!.addItemDecoration(dividerItemDecoration)
                        recyclerView!!.layoutManager = linearLayoutManager
                        recyclerView!!.adapter = adapter
                        recyclerView!!.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<BookListResult?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

        adapter!!.setOnItemClickListener(object : AdapterBookListHistory.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                val item: BookListDataABD? = adapter!!.getItem(position)
                val intent = Intent(requireContext().applicationContext, BookDetail::class.java)
                intent.putExtra("BookCode", String.format("%s", item!!.bookCode))
                intent.putExtra("TOKEN", String.format("%s", token))
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)
            }
        })

    }

    private var recyclerViewScroll: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
//            if(!recyclerView.canScrollVertically(1)) {
//                cover!!.visibility = View.VISIBLE
//                page++
//                favBookList(page)
//            }

        }
    }
}