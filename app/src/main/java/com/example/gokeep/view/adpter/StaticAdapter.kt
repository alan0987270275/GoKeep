package com.example.gokeep.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gokeep.data.model.SpendingStaticData
import com.example.gokeep.databinding.RecyclerItemStaticBinding

class StaticAdapter(private val staticList: ArrayList<SpendingStaticData>,
                    private var maxSpending: Int = 0) : RecyclerView.Adapter<StaticAdapter.StaticViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticViewHolder =
        StaticViewHolder(
            RecyclerItemStaticBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(holder: StaticViewHolder, position: Int) {
        holder.bind(staticList[position], maxSpending)
    }

    override fun getItemCount() = staticList.size

    fun addAllItem(list: List<SpendingStaticData>) {
        staticList.clear()
        staticList.addAll(list)
        maxSpending = list.maxBy{ it.spending }?.spending ?: 0
    }

    class StaticViewHolder(private val itemBinding: RecyclerItemStaticBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: SpendingStaticData, maxSpending: Int) = with(itemBinding) {
            itemView.apply {
                monthTitleTextView.text = data.monthTitle
                val progress = (data.spending.toFloat() / maxSpending.toFloat()) * 100
                progressView.progress = progress
            }
        }
    }
}