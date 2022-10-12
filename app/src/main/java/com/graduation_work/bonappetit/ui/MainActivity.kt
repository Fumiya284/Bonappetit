package com.graduation_work.bonappetit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.data.repository.FoodRepository
import com.graduation_work.bonappetit.test_util.FoodRepoTest
import com.graduation_work.bonappetit.test_util.RecyclerViewTest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // RecyclerViewTest().startTest()
    }
}