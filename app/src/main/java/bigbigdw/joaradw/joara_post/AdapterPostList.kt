package bigbigdw.joaradw.joara_post

import bigbigdw.joaradw.main.MainBookListData
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import android.widget.TextView
import androidx.cardview.widget.CardView
import android.widget.LinearLayout
import org.json.JSONObject
import org.json.JSONException
import android.widget.Toast
import bigbigdw.joaradw.Config
import bigbigdw.joaradw.main.MainBookListAdapterC
import java.util.ArrayList

class AdapterPostList(items: List<PostListData?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<PostListData?>?
    var f = "FALSE"

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: AdapterPostList.OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.main_booklistdata_booklist_c, parent, false)
            PostListViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.spinner, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostListViewHolder) {
            populateItemRows(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val viewTypeLoading = 1
        return if (listData!![position] == null) viewTypeLoading else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    private fun populateItemRows(holder: PostListViewHolder, position: Int) {
        val item = listData!![position]
        Glide.with(holder.itemView.context)
                .load(item!!.bookImg)
                .into(holder.image)
        holder.title.text = listData!![position]!!.title
        holder.writer.text = listData!![position]!!.writer
        holder.intro.text = listData!![position]!!.intro
        holder.bookCodeWrap.text = listData!![position]!!.bookCode
        holder.bookFav.text = listData!![position]!!.isFav
        holder.category.text = listData!![position]!!.bookCategory
        holder.textCntChapter.text = listData!![position]!!.cntChapter
        val topText = holder.topText
        val bar = holder.bar
        val category = holder.category
        if (listData!![position]!!.isNobless == "TRUE" && listData!![position]!!.isAdult == f) {
            textSetting(R.string.NOBLESS, -0x555a3b00, topText, bar, category)
        } else if (listData!![position]!!.isPremium == "TRUE" && listData!![position]!!.isAdult == f) {
            textSetting(R.string.PREMIUM, -0x55b68e11, topText, bar, category)
        } else if (listData!![position]!!.isFinish == "TRUE" && listData!![position]!!.isAdult == f) {
            textSetting(R.string.FINISH, -0x5589898a, topText, bar, category)
        } else if (listData!![position]!!.isNobless == "TRUE" && listData!![position]!!.isAdult == "TRUE") {
            textSetting(R.string.ADULT_NOBLESS, -0x550bbcca, topText, bar, category)
        } else if (listData!![position]!!.isPremium == "TRUE" && listData!![position]!!.isAdult == "TRUE") {
            textSetting(R.string.ADULT_PREMIUM, -0x55b68e11, topText, bar, category)
        } else if (listData!![position]!!.isFinish == "TRUE" && listData!![position]!!.isAdult == "TRUE") {
            textSetting(R.string.ADULT_FINISH, -0x5589898a, topText, bar, category)
        } else {
            topText.text = "무료"
            textSetting(R.string.FREE, -0x56000000, topText, bar, category)
        }
        if (listData!![position]!!.isFav == "TRUE") {
            holder.favon.visibility = View.VISIBLE
            holder.favoff.visibility = View.GONE
        } else {
            holder.favoff.visibility = View.VISIBLE
            holder.favon.visibility = View.GONE
        }
    }

    fun textSetting(title: Int, color: Int, topText: TextView, bar: TextView, category: TextView) {
        topText.setText(title)
        topText.setTextColor(color)
        bar.setTextColor(color)
        category.setTextColor(color)
    }

    inner class PostListViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var favon: ImageView
        var favoff: ImageView
        var title: TextView
        var writer: TextView
        var intro: TextView
        var topText: TextView
        var bookCodeWrap: TextView
        var bookFav: TextView
        var textCntChapter: TextView
        var bar: TextView
        var category: TextView
        var imgWrap: CardView
        var bookTitle: String? = null
        var bookCode: String? = null
        var token = ""
        var bookContentsWrapC: LinearLayout
        var getuserinfo: JSONObject? = null

        init {
            image = itemView.findViewById(R.id.Img_Book)
            title = itemView.findViewById(R.id.Text_Title)
            intro = itemView.findViewById(R.id.Text_Intro)
            writer = itemView.findViewById(R.id.Text_Writer)
            topText = itemView.findViewById(R.id.TopText)
            favon = itemView.findViewById(R.id.FavON)
            favoff = itemView.findViewById(R.id.FavOff)
            imgWrap = itemView.findViewById(R.id.Img_Wrap)
            bookCodeWrap = itemView.findViewById(R.id.BookCodeText)
            bookFav = itemView.findViewById(R.id.TextBookFav)
            bookContentsWrapC = itemView.findViewById(R.id.BookContentsWrapC)
            bar = itemView.findViewById(R.id.Bar)
            category = itemView.findViewById(R.id.Category)
            textCntChapter = itemView.findViewById(R.id.Text_CntChapter)
            if (Config.getuserinfo() != null) {
                getuserinfo = Config.getuserinfo()
                val userInfo: JSONObject
                try {
                    userInfo = getuserinfo!!.getJSONObject("user")
                    token = userInfo.getString("token")
                } catch (e: JSONException) {
                    e.printStackTrace()
                    token = ""
                }
            }
            bookContentsWrapC.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "BookDetail")
                }
            }
            if (token != "") {
                imgWrap.setOnClickListener { v: View? ->
                    if (favoff.visibility == View.VISIBLE) {
                        favoff.visibility = View.GONE
                        favon.visibility = View.VISIBLE
                        bookTitle = title.text.toString()
                        bookCode = bookCodeWrap.text.toString()
                        Toast.makeText(itemView.context, "'$bookTitle'이(가) 선호작에 등록되었습니다",
                                Toast.LENGTH_SHORT).show()
                    } else {
                        favoff.visibility = View.VISIBLE
                        favon.visibility = View.GONE
                        bookTitle = title.text.toString()
                        bookCode = bookCodeWrap.text.toString()
                        Toast.makeText(itemView.context, "'$bookTitle'을(를) 선호작에서 해제하였습니다",
                                Toast.LENGTH_SHORT).show()
                    }
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        listener!!.onItemClick(v, pos, "FAV")
                    }
                }
            } else {
                imgWrap.setOnClickListener { v: View? -> Toast.makeText(itemView.context, "선호작을 등록하려면 로그인이 필요합니다", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.findViewById<View>(R.id.progressBar)
        }
    }

    fun setItems(items: List<MainBookListData?>?) {
        listData = items as ArrayList<PostListData?>?
    }

    fun getItem(position: Int): PostListData? {
        return listData!![position]
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
    }

    init {
        listData = items as ArrayList<PostListData?>?
    }
}