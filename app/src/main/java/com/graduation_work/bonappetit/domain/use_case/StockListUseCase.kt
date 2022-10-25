package com.graduation_work.bonappetit.domain.use_case

import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

class StockListUseCase {
	private val stockRepository: StockRepository by inject(StockRepository::class.java)
	
	private val _list = MutableStateFlow<List<Stock>>(emptyList())
	val list: StateFlow<List<Stock>> = _list
	
	private val _selectedTag = MutableStateFlow<List<String>>(listOf(""))
	val selectedTag = _selectedTag
	
	suspend fun loadStocks(name: String? = null, tag: List<String>? = null) {
		_list.value = stockRepository.get(name, selectedTag.value)
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
