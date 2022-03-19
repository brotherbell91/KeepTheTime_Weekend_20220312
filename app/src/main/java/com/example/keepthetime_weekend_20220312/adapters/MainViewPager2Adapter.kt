package com.example.keepthetime_weekend_20220312.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.keepthetime_weekend_20220312.fragments.AppointmentListFragment
import com.example.keepthetime_weekend_20220312.fragments.MyProfileFragment

class MainViewPager2Adapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AppointmentListFragment()
            else -> MyProfileFragment()
        }

    }
}