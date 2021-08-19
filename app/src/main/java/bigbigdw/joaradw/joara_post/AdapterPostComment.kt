package bigbigdw.joaradw.joara_post

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.R
import com.bumptech.glide.Glide
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import java.util.*


class AdapterPostComment(private val mContext: Context, items: List<PostCommentData?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listData: ArrayList<PostCommentData?>?
    private var checkedPosition = -10
    private val viewBinderHelper = ViewBinderHelper()

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_comment, parent, false)
        return PostCommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostCommentViewHolder) {

            val item = listData!![position]
            Glide.with(holder.itemView.context)
                .load(item!!.commentImg)
                .into(holder.iCommentImg)

            holder.tCommentDate.text = listData!![position]!!.commentDate
            holder.tComment.text = listData!![position]!!.comment
            holder.Comment_EditText.setText(listData!![position]!!.comment)
            holder.tCommentID.text = listData!![position]!!.commentId
            holder.Comment_EditText.setText(listData!![position]!!.comment)
            holder.tUserID.text = listData!![position]!!.userId

            holder.Swipe_Edit.setOnClickListener { v: View? ->
                val pos = holder.adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "EDIT")
                    viewBinderHelper.setOpenOnlyOne(true)
                    holder.swipelayout.close(true)
                    holder.tComment.visibility = View.GONE
                    holder.Comment_EditText.visibility = View.VISIBLE
                    holder.Comment_CancelBtn.visibility = View.VISIBLE
                    holder.Comment_ApplyBtn.visibility = View.VISIBLE

                    // 커서 위치 마지막으로 이동
                    holder.Comment_EditText.setSelection(holder.Comment_EditText.text.length)
                    //키보드 올림
                    val inputMethodManager =
                        mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    holder.Comment_EditText.requestFocus()
                    inputMethodManager.showSoftInput(holder.Comment_EditText, 0)

                    Objects.requireNonNull(holder.Comment_EditText!!)?.addTextChangedListener(object :
                        TextWatcher {
                        override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                            Log.d("holder.Comment_EditText", "beforeTextChanged")
                        }

                        override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                            holder.tComment.text = text
                            listData!![holder.getAdapterPosition()]!!.comment = text.toString()
                        }

                        override fun afterTextChanged(s: Editable) {
                            Log.d("holder.Comment_EditText", "onTextChanged")
                        }
                    })

                    if (checkedPosition != holder.getAdapterPosition()) {
                        notifyItemChanged(checkedPosition)
                        checkedPosition = holder.getAdapterPosition()
                    }
                }
            }

            // 본인이 작성한 코멘트 인지 확인
            if (mContext.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
                    .getString("LOGIN_MEMBERID", "").equals(listData!![position]!!.userId)
            ) {

                viewBinderHelper.setOpenOnlyOne(true)
                viewBinderHelper.bind(
                    holder.swipelayout,
                    java.lang.String.valueOf(listData?.get(position)?.commentId)
                )
                viewBinderHelper.closeLayout(java.lang.String.valueOf(listData?.get(position)?.commentId))

                holder.Swipe_Del.visibility = View.VISIBLE
                holder.Swipe_Edit.visibility = View.VISIBLE
                val textString = listData!![position]!!.commentWriter + "(나)"
                holder.tCommentWriter.text = textString
                holder.tCommentWriter.setTypeface(null, Typeface.BOLD);

                if (checkedPosition == -1) {
                    holder.tComment.visibility = View.VISIBLE
                    holder.Comment_EditText.visibility = View.GONE
                    holder.Comment_CancelBtn.visibility = View.GONE
                    holder.Comment_ApplyBtn.visibility = View.GONE
                } else {
                    if (checkedPosition == holder.getAdapterPosition()) {
                        holder.tComment.visibility = View.GONE
                        holder.Comment_EditText.visibility = View.VISIBLE
                        holder.Comment_CancelBtn.visibility = View.VISIBLE
                        holder.Comment_ApplyBtn.visibility = View.VISIBLE
                    } else {
                        holder.tComment.visibility = View.VISIBLE
                        holder.Comment_EditText.visibility = View.GONE
                        holder.Comment_CancelBtn.visibility = View.GONE
                        holder.Comment_ApplyBtn.visibility = View.GONE
                    }
                }
            } else {
                holder.tCommentWriter.text = listData!![position]!!.commentWriter
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

    inner class PostCommentViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var iCommentImg: ImageView
        var tCommentWriter: TextView
        var tCommentDate: TextView
        var tComment: TextView
        var tCommentID: TextView
        var tUserID: TextView

        var Comment_CancelBtn: TextView
        var Comment_ApplyBtn: TextView
        var Comment_EditText: EditText
        var swipelayout: SwipeRevealLayout
        var Swipe_Del: TextView
        var Swipe_Edit: TextView

        init {
            iCommentImg = itemView.findViewById(R.id.Comment_Img)
            tCommentWriter = itemView.findViewById(R.id.Comment_Writer)
            tCommentDate = itemView.findViewById(R.id.Comment_Date)
            tComment = itemView.findViewById(R.id.Comment)
            tCommentID = itemView.findViewById(R.id.Comment_ID)
            tUserID = itemView.findViewById(R.id.User_ID)

            Comment_CancelBtn = itemView.findViewById(R.id.Comment_CancelBtn)
            Comment_ApplyBtn = itemView.findViewById(R.id.Comment_ApplyBtn)
            Comment_EditText = itemView.findViewById(R.id.Comment_EditText)
            swipelayout = itemView.findViewById(R.id.swipelayout)
            Swipe_Del = itemView.findViewById(R.id.Swipe_Del)
            Swipe_Edit = itemView.findViewById(R.id.Swipe_Edit)
            Swipe_Edit.nextFocusDownId = R.id.Comment_EditText;

            Comment_CancelBtn.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "CANCEL")
                    tComment.visibility = View.VISIBLE
                    Comment_EditText.visibility = View.GONE
                    Comment_CancelBtn.visibility = View.GONE
                    Comment_ApplyBtn.visibility = View.GONE
                    //키보드 숨김
                    val inputMethodManager = mContext!!.getSystemService(
                        AppCompatActivity.INPUT_METHOD_SERVICE
                    ) as InputMethodManager

                    inputMethodManager.hideSoftInputFromWindow(
                        v!!.windowToken,
                        0
                    )
                }
            }

            Swipe_Del.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "DELETE")
                    swipelayout.close(true)
                    viewBinderHelper.setOpenOnlyOne(true)
                }
            }

            Comment_ApplyBtn.setOnClickListener { v: View? ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(v, pos, "APPLY")
                    checkedPosition = -10
                    tComment.visibility = View.VISIBLE
                    Comment_EditText.visibility = View.GONE
                    Comment_CancelBtn.visibility = View.GONE
                    Comment_ApplyBtn.visibility = View.GONE
                    //키보드 숨김
                    val inputMethodManager = mContext!!.getSystemService(
                        AppCompatActivity.INPUT_METHOD_SERVICE
                    ) as InputMethodManager

                    inputMethodManager.hideSoftInputFromWindow(
                        v!!.windowToken,
                        0
                    )
                }
            }
        }
    }

    fun setItems(items: List<PostCommentData?>?) {
        listData = items as ArrayList<PostCommentData?>?
    }

    fun getItem(position: Int): PostCommentData? {
        return listData!![position]
    }

    fun addItem(data: PostCommentData?) {
        listData!!.add(data)
    }

    fun deleteItem(position: Int) {
        listData!!.removeAt(position)
        notifyItemInserted(position)
        notifyDataSetChanged()
    }

    fun editItem(items: PostCommentData, position: Int) {
        listData!!.set(position, items)
        notifyItemChanged(position)
    }

    fun changeItem(items: PostCommentData, position: Int) {
        listData!!.add(position, items)
        notifyItemInserted(position)
    }

    companion object {
        private const val VIEW_TYPE_ITEM = 0
    }

    init {
        listData = items as ArrayList<PostCommentData?>?
    }
}