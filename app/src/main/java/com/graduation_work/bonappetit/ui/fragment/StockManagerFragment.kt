package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.databinding.StockManagerFragmentBinding
import com.graduation_work.bonappetit.ui.adapter.StockListAdapter
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel.Message
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent.inject

class StockManagerFragment: Fragment() {
	private val viewModel: StockManagerViewModel by inject(StockManagerViewModel::class.java)
	private lateinit var stockListAdapter: StockListAdapter
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockManagerFragmentBinding.inflate(inflater, container, false).also {
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
			is Message.Search -> onMessageSearch(message)
			else -> {}  // 参考にしたコードは拡張関数つくってelse句排除してたけどなんでできるのか分らないからとりあえず空のブロック置いとく 助けてください
		}
	}
	
	private fun onMessageSearch(message: Message.Search) {
		CategorySelectDFragment().show(parentFragmentManager, "Search Dialog")
	}
}