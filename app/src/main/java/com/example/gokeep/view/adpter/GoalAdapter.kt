package com.example.gokeep.view.adpter

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gokeep.R
import com.example.gokeep.data.localdb.entity.Goal
import com.example.gokeep.databinding.RecyclerItemGoalBinding
import com.example.gokeep.databinding.RecyclerItemSetGoalBinding

class GoalAdapter(private val goalList: ArrayList<Goal>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = GoalAdapter::javaClass.name
    private val VIEW_TYPE_SET_GOAL = 0
    private val VIEW_TYPE__GOAL_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            0 ->
                SetGoalViewHolder(
                    RecyclerItemSetGoalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            else ->
                GoalViewHolder(
                    RecyclerItemGoalBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            0 -> {
                val setGoalViewHolder = holder as SetGoalViewHolder
                Log.d(TAG, "size" + goalList.size)
                setGoalViewHolder.bind()
            }
            else -> {
                val goalViewHolder = holder as GoalViewHolder
                goalViewHolder.bind(data = goalList[position - 1])
            }
        }
    }

    /**
     * Plus one for the setGoal View
     */
    override fun getItemCount() = goalList.size + 1

    override fun getItemViewType(position: Int)
            = if( position == 0) VIEW_TYPE_SET_GOAL else VIEW_TYPE__GOAL_ITEM

    fun addItem(goal: Goal) {
        goalList.add(goal)
    }

    fun addAllItem(list: List<Goal>) {
        goalList.addAll(list)
    }

    class SetGoalViewHolder(private val itemBinding: RecyclerItemSetGoalBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind() = with(itemBinding) {
            setGoalContainer.setOnClickListener {  }
        }
    }

    class GoalViewHolder(private val itemBinding: RecyclerItemGoalBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: Goal)= with(itemBinding){
            itemView.apply {
                Glide.with(goalImageView.context)
                    .load(data.imageUrl)
                    .into(goalImageView)

                goalTitle.text = data.title

                goalProgressBar.progress = (data.currentSaving/data.budget) * 100

                if(goalProgressBar.progress == 100) {
                    goalImageView.alpha = 0.5F
                    achievedTextView.visibility = View.VISIBLE
                    goalImageView.strokeWidth = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8F,
                            context.resources.displayMetrics
                    )
                    goalProgressBar.alpha = 0.5F
                    goalImageView.strokeColor =
                        ContextCompat.getColorStateList(context, R.color.yellow)
                }
            }
        }
    }
}