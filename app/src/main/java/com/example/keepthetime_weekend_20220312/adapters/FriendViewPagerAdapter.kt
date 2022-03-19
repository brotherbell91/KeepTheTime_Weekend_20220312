package com.example.keepthetime_weekend_20220312.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.keepthetime_weekend_20220312.fragments.AppointmentListFragment
import com.example.keepthetime_weekend_20220312.fragments.MyFriendListFragment
import com.example.keepthetime_weekend_20220312.fragments.MyProfileFragment
import com.example.keepthetime_weekend_20220312.fragments.RequestedUserListFragment

class FriendViewPagerAdapter(fm : FragmentManager) : FragmentPagerAdapter( fm ) {
    override fun getCount() = 2

    override fun getItem(position: Int): Fragment {

        return when(position){
            0 -> MyFriendListFragment()
            else -> RequestedUserListFragment()

        }
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when(position){

            0 -> "내 친구 목록"
            else -> "친구 요청 목록"

        }
    }
}