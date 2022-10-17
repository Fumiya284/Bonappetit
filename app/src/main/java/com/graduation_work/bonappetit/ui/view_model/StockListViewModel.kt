package com.graduation_work.bonappetit.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.data.repository.StockRepository
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.enums.StockSortType
import com.graduation_work.bonappetit.domain.use_case.StockUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onErrorResume
import kotlinx.coroutines.launch

class StockListViewModel : ViewModel() {
	private val useCase = StockUseCase(StockRepository())
	private var currentSortType = StockSortType.ID_ASC
	
	val searchString = MutableStateFlow<String>("")
	
	val stockList: StateFlow<List<Stock>> = useCase.list
	
	private val _sortTypeText = MutableStateFlow<String>(currentSortType.text)
	val sortTypeText = _sortTypeText
	
	init {
		searchString.onEach {
			updateDisplayItem()
		}.launchIn(viewModelScope)
	}
	
	fun switchSortType() {
		currentSortType = if (currentSortType == StockSortType.ID_ASC) {
			StockSortType.ID_DESC
		} else {
			StockSortType.ID_ASC
		}
		
		useCase.sortStocks(currentSortType)
		_sortTypeText.value = currentSortType.text
	}
	
	private fun updateDisplayItem() {
		viewModelScope.launch {
			useCase.run {
				loadStocks(searchString.value)
				sortStocks(currentSortType)
			}
		}
	}
}