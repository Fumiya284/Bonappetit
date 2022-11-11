package com.graduation_work.bonappetit.ui.fragment.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.graduation_work.bonappetit.ui.view_model.StockRegisterViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class DatePickerDFragment(private val viewModel: StockRegisterViewModel) : DialogFragment() {
	
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val now = ZonedDateTime.now(ZoneId.systemDefault())
		Log.d("my_info", "$now")
		Log.d("my_info", "${now.year}/${now.month}/${now.dayOfMonth}")
		return DatePickerDialog(
			requireContext(),
			OnLimitSetListener(viewModel),
			now.year,
			now.monthValue,
			now.dayOfMonth
		)
	}
	
	private class OnLimitSetListener(private val viewModel: StockRegisterViewModel) : DatePickerDialog.OnDateSetListener {
		
		override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
			val limit = LocalDate.of(year, month, dayOfMonth)
			viewModel.onLimitSet(limit)
		}
	}
}