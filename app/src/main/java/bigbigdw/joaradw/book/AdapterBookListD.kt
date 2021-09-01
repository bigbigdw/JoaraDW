package bigbigdw.joaradw.book

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import java.util.ArrayList

class AdapterBookListD(private val mContext: Context, items: List<BookListDataABD?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<BookListDataABD?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_booklist_d, parent, false)
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

            if(listData!![position]!!.isAdult.equals("TRUE")){
                holder.UnderCoverText.text = "성인"
                holder.BookImgUnderWrap.visibility = View.VISIBLE
                holder.UnderCoverText.setTextColor(Color.parseColor("#FFFF0000"))
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var image: ImageView
        var title: TextView
        var writer: TextView
        var bookCode: TextView
        var imgWrap: CardView
        var BookImgUnderWrap: ConstraintLayout
        var UnderCoverText: TextView

        init {
            image = itemView.findViewById(R.id.Img_BookD)
            title = itemView.findViewById(R.id.Text_TitleD)
            writer = itemView.findViewById(R.id.Text_WriterD)
            bookCode = itemView.findViewById(R.id.BookCodeText)
            imgWrap = itemView.findViewById(R.id.Img_Wrap)
            BookImgUnderWrap = itemView.findViewById(R.id.BookImgUnderWrap)
            UnderCoverText = itemView.findViewById(R.id.UnderCoverText)

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