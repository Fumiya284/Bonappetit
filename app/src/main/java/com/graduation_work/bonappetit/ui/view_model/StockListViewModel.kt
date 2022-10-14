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
import kotlinx.coroutines.launch

class StockListViewModel : ViewModel() {
	private val useCase = StockUseCase(StockRepository())
	
	val stockList: StateFlow<List<Stock>> = useCase.list
	
	val targetName = MutableStateFlow<String>("")
	
	private val _isSearchByNameEnable = MutableStateFlow<Boolean>(false)
	val isSearchByNameEnable = _isSearchByNameEnable
	
	private val _sortType = MutableStateFlow<StockSortType>(StockSortType.ID_DESC)
	val sortType = _sortType
	
	init {
		targetName.onEach {
			updateDisplayItem()
		}.launchIn(viewModelScope)
	}
	
	private fun updateDisplayItem() {
		viewModelScope.launch {
			useCase.loadStocks(targetName.value)
		}
	}
}