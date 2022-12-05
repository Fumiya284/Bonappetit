package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.AndroidViewModel
import com.graduation_work.bonappetit.MyApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StockDetailViewModel(
	private val application: MyApplication,
	private val stockId: Long
) : AndroidViewModel(application) {
	private val _test = MutableStateFlow<String>(stockId.toString())
	val test: StateFlow<String> = _test
}