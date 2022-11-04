package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.use_case.StockRegisterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class StockRegisterViewModel(private val application: MyApplication) : AndroidViewModel(application) {
	private val useCase: StockRegisterUseCase by inject(StockRegisterUseCase::class.java)
	private var selectedFood: Food? = null
	
	val foodList: StateFlow<List<Food>> = useCase.foodList
	
	val foodCount = MutableStateFlow<String>("")
	
	private val _message = MutableSharedFlow<Message>()
	val message: SharedFlow<Message> = _message
	
	private val _unit = MutableStateFlow<String>("")
	val unit: StateFlow<String> = _unit
	
	private val _limitType = MutableStateFlow<String>("")
	val limitType: StateFlow<String> = _limitType
	
	private val _isFoodCountInputEnable = MutableStateFlow<Boolean>(false)
	val isFoodCountInputEnable: StateFlow<Boolean> = _isFoodCountInputEnable
	
	fun onFoodSelected(food: Food) {
		this.selectedFood = food
	}
	
	fun onRegisterBtnClick() {
		viewModelScope.launch { _message.emit(Message.STOCK_LIST) }
	}
	
	enum class Message {
		STOCK_LIST
	}
}