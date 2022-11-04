package com.graduation_work.bonappetit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.ui.adapter.ViewPagerAdapter
import com.graduation_work.bonappetit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val viewPager = binding.mainViewPager
        val tabLayout = binding.mainTabLayout
        val tabTitleArray = arrayOf(getString(R.string.sm_title), getString(R.string.ch_title), getString(R.string.c_title))

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, tabTitleArray.size)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    
        setContentView(binding.root)
        
        TestUtil.insertFoodData()
        TestUtil.insertStockData()
    }
}