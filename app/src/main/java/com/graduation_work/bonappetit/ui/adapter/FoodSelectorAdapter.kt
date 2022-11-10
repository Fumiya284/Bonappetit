package com.graduation_work.bonappetit.ui.adapter

import android.content.Context
import android.widget.ArrayAdapter

class FoodSelectorAdapter(
	context: Context,
	items: List<String> = emptyList(),
	private val layout: Int = android.R.layout.simple_spinner_item
) : ArrayAdapter<String>(context, layout, items) {
	
	fun getAdapterWithNewItem(items: List<String>): FoodSelectorAdapter {
		return FoodSelectorAdapter(context, items, layout)
	}
}