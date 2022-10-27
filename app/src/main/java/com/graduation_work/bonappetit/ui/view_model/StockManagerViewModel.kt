package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.use_case.StockManagerUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

/*
	ViewModelの役割は
	・表示する情報の保持
	・UIの状態管理
	・イベントの処理
	
	StateFlowとSharedFlowを使い分けたほうがよさそう
	StateFlowは
	・初期値を持つ
	・Subscriberの好きなタイミングで最新の値を取り出せる
	・値が変化した時だけSubscriberに伝わる
	・valueプロパティで簡単に値をセット・ゲットできる
	
	縦に長くて見ずらい
 */
class StockManagerViewModel : ViewModel() {
	private val useCase: StockManagerUseCase by inject(StockManagerUseCase::class.java)
	
	// 在庫管理画面の名前検索用の文字列
	val searchString = MutableStateFlow<String>("")
	
	// 在庫の一覧 変更はUseCaseを通じて行う
	val stockList: StateFlow<List<Stock>> = useCase.stockList
	
	// 食材のカテゴリー一覧
	val categoryList: StateFlow<Map<String, Boolean>> = useCase.categoryList
	
	// viewは↓を監視して画面遷移やDialogを表示する
	private val _message = MutableSharedFlow<Message>()
	val message: SharedFlow<Message> = _message
	
	private val _searchBtnText = MutableStateFlow<String>("")
	val searchBtnText: StateFlow<String> = _searchBtnText
	
	private val _sortBtnText = MutableStateFlow<String>("")
	val sortBtnText: StateFlow<String> = _sortBtnText
	
	init {
		viewModelScope.launch {
			useCase.run {
				loadAllStock()
				loadAllCategory()
			}
		}
		
		searchString.onEach {
			useCase.updateStockList(searchString.value)
		}.launchIn(viewModelScope)
		
		updateSearchBtnText()
		updateSortBtnText()
	}
	
	fun onSortBtnClick() {
		useCase.changeSortType()
		updateSortBtnText()
	}
	
	fun onSearchBtnClick() {
		viewModelScope.launch {
			_message.emit(Message.Search)
		}
	}
	
	fun onDialogItemClick(category: String, nextStatus: Boolean) {
		viewModelScope.launch {
			useCase.run {
				changeCategoryStatus(category, nextStatus)
				updateStockList(searchString.value)
			}
		}
		updateSearchBtnText()
	}
	
	private fun updateSearchBtnText() {
		val categoryStatus = categoryList.value.values.toBooleanArray()
		_searchBtnText.value = if (true in categoryStatus) { ("絞り込み:ON") } else { ("絞り込み:OFF") }
	}
	
	private fun updateSortBtnText() {
		_sortBtnText.value = useCase.currentSortType.value.text
	}
	
	// 画面遷移をviewに知らせるメッセージ
	sealed class Message {
		// 絞り込みのDialog
		object Search : Message()
		
		// 在庫登録画面
		object Register : Message()
		
		// 食品詳細画面
		object Detail : Message()
	}
}