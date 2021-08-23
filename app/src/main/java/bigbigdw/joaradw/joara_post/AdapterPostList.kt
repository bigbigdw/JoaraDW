package bigbigdw.joaradw.joara_post

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import android.widget.TextView
import android.widget.LinearLayout
import java.util.ArrayList

class AdapterPostList(items: List<PostListData?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<PostListData?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        return  PostListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostListViewHolder) {
            val item = listData!![position]
            Glide.with(holder.itemView.context)
                .load(item!!.postImg)
                .into(holder.post)
            holder.title.text = listData!![position]!!.title
            holder.categoryName.text = listData!![position]!!.categoryName
            holder.cntRead.text = listData!![position]!!.cntRead
            holder.cntRecom.text = listData!![position]!!.cntRecom
            holder.cntComment.text = listData!![position]!!.cntComment
            holder.postId.text = listData!![position]!!.postId
        }
    }

    override fun getItemCount(): Int {
        return if (listData == null) 0 else listData!!.size
    }

    inner class PostListViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var post: ImageView
        var wrap: LinearLayout
        var title: TextView
        var categoryName: TextView
        var cntRead: TextView
        var cntRecom: TextView
        var cntComment: TextView
        var postId: TextView

        init {
            post = itemView.findViewById(R.id.Post)
            wrap = itemView.findViewById(R.id.Wrap)
            title = itemView.findViewById(R.id.Title)
            categoryName = itemView.findViewById(R.id.CategoryName)
            postId = itemView.findViewById(R.id.PostID)
            cntRead = itemView.findViewById(R.id.cntRead)
            cntRecom = itemView.findViewById(R.id.cntRecom)
            cntComment = itemView.findViewById(R.id.cntComment)

            wrap.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos)
                }
            }
        }
    }

    fun setItems(items: List<PostListData?>?) {
        listData = items as ArrayList<PostListData?>?
    }

    fun getItem(position: Int): PostListData? {
        return listData!![position]
    }

    init {
        listData = items as ArrayList<PostListData?>?
    }
}