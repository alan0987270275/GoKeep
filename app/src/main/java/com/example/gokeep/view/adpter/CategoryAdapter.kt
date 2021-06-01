package com.example.gokeep.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gokeep.databinding.RecyclerItemCategoryBinding
import com.example.gokeep.databinding.RecyclerItemTutorialBinding
import com.example.gokeep.view.ui.fragment.HomeFragment

class CategoryAdapter(val data: List<HomeFragment.CategoryViewData>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

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
        holder.itemView.alpha = if(position == 0) 1F else 0.5F
        holder.itemView.setOnClickListener {
            holder.itemView.alpha = 1F
        }
    }

    override fun getItemCount() = data.size

    class CategoryViewHolder(private val itemBinding: RecyclerItemCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: HomeFragment.CategoryViewData) = with(itemBinding) {
            itemView.apply {
                Glide.with(categoryImageView.context)
                    .load(data.imageId)
                    .into(categoryImageView)
                categoryTextView.text = data.title
            }
        }
    }
}