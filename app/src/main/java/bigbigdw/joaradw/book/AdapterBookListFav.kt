package bigbigdw.joaradw.book

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.Toast
import java.util.ArrayList

class AdapterBookListFav(mContext: Context, items: List<BookListDataFav?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<BookListDataFav?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booklist_fav, parent, false)
        return  MainBookListViewHolderNew(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainBookListViewHolderNew) {
            val item = listData!![position]
            Glide.with(holder.itemView.context)
                .load(item!!.bookImg)
                .into(holder.image)
            holder.title.text = listData!![position]!!.title
            holder.writer.text = listData!![position]!!.writer
            holder.intro.text = listData!![position]!!.intro
            holder.textViewBookCode.text = listData!![position]!!.bookCode
            holder.textCntChapter.text = listData!![position]!!.cntChapter
            if (listData!![position]!!.isFav == "TRUE") {
                holder.favon.visibility = View.VISIBLE
                holder.favoff.visibility = View.GONE
            } else {
                holder.favoff.visibility = View.VISIBLE
                holder.favon.visibility = View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val viewTypeLoading = 1
        return if (listData!![position] == null) viewTypeLoading else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookListViewHolderNew internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView
        var writer: TextView
        var intro: TextView
        var favon: ImageView
        var favoff: ImageView
        var favWrap: LinearLayout
        var imgWrap: LinearLayout
        var booktitle: String? = null
        var bookCode: String? = null
        var textViewBookCode: TextView
        var textCntChapter: TextView

        init {
            image = itemView.findViewById(R.id.Img_BookFav)
            title = itemView.findViewById(R.id.Text_TitleFav)
            intro = itemView.findViewById(R.id.Text_IntroFav)
            writer = itemView.findViewById(R.id.Text_Writer)
            favon = itemView.findViewById(R.id.Icon_FavOn)
            favoff = itemView.findViewById(R.id.Icon_FavOff)
            favWrap = itemView.findViewById(R.id.Fav_Wrap)
            imgWrap = itemView.findViewById(R.id.Img_Wrap)
            textViewBookCode = itemView.findViewById(R.id.BookCodeText)
            textCntChapter = itemView.findViewById(R.id.Text_CntChapter)
            imgWrap.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "BookDetail")
                }
            }
            favWrap.setOnClickListener { v: View? ->
                if (favoff.visibility == View.VISIBLE) {
                    favoff.visibility = View.GONE
                    favon.visibility = View.VISIBLE
                    booktitle = title.text.toString()
                    bookCode = textViewBookCode.text.toString()
                    Toast.makeText(
                        itemView.context, "'$booktitle'이(가) 선호작에 등록되었습니다",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    favoff.visibility = View.VISIBLE
                    favon.visibility = View.GONE
                    booktitle = title.text.toString()
                    bookCode = textViewBookCode.text.toString()
                    Toast.makeText(
                        itemView.context, "'$booktitle'을(를) 선호작에서 해제하였습니다",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "FAV")
                }
            }
        }
    }

    fun setItems(items: List<BookListDataFav?>?) {
        listData = items as ArrayList<BookListDataFav?>?
    }

    fun getItem(position: Int): BookListDataFav? {
        return listData!![position]
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
    }

    init {
        listData = items as ArrayList<BookListDataFav?>?
    }
}