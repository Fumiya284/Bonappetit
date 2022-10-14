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
		FoodEntity("しいたけ", "個"),
		FoodEntity("orange", "個"),
		FoodEntity("grape", "個")
	)
	private val sampleStockList = listOf<StockEntity>(
		StockEntity.create4Insert("きゅうり", 2),
		StockEntity.create4Insert("白菜", 1),
		StockEntity.create4Insert("豆腐", 50),
		StockEntity.create4Insert("grape", 3),
		StockEntity.create4Insert("orange", 2)
	)
	
	fun registerDummyFood() {
		runBlocking{
			stockDao.deleteAll()
			foodDao.deleteAll()
			sampleFoodList.onEach {
				foodDao.insertFood(it)
			}
		}
	}
	
	fun registerDummyStock() {
		runBlocking {
			stockDao.deleteAll()
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