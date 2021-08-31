package bigbigdw.joaradw.book

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import bigbigdw.joaradw.model.BookInfo
import com.bumptech.glide.Glide
import java.util.ArrayList


class AdapterBookListBest(private val mContext: Context, items: List<BookListDataBest?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<BookListDataBest?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booklist_best, parent, false)
        return MainBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainBookViewHolder) {

            val item = listData!![position]

            Glide.with(holder.itemView.context)
                .load(item!!.bookImg)
                .into(holder.image)



            if (position == 0) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_1)
            } else if (position == 1) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_2)
            } else if (position == 2) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_3)
            } else if (position == 3) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_4)
            } else if (position == 4) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_5)
            } else if (position == 5) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_6)
            } else if (position == 6) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_7)
            } else if (position == 7) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_8)
            } else if (position == 8) {
                holder.bestRankImage.setImageResource(R.drawable.icon_best_9)
            } else {
                Log.d("bestRankImage","NO_IMAGE")
            }

            holder.title.text = listData!![position]!!.title
            holder.writer.text = listData!![position]!!.writer
            holder.intro.text = listData!![position]!!.intro

            holder.bestViewCount.text = listData!![position]!!.cntPageRead
            holder.bestFav.text = listData!![position]!!.cntFavorite
            holder.bestRecommend.text = listData!![position]!!.cntRecom
            holder.bookCode.text = listData!![position]!!.bookCode
            holder.category.text = listData!![position]!!.bookCategory
            holder.textCntChapter.text = listData!![position]!!.cntChapter

            val topText: TextView = holder.topText
            val bookLabel: LinearLayout = holder.bookLabel

            if (listData!![position]!!.isNobless == "TRUE" && listData!![position]!!.isAdult == "FALSE") {
                textSetting(topText, bookLabel, R.string.NOBLESS, -0x555a3b00)
            } else if (listData!![position]!!.isPremium == "TRUE" && listData!![position]!!.isAdult == "FALSE") {
                textSetting(topText, bookLabel, R.string.PREMIUM, -0x55b68e11)
            } else if (listData!![position]!!.isFinish == "TRUE" && listData!![position]!!.isAdult == "FALSE") {
                textSetting(topText, bookLabel, R.string.FINISH, -0x555a3b00)
            } else if (listData!![position]!!.isNobless == "TRUE" && listData!![position]!!.isAdult == "TRUE") {
                textSetting(topText, bookLabel, R.string.ADULT_NOBLESS, -0x550bbcca)
            } else if (listData!![position]!!.isPremium == "TRUE" && listData!![position]!!.isAdult == "TRUE") {
                textSetting(topText, bookLabel, R.string.ADULT_PREMIUM, -0x550bbcca)
            } else if (listData!![position]!!.isFinish == "TRUE" && listData!![position]!!.isAdult == "TRUE") {
                textSetting(topText, bookLabel, R.string.ADULT_FINISH, -0x550bbcca)
            }

            if (listData!![position]!!.isFav == "TRUE") {
                holder.favwrap.visibility = View.VISIBLE
            } else {
                holder.favwrap.visibility = View.GONE
            }
        }
    }

    private fun textSetting(topText: TextView, bookLabel: LinearLayout, title: Int, color: Int) {
        topText.setText(title)
        bookLabel.setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView
        var bestRankImage: ImageView
        var title: TextView
        var writer: TextView
        var intro: TextView
        var bestViewCount: TextView
        var bestFav: TextView
        var bestRecommend: TextView
        var bookCode: TextView
        var topText: TextView
        var bar: TextView
        var category: TextView
        var textCntChapter: TextView
        var bestWrap: LinearLayout
        var favwrap: LinearLayout
        var bookLabel: LinearLayout

        init {
            bestRankImage = itemView.findViewById(R.id.BestRankImg)
            image = itemView.findViewById(R.id.Img_BookBest)
            title = itemView.findViewById(R.id.Text_Title_Best)
            writer = itemView.findViewById(R.id.Text_Writer_Best)
            topText = itemView.findViewById(R.id.TopText)
            bar = itemView.findViewById(R.id.Bar)
            category = itemView.findViewById(R.id.Category)
            bestViewCount = itemView.findViewById(R.id.Text_BestViewed)
            bestFav = itemView.findViewById(R.id.Text_BestFav)
            bestRecommend = itemView.findViewById(R.id.Text_BestRecommend)
            bestWrap = itemView.findViewById(R.id.BestWrap)
            favwrap = itemView.findViewById(R.id.FavWrap)
            intro = itemView.findViewById(R.id.Text_Intro_Best)
            bookCode = itemView.findViewById(R.id.BookCodeText)
            bookLabel = itemView.findViewById(R.id.BookLabel)
            textCntChapter = itemView.findViewById(R.id.Text_CntChapter)

            val token = mContext.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
                .getString("TOKEN", "")

            bestWrap.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "BookDetail")
                }
            }

            if (token != "") {
                image.setOnClickListener { v: View? ->
                    if (favwrap.visibility == View.GONE) {
                        favwrap.visibility = View.VISIBLE
                    } else {
                        favwrap.setVisibility(View.GONE)
                    }
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        listener!!.onItemClick(v, pos, "FAV")
                    }
                }
            } else {
                image.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "선호작을 등록하려면 로그인이 필요합니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    fun setItems(items: List<BookListDataBest?>?) {
        listData = items as ArrayList<BookListDataBest?>?
    }

    fun getItem(position: Int): BookListDataBest? {
        return listData!![position]
    }

    init {
        listData = items as ArrayList<BookListDataBest?>?
    }
}