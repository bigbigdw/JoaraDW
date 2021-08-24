package bigbigdw.joaradw.book

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import java.util.ArrayList

class AdapterBookEventList(private val mContext: Context, items: List<BookEventListData?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<BookEventListData?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_eventlist, parent, false)
        return MainBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainBookViewHolder) {

            val item = listData!![position]
            Glide.with(holder.itemView.context)
                .load(item!!.eventImg)
                .into(holder.image)

            holder.eventTitle.text = listData!![position]!!.title
            holder.eventText.text = listData!![position]!!.content
            holder.eventID.text = listData!![position]!!.idx
            holder.eventType.text = listData!![position]!!.themeSubTitle
            holder.eventDate.text = listData!![position]!!.enddate

        }
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class MainBookViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView
        var eventText: TextView
        var eventTitle: TextView
        var eventID: TextView
        var eventType: TextView
        var eventDate: TextView

        init {
            image = itemView.findViewById(R.id.Img_Book)
            eventText = itemView.findViewById(R.id.EventText)
            eventTitle = itemView.findViewById(R.id.Event_Title)
            eventID = itemView.findViewById(R.id.EventID)
            eventType = itemView.findViewById(R.id.Event_Type)
            eventDate = itemView.findViewById(R.id.Event_Date)
        }
    }

    fun setItems(items: List<BookEventListData?>?) {
        listData = items as ArrayList<BookEventListData?>?
    }

    fun getItem(position: Int): BookEventListData? {
        return listData!![position]
    }

    init {
        listData = items as ArrayList<BookEventListData?>?
    }
}