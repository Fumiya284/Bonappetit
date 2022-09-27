package com.graduation_work.bonappetit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.model.repository.FoodRepository
import com.graduation_work.bonappetit.test_util.FoodRepoTest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        FoodRepoTest(FoodRepository()).startTest()
    }
}