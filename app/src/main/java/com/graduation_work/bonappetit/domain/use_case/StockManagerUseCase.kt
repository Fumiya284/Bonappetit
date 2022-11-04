package com.graduation_work.bonappetit.domain.use_case

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
class StockManagerUseCase {
	private val stockRepository: StockRepository by inject(StockRepository::class.java)
	private val foodRepository: FoodRepository by inject(FoodRepository::class.java)
	private var searchString: String = ""
	
	private val _stocks = MutableStateFlow<List<Stock>>(emptyList())
	val stocks: StateFlow<List<Stock>> = _stocks
	
	private val _categories = MutableStateFlow<Map<String, Boolean>>(emptyMap())
	val categories: StateFlow<Map<String, Boolean>> = _categories
	
	private val _currentSortType = MutableStateFlow(StockSortType.ID_ASC)
	val currentSortType: StateFlow<StockSortType> = _currentSortType
	
	fun setSearchString(searchString: String) {
		this.searchString = searchString
	}
	
	fun setCategoryStatus(category: String, nextStatus: Boolean) {
		val mutableCategoryList = _categories.value.toMutableMap()
			
		mutableCategoryList[category] = nextStatus
		_categories.value = mutableCategoryList.toMap()
	}
	
	fun switchSortType() {
		_currentSortType.value = when(_currentSortType.value) {
			StockSortType.ID_ASC -> { StockSortType.ID_DESC }
			StockSortType.ID_DESC -> { StockSortType.ID_ASC }
		}
	}
	
	fun sortStocks() {
		_stocks.value = when(_currentSortType.value) {
			StockSortType.ID_ASC -> { _stocks.value.sortedWith(compareBy { it.id }) }
			StockSortType.ID_DESC -> { _stocks.value.sortedWith(compareByDescending { it.id }) }
		}
	}
	
	suspend fun loadStocksAndCategoriesIfEmpty() {
		if (_stocks.value.isEmpty() && _categories.value.isEmpty()) {
			_stocks.value = stockRepository.fetchAll()
			_categories.value = foodRepository.fetchAllCategory().associateWith { false }.toMap()
		}
	}
	
	suspend fun updateStockList() {
		val selectedCategory = categories.value.filter { it.value }.keys.toTypedArray()
		
		_stocks.value = if (searchString.isEmpty() && selectedCategory.isEmpty()) {
			stockRepository.fetchAll()
		} else {
			stockRepository.fetchByCondition(searchString, selectedCategory)
		}
	}
	
	enum class StockSortType {
		ID_ASC,
		ID_DESC
	}
}
