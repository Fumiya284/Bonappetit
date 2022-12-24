package com.graduation_work.bonappetit.ui.view_model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.use_case.StockDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate

class StockDetailViewModel(
	private val myApp: MyApplication,
	private val stockId: Long
) : AndroidViewModel(myApp) {
	private val useCase: StockDetailUseCase by inject(StockDetailUseCase::class.java)
	
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
	
	private val _isSaveBtnEnable = MutableStateFlow<Boolean>(false)
	val isSaveBtnEnable: StateFlow<Boolean> = _isSaveBtnEnable
	
	private val _image =
		MutableStateFlow<Bitmap>(BitmapFactory.decodeResource(myApp.resources, R.drawable.no_image))
	val image: StateFlow<Bitmap> = _image
	
	init {
		viewModelScope.launch { useCase.loadStock(stockId) }
		
		useCase.stock
			.onEach { updateStockInfo(it) }
			.launchIn(viewModelScope)
		
		listOf(useCase.isNoteChanged, useCase.isQuantityChanged).forEach{ stateFlow ->
			stateFlow
				.onEach { updateStatus() }
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
	
	private fun updateStatus() {
		_quantityStatus.value =
			if (useCase.isQuantityChanged.value) { myApp.applicationContext.getString(R.string.sd_quantity_edited) } else { "" }
		
		_noteStatus.value =
			if (useCase.isNoteChanged.value) { myApp.applicationContext.getString(R.string.sd_note_edited) } else { "" }
		
		_isSaveBtnEnable.value = useCase.isNoteChanged.value || useCase.isQuantityChanged.value
	}
	
	private fun updateStockInfo(stock: Stock) {
		_name.value = stock.food.name
		_quantity.value = stock.quantity.toString()
		_unit.value = stock.food.unit
		_limit.value = stock.limit.toString()
		_note.value = stock.note
		_image.value = stock.food.image
	}
}