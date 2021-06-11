package com.example.gokeep.view.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gokeep.R
import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.data.localdb.entity.Spending
import com.example.gokeep.data.model.SpendingGroupByTag
import com.example.gokeep.data.model.getTagImageByTitle
import com.example.gokeep.databinding.RecyclerItemSpendingBinding
import com.example.gokeep.util.ColorHelper
import com.example.gokeep.util.TextFormatHelper
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SpendingAdapter(private val spendingList: ArrayList<SpendingGroupByTag>) :
    RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder =
        SpendingViewHolder(
            RecyclerItemSpendingBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(holder: SpendingViewHolder, position: Int) {
        holder.bind(spendingList[position])
    }

    override fun getItemCount() = spendingList.size

    fun addItem(spending: SpendingGroupByTag) {
        spendingList.add(spending)
    }

    fun addAllItem(list: List<SpendingGroupByTag>) {
        spendingList.clear()
        spendingList.addAll(list)
    }

    class SpendingViewHolder(private val itemBinding: RecyclerItemSpendingBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(data: SpendingGroupByTag) = with(itemBinding) {
            itemView.apply {
                Glide.with(categoryImageView.context)
                    .load(getTagImageByTitle(data.tag))
                    .into(categoryImageView)
                categoryTextView.text = data.tag
                contentTextView.text = data.concatTitle
                val spendingString = TextFormatHelper.moneyFormat(data.sumCost)
                moneyTextView.text =
                    if (data.tag != "Income") "-${spendingString}" else spendingString
                moneyTextView.setTextColor(
                    if (data.tag != "Income")
                        ContextCompat.getColor(context, R.color.bluePrimaryDark)
                    else
                        ContextCompat.getColor(context, R.color.greenSuccess)
                )

                val simpleDateFormat = SimpleDateFormat("MMM dd", Locale.US)
                dateTextView.text = simpleDateFormat.format(data.createdTimeStamp)

                cardView.setBackgroundColor(
                    ColorHelper.getStaticColorMap(context, data.tag) ?: ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
        }
    }

}