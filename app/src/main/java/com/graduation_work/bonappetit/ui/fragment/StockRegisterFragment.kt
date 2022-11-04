package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.StockRegisterBinding
import com.graduation_work.bonappetit.ui.view_model.StockRegisterViewModel
import com.graduation_work.bonappetit.ui.view_model.StockRegisterViewModel.Message
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockRegisterFragment : Fragment() {
	private val viewModel: StockRegisterViewModel by viewModel()
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockRegisterBinding.inflate(inflater, container, false).also {
			it.viewModel = viewModel
			it.lifecycleOwner = viewLifecycleOwner
			it.foodSelectorSpn.adapter = ArrayAdapter(
				requireContext(),
				android.R.layout.simple_spinner_item,
				viewModel.foodList.value.toTypedArray()
			).apply {
				setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
			}
			it.foodSelectorSpn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
				override fun onItemSelected(
					parent: AdapterView<*>?,
					view: View?,
					position: Int,
					id: Long
				) {
				
				}
				
				override fun onNothingSelected(parent: AdapterView<*>?) {
				
				}
			}
		}.also {
			viewModel.message
				.onEach { onMessage(it) }
				.launchIn(lifecycleScope)
		}.root
	}
	
	private fun onMessage(message: Message) {
		when(message) {
			Message.STOCK_LIST -> onMessageStockList()
		}
	}
	
	private fun onMessageStockList() {
		findNavController().navigate(R.id.action_register_to_main)
	}
}