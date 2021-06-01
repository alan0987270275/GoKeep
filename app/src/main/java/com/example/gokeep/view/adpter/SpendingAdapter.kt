package com.example.gokeep.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gokeep.databinding.RecyclerItemSpendingBinding

class SpendingAdapter : RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder =
        SpendingViewHolder(
            RecyclerItemSpendingBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(holder: SpendingViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 5
    }

    class SpendingViewHolder(private val itemBinding: RecyclerItemSpendingBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind() = with(itemBinding) {
            itemView.apply {

            }
        }
    }

}