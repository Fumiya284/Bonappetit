package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.ViewModel
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.use_case.StockRegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

class StockRegisterViewModel : ViewModel() {
	private val useCase: StockRegisterUseCase by inject(StockRegisterUseCase::class.java)
	private var selectedFood: Food? = null
	
	val foodList: StateFlow<List<Food>> = useCase.foodList
	
	private val _unit = MutableStateFlow<String>("あああ")
	val unit: StateFlow<String> = _unit
	
	private val _limitType = MutableStateFlow<String>("いいい")
	val limitType: StateFlow<String> = _limitType
	
	fun onFoodSelected(food: Food) {
	
	}
}