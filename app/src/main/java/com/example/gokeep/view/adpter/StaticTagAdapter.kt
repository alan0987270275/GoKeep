package com.example.gokeep.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gokeep.databinding.RecyclerItemStaticTagBinding

class StaticTagAdapter(): RecyclerView.Adapter<StaticTagAdapter.StaticTagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticTagViewHolder =
        StaticTagViewHolder(
            RecyclerItemStaticTagBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(holder: StaticTagViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 8

    class StaticTagViewHolder(private val itemBinding: RecyclerItemStaticTagBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind() = with(itemBinding) {
            itemView.apply {

            }
        }
    }
}