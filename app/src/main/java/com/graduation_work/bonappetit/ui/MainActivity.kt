package com.graduation_work.bonappetit.ui

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.data.repository.RecipeRepositoryImpl
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import com.graduation_work.bonappetit.domain.repository.RecipeRepository
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
                    "きゅうり",
                    "個",
                    "野菜",
                    "賞味期限",
                    BitmapFactory.decodeResource(applicationContext.resources, R.drawable.kyuri)
                )
            )
    
            foodRepo.registerFood(
                Food(
                    "トマト",
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