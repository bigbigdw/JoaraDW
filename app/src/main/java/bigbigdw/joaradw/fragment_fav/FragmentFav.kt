package bigbigdw.joaradw.fragment_fav

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import bigbigdw.joaradw.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import bigbigdw.joaradw.book.*
import bigbigdw.joaradw.book_detail.BookDetailCover
import bigbigdw.joaradw.main.TabViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class FragmentFav : Fragment() {

    private var adapter: AdapterBookListFav? = null
    private val items = ArrayList<BookListDataFav?>()

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
        val root = inflater.inflate(R.layout.fragment_fav, container, false)

        recyclerView = root.findViewById(R.id.Fav_FavBookList)
        wrap = root.findViewById(R.id.Tab_Fav)
        cover = root.findViewById(R.id.LoadingLayout)
        blank = root.findViewById(R.id.BlankLayout)
        login = root.findViewById(R.id.LoginLayout)
        adapter = AdapterBookListFav(items)
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
        RetrofitBookList.getBookFav(token, "bookdate", page)!!.enqueue(object : Callback<BookListResultC?> {
            override fun onResponse(
                call: Call<BookListResultC?>,
                response: Response<BookListResultC?>
            ) {

                if (response.isSuccessful) {
                    cover!!.visibility = View.GONE
                    blank!!.visibility = View.GONE
                    response.body()?.let { it ->
                        val books = it.books
                        if (books != null) {
                            wrap!!.visibility = View.VISIBLE
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
                                val cntChapter = "총" + books[i].cntChapter + "편"

                                items.add(
                                    BookListDataFav(
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
                                        cntChapter,
                                    )
                                )
                            }
                        } else {
                            blank!!.visibility = View.VISIBLE
                        }
                    }
                    adapter!!.notifyDataSetChanged()
                    if(page == 1){
                        recyclerView!!.layoutManager = linearLayoutManager
                        recyclerView!!.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<BookListResultC?>, t: Throwable) {
                Log.d("onFailure", "실패")
            }
        })

        adapter!!.setOnItemClickListener(object : AdapterBookListFav.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, value: String?) {
                val item: BookListDataFav? = adapter!!.getItem(position)
                RetrofitBookList.postFav(item!!.bookCode, requireContext(),item!!.title)
            }
        })

    }

    private var recyclerViewScroll: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if(!recyclerView.canScrollVertically(1)) {
                cover!!.visibility = View.VISIBLE
                page++
                favBookList(page)
            }

        }
    }
}