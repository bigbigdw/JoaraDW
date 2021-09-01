package bigbigdw.joaradw.book

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import java.util.ArrayList

class AdapterBookListA(private val mContext: Context, items: List<BookListDataABD?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<BookListDataABD?>?
    var f = "FALSE"
    var t = "True"

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booklist_a, parent, false)
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
            holder.bookCode.text = listData!![position]!!.bookCode

            if(listData!![position]!!.listType.equals("favoriteList")){
                holder.underCover.visibility = View.VISIBLE
            } else {
                holder.underCover.visibility = View.GONE
            }

            if(!listData!![position]!!.listType.equals("favoriteList")){
                if(listData!![position]!!.isAdult.equals("TRUE")){
                    holder.underCoverText.text = "성인"
                    holder.underCover.visibility = View.VISIBLE
                    holder.underCoverText.setTextColor(Color.parseColor("#FFFF0000"))
                }
            } else {
                holder.underCoverText.text = listData!![position]!!.historySortno + "편"
            }
            
        }
    }


    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView
        var title: TextView
        var writer: TextView
        var underCoverText: TextView
        var bookCode: TextView
        var underCover: ConstraintLayout
        var imgWrap: CardView

        init {
            image = itemView.findViewById(R.id.Img_BookA)
            title = itemView.findViewById(R.id.Text_TitleA)
            writer = itemView.findViewById(R.id.Text_WriterA)
            underCover = itemView.findViewById(R.id.BookImgUnderWrap)
            underCoverText = itemView.findViewById(R.id.UnderCoverText)
            bookCode = itemView.findViewById(R.id.BookCodeText)
            imgWrap = itemView.findViewById(R.id.Img_Wrap)

            imgWrap.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "BookDetail")
                }
            }
        }
    }

    fun setItems(items: List<BookListDataABD?>?) {
        listData = items as ArrayList<BookListDataABD?>?
    }

    fun getItem(position: Int): BookListDataABD? {
        return listData!![position]
    }

    init {
        listData = items as ArrayList<BookListDataABD?>?
    }
}