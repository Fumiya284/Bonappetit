package com.graduation_work.bonappetit.domain.use_case

import android.util.Log
import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

/*
	UseCaseの役割↓
	複数のViewModelで使用する関数を持つ
	ビジネスロジックを実装した関数を持つ
	必要な情報をViewModelに公開する
	
	対応するViewModelがUIに関する処理に集中できるようにビジネスロジックの実装などを持つ。
	また、ほかのViewModelとの連携のために情報を保持する。
 */
class StockManagerUseCase {
	private val stockRepository: StockRepository by inject(StockRepository::class.java)
	
	private val _list = MutableStateFlow<List<Stock>>(emptyList())
	val list: StateFlow<List<Stock>> = _list
	
	private val _selectedCategory = MutableStateFlow<Array<String>>(emptyArray())
	val selectedCategory: StateFlow<Array<String>> = _selectedCategory
	
	private val _currentSortType = MutableStateFlow(StockSortType.ID_ASC)
	val currentSortType: StateFlow<StockSortType> = _currentSortType
	
	suspend fun updateStockList(searchString: String) {
		_list.value = if(searchString.isEmpty() && selectedCategory.value.isEmpty()) {    // あとでMutableStateFlowに拡張関数鵜でisEmptyつくる
			stockRepository.fetchAll()
		} else {
			stockRepository.searchByCondition(searchString, selectedCategory.value)
		}
	}
	
	fun changeSortType() {
		_currentSortType.value = when(currentSortType.value) {
			StockSortType.ID_ASC -> {
				StockSortType.ID_DESC
			}
			StockSortType.ID_DESC -> {
				StockSortType.ID_ASC
			}
		}
		
		sortStocks(currentSortType.value)
	}
	
	private fun sortStocks(stockSortType: StockSortType) {
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
