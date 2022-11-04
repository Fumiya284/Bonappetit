package com.graduation_work.bonappetit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayoutMediator
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.data.database.entities.FoodEntity
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.ui.adapter.ViewPagerAdapter
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import com.graduation_work.bonappetit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val database = MyApplication.database
    private val stockDao = database.stockDao()
    private val foodDao = database.foodDao()
    private val sWithFDao = database.stockWithFoodDao()

    private lateinit var binding: ActivityMainBinding
    private val tabTitleArray = arrayOf(
        "syouzai",
        "aaa",
        "bbbb"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.mainViewPager
        val tabLayout = binding.mainTabLayout

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, tabTitleArray.size)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }
}
    /*
    private object TestUtil {
        private val database = MyApplication.database
        private val stockDao = database.stockDao()
        private val foodDao = database.foodDao()
        private val sWithFDao = database.stockWithFoodDao()
        
        private val sampleFood = arrayOf(
            FoodEntity("tomato", "個", "vegetable"),
            FoodEntity("eggplant", "個", "vegetable"),
            FoodEntity("cucumber", "本", "vegetable"),
            FoodEntity("potato", "個", "vegetable"),
            FoodEntity("orange", "個", "fruit"),
            FoodEntity("grape", "個", "fruit"),
            FoodEntity("apple", "個", "fruit"),
            FoodEntity("mandarin orange", "個", "fruit"),
            FoodEntity("chicken", "グラム", "meat"),
            FoodEntity("salmon", "匹", "fish")
        )
        private val sampleStock = arrayOf(
            StockEntity(0, "tomato", 4),
            StockEntity(0, "eggplant", 2),
            StockEntity(0, "cucumber", 1, LocalDate.now(), "賞味期限"),
            StockEntity(0, "chicken", 5, LocalDate.now(), "消費期限"),
            StockEntity(0, "salmon", 2, LocalDate.now(), "消費期限"),
            StockEntity(0, "grape", 3, LocalDate.now(), "賞味期限")
        )
        fun insertFoodData() {
            runBlocking {
                stockDao.deleteAll()
                foodDao.deleteAll()
                
                sampleFood.onEach { foodDao.insertFood(it) }
            }
        }
        
        fun insertStockData() {
            runBlocking {
                stockDao.deleteAll()
                
                sampleStock.onEach { stockDao.insert(it) }
            }
        }
    }
     */