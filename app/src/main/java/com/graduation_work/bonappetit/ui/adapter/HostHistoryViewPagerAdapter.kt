package com.graduation_work.bonappetit.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.graduation_work.bonappetit.ui.fragment.ConsumptionHistoryFragment
import com.graduation_work.bonappetit.ui.fragment.TopHistoryFragment
import com.graduation_work.bonappetit.ui.fragment.WastedHistoryFragment

class HostHistoryViewPagerAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> ConsumptionHistoryFragment()
            2 -> WastedHistoryFragment()
            else -> TopHistoryFragment()
        }
    }
}
