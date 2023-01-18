package com.graduation_work.bonappetit.ui.view_model

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.dto.Recipe
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.RecipeRepository
import com.graduation_work.bonappetit.domain.use_case.StockDetailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate

class StockDetailViewModel(
	private val myApp: MyApplication,
	private val stockId: Long
) : AndroidViewModel(myApp) {
	private val useCase: StockDetailUseCase by inject(StockDetailUseCase::class.java)
	private lateinit var stock: Stock
	
	private val _name = MutableStateFlow<String>("")
	val name: StateFlow<String> = _name
	
	private val _quantity = MutableStateFlow<String>("")
	val quantity: StateFlow<String> = _quantity
	
	private val _quantityStatus = MutableStateFlow<String>("")
	val quantityStatus: StateFlow<String> = _quantityStatus
	
	private val _unit = MutableStateFlow<String>("")
	val unit: StateFlow<String> = _unit
	
	private val _limit = MutableStateFlow<String>("0000-00-00")
	val limit: StateFlow<String> = _limit
	
	private val _note = MutableStateFlow<String>("")
	val note: StateFlow<String> = _note
	
	private val _noteStatus = MutableStateFlow<String>("")
	val noteStatus: StateFlow<String> = _noteStatus
	
	private val _recipe = MutableStateFlow<List<Recipe>>(emptyList())
	val recipe: StateFlow<List<Recipe>> = _recipe
	
	private val _isSaveBtnEnable = MutableStateFlow<Boolean>(false)
	val isSaveBtnEnable: StateFlow<Boolean> = _isSaveBtnEnable
	
	private val _image =
		MutableStateFlow<Bitmap>(BitmapFactory.decodeResource(myApp.resources, R.drawable.no_image))
	val image: StateFlow<Bitmap> = _image
	
	private val _message = MutableSharedFlow<Message>()
	val message = _message.asSharedFlow()
	
	init {
		viewModelScope.launch { useCase.loadStock(stockId) }
		
		useCase.stock
			.onEach { updateStockInfo(it) }
			.launchIn(viewModelScope)
		
		useCase.recipe
			.onEach { _recipe.value = it }
			.launchIn(viewModelScope)
		
		listOf(useCase.isNoteChanged, useCase.isQuantityChanged).forEach{ stateFlow ->
			stateFlow
				.onEach { updateSaveBtnStatus() }
				.launchIn(viewModelScope)
		}
	}
	
	fun onQuantityChange(editable: Editable) {
		val quantity = editable.toString().toIntOrNull() ?: return
		useCase.setQuantity(quantity)
	}
	
	fun onNoteChange(editable: Editable) {
		val note = editable.toString()
		useCase.setNote(note)
	}
	
	fun onSaveBtnClick() {
		viewModelScope.launch {
			useCase.saveEdits()
			useCase.loadStock(stockId)
		}
	}
	
	fun onConsumeBtnClick() {
		val now = LocalDate.now()
		if (stock.limit <= now) {
			viewModelScope.launch { _message.emit(Message.DisplayLimitExceedDialog) }
			return
		}
		markAsConsumed(null)
	}
	
	fun onRecipeItemClick(urlStr: String) {
		val url = Uri.parse(urlStr)
		viewModelScope.launch { _message.emit(Message.OpenUrl(url)) }
	}
	
	fun onCancel() {
		viewModelScope.launch { _message.emit(Message.MoveToManager) }
	}
	
	fun markAsConsumed(reasonForWasted: String?) {
		viewModelScope.launch {
			useCase.markAsConsumed(reasonForWasted)
			_message.emit(Message.MoveToManager)
		}
	}
	
	private fun updateSaveBtnStatus() {
		_quantityStatus.value =
			if (useCase.isQuantityChanged.value) { myApp.applicationContext.getString(R.string.sd_quantity_edited) } else { "" }
		
		_noteStatus.value =
			if (useCase.isNoteChanged.value) { myApp.applicationContext.getString(R.string.sd_note_edited) } else { "" }
		
		_isSaveBtnEnable.value = useCase.isNoteChanged.value || useCase.isQuantityChanged.value
	}
	
	private fun updateStockInfo(stock: Stock) {
		_name.value = "${myApp.getString(R.string.sd_name)}:${stock.food.name}"
		_quantity.value = stock.quantity.toString()
		_unit.value = stock.food.unit
		_limit.value = "${stock.food.limitType}:${stock.limit}"
		_note.value = stock.note
		_image.value = stock.food.image
		
		this.stock = stock
	}
	
	sealed class Message {
		object DisplayLimitExceedDialog : Message()
		object MoveToManager : Message()
		
		data class OpenUrl(
			val url: Uri
		) : Message()
	}
}