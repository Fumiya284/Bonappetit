package com.graduation_work.bonappetit.test_util

import android.util.Log
import com.graduation_work.bonappetit.data.database.entities.FoodEntity
import com.graduation_work.bonappetit.data.repository.FoodRepository
import com.graduation_work.bonappetit.data.repository.StockRepository
import com.graduation_work.bonappetit.domain.dto.Stock
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class RecyclerViewTest {
	val sRepo = StockRepository()
	val fRepo = FoodRepository()
	
	val sampleStock = Stock(12, "キュウリ", "個", 3, null)
	
	fun startTest() {
		runBlocking {
		
		}
	}
}