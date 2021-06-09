package com.example.gokeep.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gokeep.databinding.RecyclerItemStaticTagBinding

class StaticTagAdapter(): RecyclerView.Adapter<StaticTagAdapter.StaticTagViewHolder>() {

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
        holder.bind()
        holder.itemView.setOnClickListener{
            onItemClickListener?.onItemClick(position)
        }
    }

    override fun getItemCount() = 8

    fun setOnItemClickListener(_OnItemClickListener: OnItemClickListener) {
        onItemClickListener = _OnItemClickListener
    }

    class StaticTagViewHolder(private val itemBinding: RecyclerItemStaticTagBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind() = with(itemBinding) {
            itemView.apply {

            }
        }
    }
}