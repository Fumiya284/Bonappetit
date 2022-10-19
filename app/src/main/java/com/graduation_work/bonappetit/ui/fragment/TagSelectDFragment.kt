package com.graduation_work.bonappetit.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class TagSelectDFragment: DialogFragment() {
	
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		AlertDialog.Builder(activity).apply {
			setTitle("Select Tag.")
			
		}
		
		return super.onCreateDialog(savedInstanceState)
	}
}