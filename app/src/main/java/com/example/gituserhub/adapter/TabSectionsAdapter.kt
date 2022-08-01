package com.example.gituserhub.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gituserhub.fragments.FollowersFragment
import com.example.gituserhub.fragments.FollowingFragment

class TabSectionsAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var allFrags: Fragment? = null
        when (position) {
            0 -> allFrags = FollowersFragment()
            1 -> allFrags = FollowingFragment()
        }
        return allFrags as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}