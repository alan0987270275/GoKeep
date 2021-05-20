package com.example.gokeep.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.gokeep.R
import com.example.gokeep.databinding.ActivityTutorialBinding
import com.example.gokeep.view.adpter.TutorialAdapter
import cz.intik.overflowindicator.SimpleSnapHelper

class TutorialActivity : AppCompatActivity() {

    data class TutorialViewData(
        val imageId: Int,
        val title: String,
        val subTitle: String,
        val message: String
    )

    private lateinit var data: List<TutorialViewData>

    private lateinit var binding: ActivityTutorialBinding
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpData()
        setUpView()
    }

    private fun setUpData() {
        data = listOf(
            TutorialViewData(
                R.drawable.ic_circle_save_money,
                resources.getString(R.string.tutorial_title_1),
                resources.getString(R.string.tutorial_sub_title_1),
                resources.getString(R.string.tutorial_message_1)
            ),
            TutorialViewData(
                R.drawable.ic_circle_key,
                resources.getString(R.string.tutorial_title_2),
                resources.getString(R.string.tutorial_sub_title_2),
                resources.getString(R.string.tutorial_message_2)
            ),
            TutorialViewData(
                R.drawable.ic_circle_wallet,
                resources.getString(R.string.tutorial_title_3),
                resources.getString(R.string.tutorial_sub_title_3),
                resources.getString(R.string.tutorial_message_3)
            ),
            TutorialViewData(
                R.drawable.ic_circle_growth,
                resources.getString(R.string.tutorial_title_4),
                resources.getString(R.string.tutorial_sub_title_4),
                resources.getString(R.string.tutorial_message_4)
            )
        )
    }


    private fun setUpView() = with(binding) {

        /**
         * Init recyclerView first
         */
        val adapter = TutorialAdapter(arrayListOf())
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        adapter.addAllItem(data)

        /**
         * Init overflowPagerIndicator and attach to recyclerView
         */
        viewOverflowPagerIndicator.attachToRecyclerView(recyclerView)
        SimpleSnapHelper(viewOverflowPagerIndicator).attachToRecyclerView(recyclerView)
    }
}