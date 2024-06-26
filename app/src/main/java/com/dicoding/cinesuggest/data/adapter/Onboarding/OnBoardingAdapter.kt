package com.dicoding.cinesuggest.data.adapter.Onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.cinesuggest.view.Onboarding.OnboardingFragment

class OnBoardingAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 1 //current fragment
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> OnboardingFragment()
            else -> OnboardingFragment() //change if have more fragment
        }
    }
}
