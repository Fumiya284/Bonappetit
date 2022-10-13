package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation_work.bonappetit.databinding.StockListFragmentBinding
import com.graduation_work.bonappetit.ui.adapter.StockListAdapter
import com.graduation_work.bonappetit.ui.view_model.StockListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StockListFragment: Fragment() {
	private val viewModel = StockListViewModel() // DIにしないと画面回転するたびに新しいの作ることになる
	private lateinit var stockListAdapter: StockListAdapter
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockListFragmentBinding.inflate(inflater, container, false).also {
			it.viewModel = viewModel
			it.lifecycleOwner = viewLifecycleOwner
			
			it.stockList.apply {
				layoutManager = LinearLayoutManager(context)
				stockListAdapter = StockListAdapter(viewLifecycleOwner, viewModel)
				adapter = stockListAdapter
			}
		}.also {
			viewModel.stockList.onEach {    // onEachの挙動がよくわからない
				stockListAdapter.submitList(it)
			}.launchIn(lifecycleScope)
		}.root
	}
}