package com.graduation_work.bonappetit.ui.view_model

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
	
	flow.onEachのラムダ式がどのタイミングで実行されるのかわからない
	
	在庫情報を検索やソートするタイミングはUIに関係することだと思うのでViewModelで管理する
 */
class StockManagerViewModel(private val application: MyApplication) : AndroidViewModel(application) {
	private val useCase: StockManagerUseCase by inject(StockManagerUseCase::class.java)
	
	// 在庫の名前検索用の文字列
	val searchString = MutableStateFlow<String>(useCase.searchString.value)
	
	// 在庫の一覧 変更はUseCaseを通じて行う
	val stocks: StateFlow<List<Stock>> = useCase.stocks
	
	// 食材のカテゴリー一覧
	val categories: StateFlow<Map<String, Boolean>> = useCase.categories
	
	// viewは↓を監視して画面遷移やDialogを表示する
	private val _message = MutableSharedFlow<Message>()
	val message: SharedFlow<Message> = _message
	
	private val _searchBtnText = MutableStateFlow<String>("")
	val searchBtnText: StateFlow<String> = _searchBtnText
	
	private val _sortBtnText = MutableStateFlow<String>("")
	val sortBtnText: StateFlow<String> = _sortBtnText
	
	init {
		viewModelScope.launch { useCase.loadStocksAndCategoriesIfEmpty() }
		
		searchString.onEach {
			useCase.setSearchString(searchString.value)
			useCase.updateStockListWithCondition()
			useCase.sortStocks()
		}.launchIn(viewModelScope)
		
		useCase.categories
			.onEach { updateSearchBtnText() }
			.launchIn(viewModelScope)
		
		useCase.currentSortType
			.onEach { updateSortBtnText() }
			.launchIn(viewModelScope)
	}
	
	/**
	 * 絞り込みボタンをタップした際に絞り込みダイアログの表示をViewへ要求する
	 */
	fun onSearchBtnClick() {
		viewModelScope.launch { _message.emit(Message.DisplaySearchDialog) }
	}
	
	/**
	 * 在庫登録ボタンをタップした際に在庫登録画面への遷移をViewに要求する
	 */
	fun onRegisterBtnClick() {
		viewModelScope.launch { _message.emit(Message.MoveToRegisterScreen) }
	}
	
	/**
	 * 在庫一覧のアイテムをタップした際に在庫詳細画面への遷移をViewへ要求する
	 */
	fun onStockItemClick(stock: Stock) {
		viewModelScope.launch { _message.emit(Message.MoveToDetailScreen(stock)) }
	}
	
	/**
	 * 在庫ソートボタンをタップした際に在庫をソートする
	 */
	fun onSortBtnClick() {
		viewModelScope.launch {
			useCase.switchSortType()
			useCase.sortStocks()
		}
	}
	
	/**
	 * 絞り込みダイアログのアイテムを選択した際に
	 * 表示する食材カテゴリーの選択状況を更新し
	 * 新しい条件に合わせて在庫情報を更新する
	 */
	fun onDialogItemClick(category: String, nextStatus: Boolean) {
		viewModelScope.launch {
			useCase.setCategoryStatus(category, nextStatus)
			useCase.updateStockListWithCondition()
			useCase.sortStocks()
		}
	}
	
	/**
	 * 現在選択されているソートの種類に応じてソートボタンのテキストを書き換える
	 */
	private fun updateSortBtnText() {
		_sortBtnText.value = when(useCase.currentSortType.value) {
			StockManagerUseCase.StockSortType.ID_ASC -> { application.applicationContext.getString(R.string.sm_sort_register_oder_asc) }
			StockManagerUseCase.StockSortType.ID_DESC -> { application.applicationContext.getString(R.string.sm_sort_register_oder_desc) }
		}
	}
	
	/**
	 * 絞り込みの状態に応じて絞り込みボタンのテキストを書き換える
	 */
	private fun updateSearchBtnText() {
		_searchBtnText.value =
			if (true in categories.value.values.toBooleanArray()) {
				application.applicationContext.getString(R.string.sm_search_by_category_on)
			} else {
				application.applicationContext.getString(R.string.sm_search_by_category_off)
			}
	}
	
	// 画面遷移をviewに知らせるメッセージ
	/*
	enum class Message {
		DISPLAY_SEARCH_DIALOG,
		MOVE_TO_REGISTER_SCREEN,
		MOVE_TO_DETAIL_SCREEN
	}
	 */
	
	sealed class Message {
		object DisplaySearchDialog : Message()
		object MoveToRegisterScreen : Message()
		
		data class MoveToDetailScreen(
			val stock: Stock
		) : Message()
	}
}