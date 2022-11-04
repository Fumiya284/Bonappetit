package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.MainScreenFragmentBinding
import com.graduation_work.bonappetit.ui.adapter.ViewPagerAdapter

class MainScreenFragment : Fragment() {
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return MainScreenFragmentBinding.inflate(layoutInflater).also {
			val tabTitleArray = arrayOf(getString(R.string.sm_title), getString(R.string.ch_title), getString(
				R.string.c_title))
			
			it.mainViewPager.adapter = ViewPagerAdapter(parentFragmentManager, lifecycle, tabTitleArray.size)
			TabLayoutMediator(it.mainTabLayout, it.mainViewPager) { tab, position ->
				tab.text = tabTitleArray[position]
			}.attach()
		}.root
	}
}