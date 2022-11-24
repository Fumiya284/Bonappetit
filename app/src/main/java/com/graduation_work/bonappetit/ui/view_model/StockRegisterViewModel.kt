package com.graduation_work.bonappetit.ui.view_model

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.use_case.StockRegisterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate

/*
	複雑になってしまう
	
	Editable.toString呼び出すとkaptが例外吐くのなんで？誰か教えて　何も調べてないけど
 */
class StockRegisterViewModel(private val application: MyApplication) : AndroidViewModel(application) {
	private val useCase: StockRegisterUseCase by inject(StockRegisterUseCase::class.java)
	private var chosenFood: Food? = null
	private var limit: LocalDate? = null
	
	val foods: StateFlow<List<Food>> = useCase.foods
	
	val quantityStr = MutableStateFlow<String>("")
	
	val note = MutableStateFlow<String>("")
	
	private val _currentStockText = MutableStateFlow<String>("")
	val currentStockText: StateFlow<String> = _currentStockText
	
	private val _unit = MutableStateFlow<String>("")
	val unit: StateFlow<String> = _unit
	
	private val _limitType = MutableStateFlow<String>("")
	val limitType: StateFlow<String> = _limitType
	
	private val _limitText = MutableStateFlow<String>(application.applicationContext.getString(R.string.sr_date_text))
	val limitText: StateFlow<String> = _limitText
	
	private val _isQuantityInputEnable = MutableStateFlow<Boolean>(false)
	val isQuantityInputEnable: StateFlow<Boolean> = _isQuantityInputEnable
	
	private val _isLimitSelectorEnable = MutableStateFlow<Boolean>(false)
	val isLimitSelectorEnable: StateFlow<Boolean> = _isLimitSelectorEnable
	
	private val _message = MutableSharedFlow<Message>()
	val message: SharedFlow<Message> = _message
	
	init {
		viewModelScope.launch { useCase.loadFoods() }
	}
	
	fun onDatePickerClick() {
		viewModelScope.launch { _message.emit(Message.DISPLAY_DATE_PICKER) }
	}
	
	fun onFoodChoice(food: Food?) {
		this.chosenFood = food
		updateUiStatus(chosenFood)
	}
	
	fun onLimitSet(limit: LocalDate) {
		this.limit = limit
		_limitText.value = limit.toString()
	}
	
	fun onCancelBtnClick() {
		viewModelScope.launch { _message.emit(Message.MOVE_TO_STOCK_MANAGER) }
	}
	
	fun onRegisterBtnClick() {
		val food = chosenFood
		val quantity = quantityStr.value.toIntOrNull()
		val note = note.value
		
		viewModelScope.launch {
			if (food == null) {
				_message.emit(Message.NOTIFY_NO_FOOD_SELECTED)
			} else if (quantity == null) {
				_message.emit(Message.NOTIFY_NO_QUANTITY_ENTERED)
			}else {
				when(useCase.register(food, quantity, limit, note)) {
					is Either.Right -> { _message.emit(Message.MOVE_TO_STOCK_MANAGER) }
					is Either.Left -> { _message.emit(Message.NOTIFY_FAILED_TO_REGISTER) }
				}
			}
		}
	}
	
	private fun updateUiStatus(chosenFood: Food?) {
		if (chosenFood != null) {
			_isLimitSelectorEnable.value = true
			_isQuantityInputEnable.value = true
			_unit.value = chosenFood.unit
			_limitType.value = chosenFood.limitType
			updateCurrentStockText(chosenFood)
		} else {
			_isLimitSelectorEnable.value = false
			_isQuantityInputEnable.value = false
			_unit.value = ""
			_limitType.value = ""
			_currentStockText.value = ""
		}
	}
	
	private fun updateCurrentStockText(chosenFood: Food) {
		viewModelScope.launch {
			useCase.loadRegisteredStock(chosenFood)
			val stockQuantity = useCase.registeredStock.value.sumOf { it.quantity }
			
			if (0 < stockQuantity) {
				_currentStockText.value = application.applicationContext.getString(R.string.sr_current_stock, stockQuantity, chosenFood.unit)
			} else {
				_currentStockText.value = application.applicationContext.getString(R.string.sr_current_stock_empty)
			}
		}
	}
	
	enum class Message {
		MOVE_TO_STOCK_MANAGER,
		DISPLAY_DATE_PICKER,
		NOTIFY_FAILED_TO_REGISTER,
		NOTIFY_NO_FOOD_SELECTED,
		NOTIFY_NO_QUANTITY_ENTERED,
	}
}