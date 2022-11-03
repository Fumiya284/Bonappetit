package com.graduation_work.bonappetit.domain.use_case

import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
@OptIn(DelicateCoroutinesApi::class)
class StockManagerUseCase {
	private val stockRepository: StockRepository by inject(StockRepository::class.java)
	private val foodRepository: FoodRepository by inject(FoodRepository::class.java)
	private var searchString: String = ""
	
	private val _stockList = MutableStateFlow<List<Stock>>(emptyList())
	val stockList: StateFlow<List<Stock>> = _stockList
	
	private val _categoryList = MutableStateFlow<MutableMap<String, Boolean>>(mutableMapOf())
	val categoryList: StateFlow<Map<String, Boolean>> = _categoryList
	
	private val _currentSortType = MutableStateFlow(StockSortType.ID_ASC)
	val currentSortType: StateFlow<StockSortType> = _currentSortType
	
	init {
		// 最初にすべての在庫データと食べ物のカテゴリー一覧をロードしたい　GlobalScopeの正しい使い方から外れてるのでいい方法を知りたい
		GlobalScope.launch {
			_stockList.value = stockRepository.fetchAll()
			_categoryList.value = foodRepository.fetchAllCategory().associateWith { false }.toMutableMap()
		}
	}
	
	
	suspend fun setSearchStringWithReload(searchString: String) {
		this.searchString = searchString
		
		loadStockList()
	}
	
	suspend fun setCategoryStatusWithReload(category: String, nextStatus: Boolean) {
		if (nextStatus != _categoryList.value[category]) {
			_categoryList.value[category] = nextStatus
		}
		
		loadStockList()
	}
	
	suspend fun switchSortTypeWithReload() {
		_currentSortType.value = when(_currentSortType.value) {
			StockSortType.ID_ASC -> {
				StockSortType.ID_DESC
			}
			StockSortType.ID_DESC -> {
				StockSortType.ID_ASC
			}
		}
		
		loadStockList()
	}
	
	private suspend fun loadStockList() {
		val selectedCategory = categoryList.value.filter { it.value }.keys.toTypedArray()
		
		_stockList.value = if(searchString.isEmpty() && selectedCategory.isEmpty()) {
			stockRepository.fetchAll()
		} else {
			stockRepository.fetchByCondition(searchString, selectedCategory)
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
