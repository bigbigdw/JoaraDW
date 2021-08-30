package bigbigdw.joaradw.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.main.OLD_MainBookListData
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.util.ArrayList

class AdapterBookListC(private val mContext: Context, items: List<BookListDataC?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<BookListDataC?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booklist_c, parent, false)
        return MainBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainBookViewHolder) {

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

            val topText: TextView = holder.topText
            val bar: TextView = holder.bar
            val category: TextView = holder.category

            if (listData!![position]!!.isNobless == "TRUE" && listData!![position]!!.isAdult == "FALSE") {
                textSetting(R.string.NOBLESS, -0x555a3b00, topText, bar, category)
            } else if (listData!![position]!!.isPremium == "TRUE" && listData!![position]!!.isAdult == "FALSE") {
                textSetting(R.string.PREMIUM, -0x55b68e11, topText, bar, category)
            } else if (listData!![position]!!.isFinish == "TRUE" && listData!![position]!!.isAdult == "FALSE") {
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
    }

    fun textSetting(title: Int, color: Int, topText: TextView, bar: TextView, category: TextView) {
        topText.setText(title)
        topText.setTextColor(color)
        bar.setTextColor(color)
        category.setTextColor(color)
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        var bookContentsWrapC: LinearLayout

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

            val token = mContext.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

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
                    } else {
                        favoff.visibility = View.VISIBLE
                        favon.visibility = View.GONE
                    }
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        listener!!.onItemClick(v, pos, "FAV")
                    }
                }
            } else {
                imgWrap.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "선호작을 등록하려면 로그인이 필요합니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun setItems(items: List<BookListDataC?>?) {
        listData = items as ArrayList<BookListDataC?>?
    }

    fun getItem(position: Int): BookListDataC? {
        return listData!![position]
    }

    init {
        listData = items as ArrayList<BookListDataC?>?
    }
}