package com.graduation_work.bonappetit.model.use_case

import com.graduation_work.bonappetit.model.SortType
import com.graduation_work.bonappetit.model.data.StockList
import com.graduation_work.bonappetit.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// UseCase必要ないかも ViewModelに書いたほうがわかりやすいかもしれない
class StockUseCase(
	private val stockRepository: StockRepository
) {
	private val _list = MutableStateFlow(StockList())
	val list: StateFlow<StockList> = _list
	
	suspend fun loadStocks() {
		_list.value = stockRepository.get()
	}
	
	suspend fun searchStockByName(name: String) {
		_list.value = stockRepository.get(name)
	}
	
	fun sortByRegistrationOrderAsc() {
		_list.value = _list.value.sort(SortType.ID_ASC)
	}
	
	fun sortByRegistrationOrderDesc() {
		_list.value = _list.value.sort(SortType.ID_DESC)
	}
}
