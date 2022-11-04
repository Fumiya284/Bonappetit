package com.graduation_work.bonappetit.ui.view_model

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.R
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
	
	縦に長くて見ずらい<-改善できた！！
 */
class StockManagerViewModel(private val application: MyApplication) : AndroidViewModel(application) {
	private val useCase: StockManagerUseCase by inject(StockManagerUseCase::class.java)
	
	// 在庫管理画面の名前検索用の文字列
	val searchString = MutableStateFlow<String>(useCase.searchString.value)
	
	// 在庫の一覧 変更はUseCaseを通じて行う
	val stocks: StateFlow<List<Stock>> = useCase.stocks
	
	// 食材のカテゴリー一覧
	private val categories: StateFlow<Map<String, Boolean>> = useCase.categories
	
	// viewは↓を監視して画面遷移やDialogを表示する
	private val _message = MutableSharedFlow<Message>()
	val message: SharedFlow<Message> = _message
	
	private val _searchBtnText = MutableStateFlow<String>(application.applicationContext.getString(R.string.sm_search_by_category_off))
	val searchBtnText: StateFlow<String> = _searchBtnText
	
	private val _sortBtnText = MutableStateFlow<String>(application.applicationContext.getString(R.string.sm_sort_register_oder_asc))
	val sortBtnText: StateFlow<String> = _sortBtnText
	
	init {
		viewModelScope.launch { useCase.loadStocksAndCategoriesIfEmpty() }
		
		searchString.onEach {
			useCase.searchString.value = searchString.value
			useCase.updateStockList()
			useCase.sortStocks()
		}.launchIn(viewModelScope)
		
		useCase.categories
			.onEach { updateSearchBtnText() }
			.launchIn(viewModelScope)
		
		useCase.currentSortType
			.onEach { updateSortBtnText() }
			.launchIn(viewModelScope)
	}
	
	fun onSearchBtnClick() {
		viewModelScope.launch { _message.emit(Message.SEARCH) }
	}
	
	fun onRegisterBtnClick() {
		viewModelScope.launch { _message.emit(Message.REGISTER) }
	}
	
	fun onSortBtnClick() {
		viewModelScope.launch {
			useCase.switchSortType()
			useCase.sortStocks()
		}
	}
	
	private fun updateSortBtnText() {
		_sortBtnText.value = when(useCase.currentSortType.value) {
			StockManagerUseCase.StockSortType.ID_ASC -> { application.applicationContext.getString(R.string.sm_sort_register_oder_asc) }
			StockManagerUseCase.StockSortType.ID_DESC -> { application.applicationContext.getString(R.string.sm_sort_register_oder_desc) }
		}
	}
	
	private fun updateSearchBtnText() {
		_searchBtnText.value =
			if (true in categories.value.values.toBooleanArray()) { application.applicationContext.getString(R.string.sm_search_by_category_on) } else { application.applicationContext.getString(R.string.sm_search_by_category_off) }
	}
	
	// 画面遷移をviewに知らせるメッセージ
	enum class Message {
		// 絞り込みのDialog
		SEARCH,
		
		// 在庫登録画面
		REGISTER,
		
		// 食品詳細画面
		DETAIL
	}
}