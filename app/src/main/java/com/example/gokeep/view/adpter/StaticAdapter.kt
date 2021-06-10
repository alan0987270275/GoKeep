package com.example.gokeep.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gokeep.data.model.StaticMonthlySumData
import com.example.gokeep.databinding.RecyclerItemStaticBinding
import kotlinx.android.synthetic.main.recycler_item_static.view.*

class StaticAdapter(private val staticMonthlySumList: ArrayList<StaticMonthlySumData>,
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
        holder.bind(staticMonthlySumList[position], maxSpending)
        holder.itemView.progressView.setOnProgressClickListener {
            onclickListener(position)
        }
    }

    override fun getItemCount() = staticMonthlySumList.size

    private fun onclickListener(position: Int) {
        for(i in staticMonthlySumList.indices) {
            staticMonthlySumList[i].isSelected = false
        }
        staticMonthlySumList[position].isSelected = true
        notifyDataSetChanged()
    }

    fun addAllItem(list: List<StaticMonthlySumData>) {
        staticMonthlySumList.clear()
        staticMonthlySumList.addAll(list)
        maxSpending = list.maxBy{ it.sumSpending }?.sumSpending ?: 0
    }

    class StaticViewHolder(private val itemBinding: RecyclerItemStaticBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(monthlySumData: StaticMonthlySumData, maxSpending: Int) = with(itemBinding) {
            itemView.apply {
                monthTitleTextView.text = monthlySumData.monthTitle
                val progress = (monthlySumData.sumSpending.toFloat() / maxSpending.toFloat()) * 100
                progressView.progress = progress + 10
                progressView.alpha = if(monthlySumData.isSelected) 1F else 0.5F
            }
        }
    }
}