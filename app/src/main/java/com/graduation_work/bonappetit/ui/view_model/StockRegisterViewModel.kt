package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.use_case.StockRegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

class StockRegisterViewModel(private val application: MyApplication) : AndroidViewModel(application) {
	private val useCase: StockRegisterUseCase by inject(StockRegisterUseCase::class.java)
	private var selectedFood: Food? = null
	
	val foodList: StateFlow<List<Food>> = useCase.foodList
	
	val foodCount = MutableStateFlow<String>("")
	
	private val _unit = MutableStateFlow<String>("")
	val unit: StateFlow<String> = _unit
	
	private val _limitType = MutableStateFlow<String>("")
	val limitType: StateFlow<String> = _limitType
	
	private val _isFoodCountInputEnable = MutableStateFlow<Boolean>(false)
	val isFoodCountInputEnable: StateFlow<Boolean> = _isFoodCountInputEnable
	
	fun onFoodSelected(food: Food) {
		this.selectedFood = food
	}
}