package com.graduation_work.bonappetit.ui.adapter

import android.content.Context
import android.widget.ArrayAdapter
import com.graduation_work.bonappetit.R

class FoodSelectorAdapter(
	context: Context,
	items: List<String> = emptyList(),
	private val layout: Int = R.layout.food_selector_spinner
) : ArrayAdapter<String>(context, layout, items) {
	init {
		this.setDropDownViewResource(R.layout.food_selector_item)
	}
	
	fun getAdapterWithNewItem(items: List<String>): FoodSelectorAdapter {
		
		return FoodSelectorAdapter(context, items, layout)
	}
}