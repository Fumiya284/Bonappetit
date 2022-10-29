package com.graduation_work.bonappetit.domain.use_case

import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.dto.Limit
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

class StockRegisterUseCase {
	private val stockRepository: StockRepository by inject(StockRepository::class.java)
	private val foodRepository: FoodRepository by inject(FoodRepository::class.java)
	
	private val _foodList = MutableStateFlow<List<Food>>(emptyList())
	val foodList: StateFlow<List<Food>> = _foodList
	
	suspend fun loadFoodList() {
		_foodList.value = foodRepository.fetchAllFood()
	}
	
	suspend fun register(food: Food, count: Int, limit: Limit?) {
		val newStock = Stock(food = food, count = count, limit = limit)
		stockRepository.save(newStock)
	}
}