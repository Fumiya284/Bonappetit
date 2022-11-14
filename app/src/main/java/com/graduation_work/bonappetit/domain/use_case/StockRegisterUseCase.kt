package com.graduation_work.bonappetit.domain.use_case

import arrow.core.Either
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.dto.StockRegistrationInfo
import com.graduation_work.bonappetit.domain.exception.FailedToRegisterException
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate

class StockRegisterUseCase {
	private val stockRepository: StockRepository by inject(StockRepository::class.java)
	private val foodRepository: FoodRepository by inject(FoodRepository::class.java)
	
	private val _foods = MutableStateFlow<List<Food>>(emptyList())
	val foods: StateFlow<List<Food>> = _foods
	
	private val _registeredStock = MutableStateFlow<List<Stock>>(emptyList())
	val registeredStock: StateFlow<List<Stock>> = _registeredStock
	
	suspend fun loadFoods() {
		_foods.value = foodRepository.fetchAllFood()
	}
	
	suspend fun loadRegisteredStock(food: Food) {
		_registeredStock.value = stockRepository.fetchByName(food.name)
	}
	
	suspend fun register(chosenFood: Food, quantity: Int, limit: LocalDate?, note: String?): Either<FailedToRegisterException, Unit> {
		val newStock = StockRegistrationInfo(chosenFood.name, quantity, limit, note)
		return stockRepository.save(newStock)
	}
}