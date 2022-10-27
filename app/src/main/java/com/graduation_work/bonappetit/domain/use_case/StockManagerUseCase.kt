package com.graduation_work.bonappetit.domain.use_case

import android.util.Log
import com.graduation_work.bonappetit.data.repository.FoodRepositoryImpl
import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import org.koin.java.KoinJavaComponent.inject

/*
	UseCaseの役割↓
	複数のViewModelで使用する関数を持つ
	ビジネスロジックを実装した関数を持つ
	必要な情報をViewModelに公開する
	
	対応するViewModelがUIに関する処理に集中できるようにビジネスロジックの実装などを持つ。
	また、ほかのViewModelとの連携のために情報を保持する。
	
	StateFlowとSharedFlowのどっちを使うべきかあとで考える
 */
class StockManagerUseCase {
	private val stockRepository: StockRepository by inject(StockRepository::class.java)
	private val foodRepository: FoodRepository by inject(FoodRepository::class.java)
	
	private val _stockList = MutableStateFlow<List<Stock>>(emptyList())
	val stockList: StateFlow<List<Stock>> = _stockList
	
	private val _categoryList = MutableStateFlow<MutableMap<String, Boolean>>(mutableMapOf())
	val categoryList: StateFlow<Map<String, Boolean>> = _categoryList
	
	private val _currentSortType = MutableStateFlow(StockSortType.ID_ASC)
	val currentSortType: StateFlow<StockSortType> = _currentSortType
	
	suspend fun loadAllStock() {
		_stockList.value = stockRepository.fetchAll()
	}
	
	suspend fun loadAllCategory() {
		_categoryList.value = foodRepository.fetchAllCategory().associateWith { false }.toMutableMap()
	}
	
	suspend fun updateStockList(searchString: String) {
		val selectedCategory = categoryList.value.filter { it.value }.keys.toTypedArray()
		
		_stockList.value = if(searchString.isEmpty() && selectedCategory.isEmpty()) {
			stockRepository.fetchAll()
		} else {
			stockRepository.searchByCondition(searchString, selectedCategory)
		}
		
		sortStocks(_currentSortType.value)
	}
	
	fun changeCategoryStatus(categoryName: String, nextStatus: Boolean) {
		if (nextStatus != _categoryList.value[categoryName]) {
			_categoryList.value[categoryName] = nextStatus
		}
	}
	
	fun changeSortType() {
		_currentSortType.value = when(_currentSortType.value) {
			StockSortType.ID_ASC -> {
				StockSortType.ID_DESC
			}
			StockSortType.ID_DESC -> {
				StockSortType.ID_ASC
			}
		}
		
		sortStocks(_currentSortType.value)
	}
	
	private fun sortStocks(stockSortType: StockSortType) {
		_stockList.value = when(stockSortType) {
			StockSortType.ID_ASC -> {
				_stockList.value.sortedWith(compareBy { it.id })
			}
			StockSortType.ID_DESC -> {
				_stockList.value.sortedWith(compareByDescending { it.id })
			}
		}
	}
}
