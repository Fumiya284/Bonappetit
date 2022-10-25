package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.use_case.StockManagerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent.inject

/*
	ViewModelの役割は
	・表示する情報の保持
	・UIの状態管理
	・イベントの処理
 */
class StockManagerViewModel : ViewModel() {
	private val useCase: StockManagerUseCase by inject(StockManagerUseCase::class.java)
	
	// 在庫管理画面の名前検索用の文字列
	val searchString = MutableStateFlow<String>("")
	
	// 在庫の一覧 変更はUseCaseを通じて行う
	val stockList: StateFlow<List<Stock>> = useCase.list
	
	// 在庫管理画面のソートボタンに表示するテキスト
	val sortBtnText: StateFlow<StockSortType> = useCase.currentSortType
	
	init {
		searchString.onEach {
			useCase.run { updateStockList(searchString.value) }
		}.launchIn(viewModelScope)
		
		useCase.selectedCategory.onEach {
			useCase.run { updateStockList(searchString.value) }
		}.launchIn(viewModelScope)
	}
	
	fun onSortBtnTap() {
		useCase.changeSortType()
	}
}