package com.graduation_work.bonappetit.model.use_case

import com.graduation_work.bonappetit.model.data.Stock
import com.graduation_work.bonappetit.model.data.StockList
import com.graduation_work.bonappetit.model.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// UseCase必要ないかも
class StockUseCase(
	private val stockRepository: StockRepository
) {
	private val _list = MutableStateFlow(StockList())
	val list: StateFlow<StockList> = _list
	
	suspend fun registerNewStock(stock: Stock) {
		stockRepository.register(stock)
	}
}
