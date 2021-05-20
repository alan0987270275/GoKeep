package com.example.gokeep.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gokeep.databinding.RecyclerItemTutorialBinding
import com.example.gokeep.view.TutorialActivity
import cz.intik.overflowindicator.OverflowPagerIndicator

class TutorialAdapter(private val data: ArrayList<TutorialActivity.TutorialViewData>,
                      private val recyclerView: RecyclerView,
                      private val viewOverflowPagerIndicator: OverflowPagerIndicator): RecyclerView.Adapter<TutorialAdapter.TutorialViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TutorialViewHolder(RecyclerItemTutorialBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        /**
        Bind data to recyclerView
         */
        holder.bind(data[position], recyclerView, viewOverflowPagerIndicator)
    }

    override fun getItemCount() = data.size

    fun addAllItem(data: List<TutorialActivity.TutorialViewData>) {
        this.data.apply {
            clear()
            addAll(data)
        }
    }

    class TutorialViewHolder(private val itemBinding: RecyclerItemTutorialBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: TutorialActivity.TutorialViewData,
                 recyclerView: RecyclerView,
                 viewOverflowPagerIndicator: OverflowPagerIndicator) = with(itemBinding) {

            itemView.apply {
                Glide.with(tutorialImageView.context)
                    .load(data.imageId)
                    .into(tutorialImageView)

                subTitleTexView.text = data.subTitle
                messageTexView.text = data.message

                tutorialButton.setOnClickListener {
                    recyclerView.smoothScrollToPosition(adapterPosition + 1)
                    viewOverflowPagerIndicator.onPageSelected(adapterPosition + 1)
                }
            }
        }
    }

}