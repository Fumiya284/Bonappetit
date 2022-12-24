package com.graduation_work.bonappetit.ui

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.inject

class MainActivity : AppCompatActivity() {
    private val database = MyApplication.database
    private val foodRepo: FoodRepository by inject(FoodRepository::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        runBlocking {
            foodRepo.registerFood(
                Food(
                    "kyuri",
                    "個",
                    "野菜",
                    "賞味期限",
                    BitmapFactory.decodeResource(applicationContext.resources, R.drawable.kyuri)
                )
            )
    
            foodRepo.registerFood(
                Food(
                    "tomato",
                    "個",
                    "野菜",
                    "賞味期限",
                    BitmapFactory.decodeResource(applicationContext.resources, R.drawable.tomato)
                )
            )
        }
         */
    }
}