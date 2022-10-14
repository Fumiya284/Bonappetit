package com.graduation_work.bonappetit.domain.use_case

import android.util.Log
import com.graduation_work.bonappetit.data.repository.StockRepository
import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.dto.Stock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class StockUseCase(
	private val stockRepository: StockRepository
) {
	private val _list = MutableStateFlow<List<Stock>>(emptyList())
	val list: StateFlow<List<Stock>> = _list
	
	suspend fun loadStocks(name:String? = null) {
		_list.value = stockRepository.get(name).map { Stock.fromView(it) }
	}
	
	fun sortStocks(stockSortType: StockSortType) {
		_list.value = when(stockSortType) {
			StockSortType.ID_ASC -> {
				_list.value.sortedWith(compareBy { it.id })
			}
			StockSortType.ID_DESC -> {
				_list.value.sortedWith(compareByDescending { it.id })
			}
		}
	}
}
