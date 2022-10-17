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
	
	val searchString = MutableStateFlow<String>("")
	
	val stockList: StateFlow<List<Stock>> = useCase.list
	
	private val _sortTypeText = MutableStateFlow<String>(StockSortType.ID_ASC.text)
	val sortTypeText = _sortTypeText
	
	private val currentSortType = MutableStateFlow<StockSortType>(StockSortType.ID_ASC)
	
	init {
		searchString.onEach {
			updateDisplayItem()
		}.launchIn(viewModelScope)
		
		currentSortType.onEach {
			sortDisplayItem()
		}.launchIn(viewModelScope)
	}
	
	fun switchSortType() {
		currentSortType.value = if (currentSortType.value == StockSortType.ID_ASC) {
			StockSortType.ID_DESC
		} else {
			StockSortType.ID_ASC
		}
		
		_sortTypeText.value = currentSortType.value.text
	}
	
	private fun sortDisplayItem() {
		useCase.sortStocks(currentSortType.value)
	}
	
	private fun updateDisplayItem() {
		viewModelScope.launch {
			useCase.loadStocks(searchString.value)
			useCase.sortStocks(currentSortType.value)
		}
	}
}