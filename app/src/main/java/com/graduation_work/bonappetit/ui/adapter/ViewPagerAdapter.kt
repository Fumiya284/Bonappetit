package com.graduation_work.bonappetit.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.graduation_work.bonappetit.test_utils.MockFragment
import com.graduation_work.bonappetit.ui.fragment.StockManagerFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val numOfTabs: Int
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    override fun getItemCount(): Int {
        return numOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> { StockManagerFragment() }
            else -> { MockFragment() }
        }
    }
}