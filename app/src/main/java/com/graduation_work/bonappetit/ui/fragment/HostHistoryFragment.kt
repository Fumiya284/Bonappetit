package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.graduation_work.bonappetit.databinding.HostHistoryFragmentBinding
import com.graduation_work.bonappetit.ui.adapter.HostHistoryViewPagerAdapter

class HostHistoryFragment : Fragment() {

    private lateinit var binding: HostHistoryFragmentBinding
    private lateinit var viewPager: ViewPager2

    private val tabTitleArray = arrayOf(
        "トップ",
        "消費",
        "廃棄",
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HostHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.historyViewPager
        viewPager.adapter = HostHistoryViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.historyTabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }
}