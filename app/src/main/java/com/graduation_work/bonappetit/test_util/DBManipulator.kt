package com.graduation_work.bonappetit.test_util

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.FoodEntity
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.domain.dto.Stock
import kotlinx.coroutines.runBlocking

class DBManipulator {
	private val foodDao = MyApplication.database.foodDao()
	private val stockDao = MyApplication.database.stockDao()
	private val sampleFoodList = listOf<FoodEntity>(
		FoodEntity("きゅうり", "個"),
		FoodEntity("豆腐", "グラム"),
		FoodEntity("白菜", "個"),
		FoodEntity("トマト", "個"),
		FoodEntity("しいたけ", "個")
	)
	private val sampleStockList = listOf<StockEntity>(
		StockEntity.create4Insert("きゅうり", 2),
		StockEntity.create4Insert("白菜", 1),
		StockEntity.create4Insert("豆腐", 50)
	)
	
	fun registerDummyFood() {
		runBlocking{
			sampleFoodList.onEach {
				foodDao.insertFood(it)
			}
		}
	}
	
	fun registerDummyStock() {
		runBlocking {
			sampleStockList.onEach {
				stockDao.insert(it)
			}
		}
	}
	
	fun deleteAllData() {
		runBlocking {
			foodDao.deleteAll()
			stockDao.deleteAll()
		}
	}
}