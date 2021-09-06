package bigbigdw.joaradw.book

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import android.widget.TextView
import java.util.ArrayList

class AdapterBookListHistory(items: List<BookListDataABD?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listData: ArrayList<BookListDataABD?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_list_history, parent, false)
        return MainBookListViewHolderHistory(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainBookListViewHolderHistory) {
            val item = listData!![position]

            Glide.with(holder.itemView.context)
                .load(item!!.bookImg)
                .into(holder.image)

            holder.title.text = listData!![position]!!.title
            holder.writer.text = listData!![position]!!.writer
            holder.readCount.text = listData!![position]!!.historySortno
        }
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookListViewHolderHistory internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        var title: TextView
        var writer: TextView
        var readCount: TextView
        var Img_Wrap : LinearLayout

        init {
            image = itemView.findViewById(R.id.Img_BookFav)
            title = itemView.findViewById(R.id.Text_TitleFav)
            writer = itemView.findViewById(R.id.Text_WriterFav)
            readCount = itemView.findViewById(R.id.Text_ReadCount)
            Img_Wrap = itemView.findViewById(R.id.Img_Wrap)

            Img_Wrap.setOnClickListener { v: View? ->
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