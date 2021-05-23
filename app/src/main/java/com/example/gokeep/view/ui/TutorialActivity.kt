package com.example.gokeep.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gokeep.R
import com.example.gokeep.databinding.ActivityTutorialBinding
import com.example.gokeep.util.PreferencesManager
import com.example.gokeep.view.adpter.TutorialAdapter
import cz.intik.overflowindicator.SimpleSnapHelper

class TutorialActivity : AppCompatActivity() {

    val TAG: String = TutorialActivity::class.java.name

    data class TutorialViewData(
        val imageId: Int,
        val title: String,
        val subTitle: String,
        val message: String,
        val backgroundColor: Int
    )

    private lateinit var data: List<TutorialViewData>

    private lateinit var binding: ActivityTutorialBinding
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefManager = PreferencesManager
        prefManager.init(this)

        /**
         * Set up prefManger First Time in App to true
         */
        if(prefManager.get("first_time_in_app", false) == false) {
            prefManager.set("first_time_in_app", true)
        }


        setUpData()
        setUpView()
    }

    private fun setUpData() {
        data = listOf(
            TutorialViewData(
                R.drawable.ic_circle_save_money,
                resources.getString(R.string.tutorial_title_1),
                resources.getString(R.string.tutorial_sub_title_1),
                resources.getString(R.string.tutorial_message_1),
                ContextCompat.getColor(this, R.color.bluePrimary)
            ),
            TutorialViewData(
                R.drawable.ic_circle_key,
                resources.getString(R.string.tutorial_title_2),
                resources.getString(R.string.tutorial_sub_title_2),
                resources.getString(R.string.tutorial_message_2),
                ContextCompat.getColor(this, R.color.blueAccent)
            ),
            TutorialViewData(
                R.drawable.ic_circle_wallet,
                resources.getString(R.string.tutorial_title_3),
                resources.getString(R.string.tutorial_sub_title_3),
                resources.getString(R.string.tutorial_message_3),
                ContextCompat.getColor(this, R.color.bluePrimaryLight)
            ),
            TutorialViewData(
                R.drawable.ic_circle_growth,
                resources.getString(R.string.tutorial_title_4),
                resources.getString(R.string.tutorial_sub_title_4),
                resources.getString(R.string.tutorial_message_4),
                ContextCompat.getColor(this, R.color.bluePrimaryDark)
            )
        )
    }

    private fun setUpView() = with(binding) {

        /**
         * Init recyclerView first
         */
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TutorialAdapter(arrayListOf(), recyclerView, viewOverflowPagerIndicator)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.addAllItem(data)
        setUpRecyclerOnScrollListener()
        /**
         * Attach overflowPagerIndicator to recyclerView
         */
        viewOverflowPagerIndicator.attachToRecyclerView(recyclerView)
        SimpleSnapHelper(viewOverflowPagerIndicator).attachToRecyclerView(recyclerView)
    }

    private fun setUpRecyclerOnScrollListener() = with(binding) {
        val scrollChangeListener = object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()?.let {
                    tutorialTitle.text = data[it].title
                    tutorialContainer.setBackgroundColor(data[it].backgroundColor)
                }
            }
        }
        recyclerView.addOnScrollListener(scrollChangeListener)
    }
}