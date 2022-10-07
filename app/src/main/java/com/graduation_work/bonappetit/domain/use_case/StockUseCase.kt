package com.graduation_work.bonappetit.domain.use_case

import com.graduation_work.bonappetit.data.repository.StockRepository
import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.dto.StockDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StockUseCase(
	private val stockRepository: StockRepository
) {
	private val _list = MutableStateFlow<List<StockDto>>(emptyList())
	val list: StateFlow<List<StockDto>> = _list
	
	suspend fun loadStocks() {
		_list.value = stockRepository.get().map { StockDto.fromEntity(it) }
	}
	
	suspend fun searchStocksByName(name: String) {
		_list.value = stockRepository.get(name).map { StockDto.fromEntity(it) }
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
