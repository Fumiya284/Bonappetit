package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.databinding.StockListFragmentBinding
import com.graduation_work.bonappetit.ui.adapter.StockListAdapter
import com.graduation_work.bonappetit.ui.view_model.StockListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StockListFragment: Fragment() {
	private val viewModel = StockListViewModel()// DIにしないと画面回転するたびに新しいの作ることになる
	private lateinit var stockListAdapter: StockListAdapter
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockListFragmentBinding.inflate(inflater, container, false).also {
			it.viewModel = viewModel
			it.lifecycleOwner = viewLifecycleOwner
			
			it.stockListRv.apply {
				layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
				stockListAdapter = StockListAdapter(viewLifecycleOwner, viewModel)
				adapter = stockListAdapter
			}
		}.also {
			viewModel.stockList.onEach {
				stockListAdapter.submitList(it)
			}.launchIn(lifecycleScope)
		}.root
	}
}