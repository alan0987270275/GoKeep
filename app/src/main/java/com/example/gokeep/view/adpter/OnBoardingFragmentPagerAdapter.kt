package com.example.gokeep.view.adpter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.gokeep.view.ui.activity.OnBoardingActivity
import com.example.gokeep.view.ui.fragment.OnboardingFragmennt

class OnBoardingFragmentPagerAdapter(fm: FragmentManager, private val data: List<OnBoardingActivity.OnBoardingViewData>) :
    FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return OnboardingFragmennt.newInstance(
            position,
            data[position].backgroundColor,
            data[position].imageId,
            data[position].title,
            data[position].subTitle,
            data[position].message
        )
    }

    override fun getCount(): Int {
        return  data.size
    }
}