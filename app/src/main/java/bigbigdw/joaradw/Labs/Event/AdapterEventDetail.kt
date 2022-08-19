package com.example.moavara.Soon.Event

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.databinding.ItemEventDetailBinding
import com.bumptech.glide.Glide
import com.example.moavara.Best.AdapterBestData
import com.example.moavara.Search.AnayzeData
import com.example.moavara.Search.EventDetailData
import kotlin.collections.ArrayList

class AdapterEventDetail(
    private var context: Context,
    private var holder: List<EventDetailData>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemEventDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoderEvent(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHoderEvent) {

            val item = this.holder[position]

            if(item.imgFile.isNotEmpty()){
                holder.binding.llayoutNull.visibility = View.GONE
                Glide.with(holder.itemView.context)
                    .load(item.imgFile)
                    .into(holder.binding.iView)
            } else {
                holder.binding.llayoutNull.visibility = View.VISIBLE
            }

            with(holder.binding){
                tviewTitle.text = item.title
                tviewDate.text = "${item.sDate} ~ ${item.wDate}"
                tviewCntRead.text = "오늘 조회수 : ${item.cntRead}"
            }

            val adapter = item.data?.let { AdapterBestData(it) }

            holder.binding.rview.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.binding.rview.adapter = adapter
        }
    }

    override fun getItemCount(): Int {
        return holder.size
    }

    inner class ViewHoderEvent internal constructor(val binding: ItemEventDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding){
                llayoutWrap.setOnClickListener { v: View? ->
                    val pos = adapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        listener?.onItemClick(v, pos)
                    }
                }
            }
        }


    }

    fun getItem(position: Int): EventDetailData {
        return holder[position]
    }

}