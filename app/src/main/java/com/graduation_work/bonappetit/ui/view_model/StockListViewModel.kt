package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.use_case.StockUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StockListViewModel : ViewModel() {
	private val useCase: StockUseCase = StockUseCase()
	private var currentSortType = StockSortType.ID_ASC
	
	// 在庫管理画面の名前検索用の文字列　双方向データバインディング
	val searchString = MutableStateFlow<String>("")
	
	// 在庫の一覧 変更はUseCaseを通じて行う
	val stockList: StateFlow<List<Stock>> = useCase.list
	
	// 在庫情報の現在のソートボタンに表示するテキスト
	private val _sortBtnText = MutableStateFlow<String>(currentSortType.text)
	val sortBtnText = _sortBtnText
	
	init {
		searchString.onEach {
			useCase.run {
				loadStocks(searchString.value)
				sortStocks(currentSortType)
			}
		}.launchIn(viewModelScope)
	}
	
	fun onSortBtnTap() {
		currentSortType = when(currentSortType) {
			StockSortType.ID_ASC -> {
				StockSortType.ID_DESC
			}
			StockSortType.ID_DESC -> {
				StockSortType.ID_ASC
			}
		}
		
		useCase.sortStocks(currentSortType)
		_sortBtnText.value = currentSortType.text
	}
}