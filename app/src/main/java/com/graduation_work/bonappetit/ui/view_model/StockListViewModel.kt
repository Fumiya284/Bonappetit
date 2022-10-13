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
		viewModelScope.launch {
			useCase.loadStocks()
		}
	}
	
	fun searchStock() {
		viewModelScope.launch {
			useCase.searchStocksByName(targetName.value)
		}
	}
	
	fun updateSearchBtnStatus() {
		_isSearchByNameEnable.value = !targetName.value.isNullOrBlank()
		Log.d("my_info", "${targetName.value}")
	}
}