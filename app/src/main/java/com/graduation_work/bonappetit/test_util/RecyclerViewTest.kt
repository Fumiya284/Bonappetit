package com.graduation_work.bonappetit.test_util

import com.graduation_work.bonappetit.data.repository.StockRepositoryImpl
import com.graduation_work.bonappetit.domain.dto.Stock
import kotlinx.coroutines.runBlocking

class RecyclerViewTest {
	val sRepo = StockRepositoryImpl()
	
	val sampleStock = Stock(12, "キュウリ", "個", 3, null)
	
	fun startTest() {
		runBlocking {
		
		}
	}
}