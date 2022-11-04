package com.graduation_work.bonappetit.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel
import org.koin.java.KoinJavaComponent.inject

class CategorySelectDFragment(private val viewModel: StockManagerViewModel) : DialogFragment() {
	
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val categoryList = viewModel.categoryList.value.keys.toTypedArray()
		val checkedItem = viewModel.categoryList.value.values.toBooleanArray()
		
		return AlertDialog.Builder(this.context).apply {
			setTitle(getString(R.string.sm_search_dialog_title))
			setMultiChoiceItems(categoryList, checkedItem) { _, which, isChecked ->
				viewModel.onDialogItemClick(categoryList[which], isChecked)
			}
		}.create()
	}
}