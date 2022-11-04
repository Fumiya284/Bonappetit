package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.StockManagerBinding
import com.graduation_work.bonappetit.ui.adapter.StockListAdapter
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel.Message
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockManagerFragment: Fragment() {
	private val viewModel: StockManagerViewModel by viewModel()
	private lateinit var stockListAdapter: StockListAdapter
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockManagerBinding.inflate(inflater, container, false).also {
			it.viewModel = viewModel
			it.lifecycleOwner = viewLifecycleOwner
			it.stockListRv.apply {
				layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
				stockListAdapter = StockListAdapter(viewLifecycleOwner, viewModel)
				adapter = stockListAdapter
			}
		}.also {
			viewModel.message
				.onEach { onMessage(it) }
				.launchIn(lifecycleScope)
			
			viewModel.stockList
				.onEach { stockListAdapter.submitList(it) }
				.launchIn(lifecycleScope)
		}.root
	}
	
	private fun onMessage(message: Message) {
		when (message) {
			Message.SEARCH -> onMessageSearch()
			Message.REGISTER -> onMessageRegister()
			Message.DETAIL -> {}
		}
	}
	
	private fun onMessageSearch() {
		CategorySelectDFragment(viewModel).show(parentFragmentManager, getString(R.string.sm_search_dialog_tag))
	}
	
	private fun onMessageRegister() {
		findNavController().navigate(R.id.action_manager_to_register)
	}
}