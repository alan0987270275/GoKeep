package com.example.gokeep.view.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gokeep.R
import com.example.gokeep.data.model.StaticMonthlyTagData
import com.example.gokeep.databinding.RecyclerItemStaticTagBinding
import com.example.gokeep.util.ColorHelper

class StaticTagAdapter(
    private var staticMonthlyTagDataList: ArrayList<StaticMonthlyTagData>
) :
    RecyclerView.Adapter<StaticTagAdapter.StaticTagViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticTagViewHolder =
        StaticTagViewHolder(
            RecyclerItemStaticTagBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(holder: StaticTagViewHolder, position: Int) {
        holder.bind(staticMonthlyTagDataList[position])
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }

    override fun getItemCount() = staticMonthlyTagDataList.size

    fun addAll(list: ArrayList<StaticMonthlyTagData>) {
        staticMonthlyTagDataList = list
    }

    fun setOnItemClickListener(_OnItemClickListener: OnItemClickListener) {
        onItemClickListener = _OnItemClickListener
    }

    fun getData() = staticMonthlyTagDataList


    class StaticTagViewHolder(private val itemBinding: RecyclerItemStaticTagBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: StaticMonthlyTagData) = with(itemBinding) {
            itemView.apply {
                tagContainer.setCardBackgroundColor(
                    ColorHelper.getStaticColorMap(context, data.tag) ?: ContextCompat.getColor(
                        context,
                        R.color.while_gray
                    )
                )
                tagTextView.text = data.tag

            }
        }
    }
}