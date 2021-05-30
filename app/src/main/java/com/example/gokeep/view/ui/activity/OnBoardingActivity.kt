package com.example.gokeep.view.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.gokeep.R
import com.example.gokeep.databinding.ActivityOnBoardingBinding
import com.example.gokeep.view.adpter.OnBoardingFragmentPagerAdapter

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    data class OnBoardingViewData(
        val imageId: Int,
        val title: String,
        val subTitle: String,
        val message: String,
        val backgroundColor: Int
    )
    private lateinit var data: List<OnBoardingViewData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpData()
        initView()
    }

    private fun initView() = with(binding) {
        // Set up number of Page
        viewPager.adapter = OnBoardingFragmentPagerAdapter(supportFragmentManager, data)
        wormDotsIndicator.setViewPager(viewPager);
    }

    private fun setUpData() {
        data = listOf(
            OnBoardingViewData(
                R.drawable.ic_key,
                resources.getString(R.string.on_boarding_title_1),
                resources.getString(R.string.on_boarding_sub_title_1),
                resources.getString(R.string.on_boarding_message_1),
                ContextCompat.getColor(this, R.color.bluePrimary)
            ),
            OnBoardingViewData(
                R.drawable.ic_wallet,
                resources.getString(R.string.on_boarding_title_2),
                resources.getString(R.string.on_boarding_sub_title_2),
                resources.getString(R.string.on_boarding_message_2),
                ContextCompat.getColor(this, R.color.blueAccent)
            ),
            OnBoardingViewData(
                R.drawable.ic_growth,
                resources.getString(R.string.on_boarding_title_3),
                resources.getString(R.string.on_boarding_sub_title_3),
                resources.getString(R.string.on_boarding_message_3),
                ContextCompat.getColor(this, R.color.bluePrimaryDark)
            )
        )
    }

}