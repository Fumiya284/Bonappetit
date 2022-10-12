package com.graduation_work.bonappetit.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.data.repository.StockRepository
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.use_case.StockUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StockListViewModel : ViewModel() {
	private val useCase: StockUseCase = StockUseCase(StockRepository())
	
	val stockList: StateFlow<List<Stock>> = useCase.list
	
	init {
		viewModelScope.launch {
			useCase.loadStocks()
			Log.d("my_info", "init")
		}
	}
}