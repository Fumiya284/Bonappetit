package com.graduation_work.bonappetit.test_utils

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.FoodEntity
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

object TestUtil {
	private val database = MyApplication.database
	private val stockDao = database.stockDao()
	private val foodDao = database.foodDao()
	private val sWithFDao = database.stockWithFoodDao()
	
	private val sampleFood = arrayOf(
		FoodEntity("tomato", "個", "vegetable", "賞味期限"),
		FoodEntity("eggplant", "個", "vegetable", "賞味期限"),
		FoodEntity("cucumber", "本", "vegetable", "賞味期限"),
		FoodEntity("potato", "個", "vegetable", "賞味期限"),
		FoodEntity("orange", "個", "fruit", "消費期限"),
		FoodEntity("grape", "個", "fruit", "消費期限"),
		FoodEntity("apple", "個", "fruit", "消費期限"),
		FoodEntity("mandarin orange", "個", "fruit", "消費期限"),
		FoodEntity("chicken", "グラム", "meat", "消費期限"),
		FoodEntity("salmon", "匹", "fish", "消費期限")
	)
	private val sampleStock = arrayOf(
		StockEntity(0, "tomato", 4),
		StockEntity(0, "eggplant", 2),
		StockEntity(0, "cucumber", 1, LocalDate.now()),
		StockEntity(0, "chicken", 5, LocalDate.now()),
		StockEntity(0, "salmon", 2, LocalDate.now()),
		StockEntity(0, "grape", 3, LocalDate.now())
	)
	
	fun insertFoodData() {
		runBlocking {
			stockDao.deleteAll()
			foodDao.deleteAll()
			
			sampleFood.onEach { foodDao.insert(it) }
		}
	}
	
	fun insertStockData() {
		runBlocking {
			stockDao.deleteAll()
			
			sampleStock.onEach { stockDao.insert(it) }
		}
	}
}