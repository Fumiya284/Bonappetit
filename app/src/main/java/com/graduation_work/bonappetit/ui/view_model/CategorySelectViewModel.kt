package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.domain.use_case.StockManagerUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class CategorySelectViewModel : ViewModel() {
	private val useCase: StockManagerUseCase by inject(StockManagerUseCase::class.java)
	
	val categoryList: StateFlow<Map<String, Boolean>> = useCase.categories
	
	fun onDialogItemClick(category: String, nextStatus: Boolean) {
		viewModelScope.launch {
			useCase.setCategoryStatus(category, nextStatus)
			useCase.updateStockList()
			useCase.sortStocks()
		}
	}
}