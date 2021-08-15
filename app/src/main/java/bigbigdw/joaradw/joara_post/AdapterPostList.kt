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
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
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
                .load(item!!.PostImg)
                .into(holder.post)
        holder.title.text = listData!![position]!!.title
        holder.categoryName.text = listData!![position]!!.categoryName
    }

    inner class PostListViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var post: ImageView
        var wrap: LinearLayout
        var title: TextView
        var categoryName: TextView

        init {
            post = itemView.findViewById(R.id.Post)
            wrap = itemView.findViewById(R.id.Wrap)
            title = itemView.findViewById(R.id.Title)
            categoryName = itemView.findViewById(R.id.Text_Intro)

            wrap.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "PostDetail")
                }
            }
        }
    }

    private class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.findViewById<View>(R.id.progressBar)
        }
    }

    fun setItems(items: List<PostListData?>?) {
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