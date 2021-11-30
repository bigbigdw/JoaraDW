package bigbigdw.joaradw.fragment_writer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import bigbigdw.joaradw.R
import bigbigdw.joaradw.book.BookListDataC
import bigbigdw.joaradw.writer.MyListResult
import bigbigdw.joaradw.writer.RetrofitWriter
import bigbigdw.joaradw.writer.WriterBookCountResult
import bigbigdw.joaradw.writer.WriterMemberLevelResult
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentWriter : Fragment() {

    lateinit var root: View

    var tviewTab1: TextView? = null
    var tviewTab2: TextView? = null
    var tviewTab3: TextView? = null
    var tviewTab4: TextView? = null
    var tviewWriterTitle: TextView? = null
    var tviewWriterName: TextView? = null
    var tviewWriterLevel: TextView? = null

    var iviewProfile: ImageView? = null
    var iviewBadge: ImageView? = null

    var llayoutLevel: LinearLayout? = null
    var Wrap: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_writer, container, false)

        tviewTab1 = root.findViewById(R.id.tview_Tab1)
        tviewTab2 = root.findViewById(R.id.tview_Tab2)
        tviewTab3 = root.findViewById(R.id.tview_Tab3)
        tviewTab4 = root.findViewById(R.id.tview_Tab4)
        iviewProfile = root.findViewById(R.id.iview_profile)

        tviewWriterTitle = root.findViewById(R.id.tview_WriterTitle)
        tviewWriterName = root.findViewById(R.id.tview_WriterName)
        tviewWriterLevel = root.findViewById(R.id.tview_WriterLevel)
        iviewBadge = root.findViewById(R.id.iview_badge)

        llayoutLevel = root.findViewById(R.id.llayout_level)
        Wrap = root.findViewById(R.id.Wrap)

        setLayout()

        return root
    }

    fun setLayout(){

        val token = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

        val profileImg = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE).getString("PROFILEIMG", "")

        tviewWriterName!!.text = requireContext().getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("NICKNAME", "")

        Glide.with(requireContext()).load(profileImg).into(iviewProfile!!)

        RetrofitWriter.getWriterLevel(token,context)!!
            .enqueue(object : Callback<WriterMemberLevelResult?> {
                override fun onResponse(
                    call: Call<WriterMemberLevelResult?>,
                    response: Response<WriterMemberLevelResult?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->

                            val memberLevel = it.writerLevel?.memberLevel

                            if(memberLevel!!.equals("")){
                                llayoutLevel!!.visibility = View.GONE
                            } else {
                                val icon = it.writerLevel?.memberLevel?.icon
                                val penExp = it.writerLevel?.memberLevel?.penExp
                                val penName = it.writerLevel?.memberLevel?.penName

                                tviewWriterTitle!!.text = penExp
                                tviewWriterLevel!!.text = penName
                                Glide.with(requireContext()).load(icon).into(iviewBadge!!)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<WriterMemberLevelResult?>, t: Throwable) {
                    Log.d("onFailure", "실패")
                }
            })

        RetrofitWriter.getBookNum(token, context)!!
            .enqueue(object : Callback<WriterBookCountResult?> {
                override fun onResponse(
                    call: Call<WriterBookCountResult?>,
                    response: Response<WriterBookCountResult?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->
                            val exerCount = it.bookCount?.exerCount
                            val finishCount = it.bookCount?.finishCount
                            val seriesCount = it.bookCount?.seriesCount
                            val shortCount = it.bookCount?.shortCount

                            tviewTab1!!.text = seriesCount
                            tviewTab2!!.text = finishCount
                            tviewTab3!!.text = shortCount
                            tviewTab4!!.text = exerCount
                        }
                    }
                }

                override fun onFailure(call: Call<WriterBookCountResult?>, t: Throwable) {
                    Log.d("onFailure", "실패")
                }
            })

        RetrofitWriter.getMyBookList(token, "1", "all", "N","", context)!!
            .enqueue(object : Callback<MyListResult?> {
                override fun onResponse(
                    call: Call<MyListResult?>,
                    response: Response<MyListResult?>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { it ->

                            val books = it.books
                            if (books != null) {
                                Wrap!!.visibility = View.VISIBLE
                                for (i in books.indices) {

                                    val writerName = books[i].writerName
                                    val subject = books[i].subject
                                    val bookImg = books[i].bookImg
                                    val isAdult = books[i].isAdult
                                    val isPremium = books[i].isPremium
                                    val isNobless = books[i].isNobless
                                    val bookCode = books[i].bookCode
                                    val categoryKoName = books[i].categoryKoName
                                    val cntChapter = books[i].cntChapter
                                    val created = books[i].created
                                    val cntFavorite = books[i].cntFavorite
                                    val cntRecom = books[i].cntRecom
                                    val cnt_page_read = books[i].cntPageRead

                                    Log.d("!!!!","성공")
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<MyListResult?>, t: Throwable) {
                    Log.d("onFailure", "실패")
                }
            })
    }

}