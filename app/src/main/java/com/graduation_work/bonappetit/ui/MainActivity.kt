package com.graduation_work.bonappetit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.ui.adapter.ViewPagerAdapter
import com.graduation_work.bonappetit.databinding.ActivityMainBinding
import com.graduation_work.bonappetit.test_utils.TestUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}