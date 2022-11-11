package com.graduation_work.bonappetit.ui.view_model

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
	食材選択と個数入力だけ作った
	期間入力は作成中
	入力不足とかDBに登録失敗したとかのメッセージも後でつくる
	
	Editable.toString呼び出すとkaptが例外吐くのなんで？誰か教えて　何も調べてないけど
 */
class StockRegisterViewModel(application: MyApplication) : AndroidViewModel(application) {
	private val useCase: StockRegisterUseCase by inject(StockRegisterUseCase::class.java)
	private var chosenFood: Food? = null
	private var limit: LocalDate? = null
	
	val foods: StateFlow<List<Food>> = useCase.foods
	
	val quantityStr = MutableStateFlow<String>("")
	
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
		updateStatus(food)
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
		
		viewModelScope.launch {
			if (food == null) {
				_message.emit(Message.DISPLAY_ALERT)
			} else if (quantity == null) {
				_message.emit(Message.DISPLAY_ALERT)
			}else {
				when(useCase.register(food, quantity, limit)) {
					is Either.Right -> { _message.emit(Message.MOVE_TO_STOCK_MANAGER) }
					is Either.Left -> { _message.emit(Message.DISPLAY_ALERT) }
				}
			}
		}
	}
	
	private fun updateStatus(chosenFood: Food?) {
		if (chosenFood != null) {
			_isLimitSelectorEnable.value = true
			_isQuantityInputEnable.value = true
			_unit.value = chosenFood.unit
			_limitType.value = chosenFood.limitType
		} else {
			_isLimitSelectorEnable.value = false
			_isQuantityInputEnable.value = false
			_unit.value = ""
			_limitType.value = ""
		}
	}
	
	enum class Message {
		MOVE_TO_STOCK_MANAGER,
		DISPLAY_DATE_PICKER,
		DISPLAY_ALERT
	}
}