package com.example.gokeep.view.adpter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gokeep.data.model.CategoryViewData
import com.example.gokeep.databinding.RecyclerItemCategoryBinding

class CategoryAdapter(val data: List<CategoryViewData>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val TAG = CategoryAdapter::javaClass.name
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder(
            RecyclerItemCategoryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            setOnclick(position)
        }
    }

    override fun getItemCount() = data.size

    private fun setOnclick(position: Int) {
        for(i in data.indices) {
            data[i].isSelected = false
        }
        data[position].isSelected = true
        notifyDataSetChanged()
    }

    fun getSelected() = data.indexOfFirst{ it.isSelected }

    class CategoryViewHolder(private val itemBinding: RecyclerItemCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: CategoryViewData) = with(itemBinding) {
            itemView.apply {
                Glide.with(categoryImageView.context)
                    .load(data.imageId)
                    .into(categoryImageView)
                categoryTextView.text = data.title
                container.alpha = if(data.isSelected) 1F else 0.5F
            }
        }
    }
}