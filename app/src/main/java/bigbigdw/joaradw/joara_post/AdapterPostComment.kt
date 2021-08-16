package bigbigdw.joaradw.joara_post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import java.util.ArrayList


class AdapterPostComment(items: List<PostCommentData?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<PostCommentData?>?

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_comment, parent, false)
            PostCommentViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.spinner, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostCommentViewHolder) {
            populateItemRows(holder, position)

            holder.itemView.setOnClickListener {
                listener?.onItemClick(it, position)
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

    private fun populateItemRows(holder: PostCommentViewHolder, position: Int) {
        val item = listData!![position]
        Glide.with(holder.itemView.context)
            .load(item!!.commentImg)
            .into(holder.iCommentImg)

        holder.tCommentWriter.text = listData!![position]!!.commentWriter
        holder.tCommentDate.text = listData!![position]!!.commentDate
        holder.tComment.text = listData!![position]!!.comment
    }

    inner class PostCommentViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iCommentImg: ImageView
        var tCommentWriter: TextView
        var tCommentDate: TextView
        var tComment: TextView

        init {
            iCommentImg = itemView.findViewById(R.id.Comment_Img)
            tCommentWriter = itemView.findViewById(R.id.Comment_Writer)
            tCommentDate = itemView.findViewById(R.id.Comment_Date)
            tComment = itemView.findViewById(R.id.Comment)

//            wrap.setOnClickListener { v: View? ->
//                val pos = adapterPosition
//                if (pos != RecyclerView.NO_POSITION) {
//                    listener!!.onItemClick(v, pos)
//                }
//            }
        }
    }

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.findViewById<View>(R.id.progressBar)
        }
    }

    fun setItems(items: List<PostCommentData?>?) {
        listData = items as ArrayList<PostCommentData?>?
    }

    fun getItem(position: Int): PostCommentData? {
        return listData!![position]
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
    }

    init {
        listData = items as ArrayList<PostCommentData?>?
    }
}