package com.dicoding.cinesuggest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.cinesuggest.Onboarding.Onboarding1Fragment
import com.dicoding.cinesuggest.Onboarding.Onboarding2Fragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 ->{
                Onboarding1Fragment()
            }
            else ->{
                Onboarding2Fragment()
            }
        }
    }

}