package com.graduation_work.bonappetit.data.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// そのうち例外処理かく
class FoodRepositoryImpl(
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FoodRepository {
	private val database = MyApplication.database
	private val dao = database.foodDao()
	
	override suspend fun fetchAllCategory(): List<String> = withContext(dispatcher) {
		return@withContext dao.selectAllCategory()
	}
	
	override suspend fun fetchAllFood(): List<Food> = withContext(dispatcher) {
		return@withContext dao.selectAll().map { it.convertToFood() }
	}
}