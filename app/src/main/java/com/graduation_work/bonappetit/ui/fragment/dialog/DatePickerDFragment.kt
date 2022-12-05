package com.graduation_work.bonappetit.ui.fragment.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.graduation_work.bonappetit.ui.view_model.StockRegisterViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class DatePickerDFragment(private val viewModel: StockRegisterViewModel) : DialogFragment() {
	
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val now = ZonedDateTime.now(ZoneId.systemDefault())
		return DatePickerDialog(
			requireContext(),
			OnLimitSetListener(viewModel),
			now.year,
			now.monthValue - 1, // 0~11で指定しなきゃいけないらしい
			now.dayOfMonth
		)
	}
	
	private class OnLimitSetListener(private val viewModel: StockRegisterViewModel) : DatePickerDialog.OnDateSetListener {
		
		override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
			val limit = LocalDate.of(year, month + 1, dayOfMonth) // monthは0~11で入ってくる
			viewModel.onLimitSet(limit)
		}
	}
}