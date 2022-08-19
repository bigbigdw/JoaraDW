package com.example.moavara.Best

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bigbigdw.joaradw.databinding.ItemBestDetailDataBinding
import com.example.moavara.Search.AnayzeData

class AdapterBestData(
    private var items: ArrayList<AnayzeData>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View?, position: Int, value: String?)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemBestDetailDataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Holder) {

            val data = items[position]

            with(holder.binding) {

                if(position > 0){
                    tviewData.text = data.date

                    if(items[position].cntRead.toInt() - items[position - 1].cntRead.toInt() > 0){
                        tviewData1.text = "_${data.cntRead}(${(items[position].cntRead.toInt() - items[position - 1].cntRead.toInt()).toString().replace(".0", "")})"
                        tviewData1.setTextColor(Color.parseColor("#02BC77"))
                    } else if(items[position].cntRead.toInt() - items[position - 1].cntRead.toInt() == 0){
                        tviewData1.setTextColor(Color.parseColor("#ffffff"))
                    } else {
                        tviewData1.text = "-${data.cntRead}(${(items[position].cntRead.toInt() - items[position - 1].cntRead.toInt()).toString().replace(".0", "")})"
                        tviewData1.setTextColor(Color.parseColor("#FF2366"))
                    }
                } else {
                    tviewData.text = data.date
                    tviewData1.text = data.cntRead
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder internal constructor(val binding: ItemBestDetailDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {}

    }

    fun getItem(position: Int): AnayzeData {
        return items[position]
    }

}