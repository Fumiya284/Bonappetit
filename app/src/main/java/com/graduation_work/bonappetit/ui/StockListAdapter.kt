package com.graduation_work.bonappetit.ui

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.databinding.StockListFragmentBinding
import com.graduation_work.bonappetit.domain.dto.StockDto

private object DiffCallback : DiffUtil.ItemCallback<StockDto>() {
	override fun areItemsTheSame(oldItem: StockDto, newItem: StockDto): Boolean {
		return oldItem.id == newItem.id
	}
	
	override fun areContentsTheSame(oldItem: StockDto, newItem: StockDto): Boolean {
		return oldItem == newItem
	}
}

class StockListAdapter(
	private val viewLifecycleOwner: LifecycleOwner,
	private val viewModel: StockListViewModel
) : ListAdapter<StockDto, StockListAdapter.StockListViewHolder>(DiffCallback) {

	class StockListViewHolder(private val binding: StockListFragmentBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(item: StockDto, viewLifecycleOwner: LifecycleOwner, viewModel: StockListViewModel) {
			binding.run {
				lifecycleOwner = viewLifecycleOwner
			}
		}
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockListViewHolder {
		TODO("Not yet implemented")
	}
	
	override fun onBindViewHolder(holder: StockListViewHolder, position: Int) {
		TODO("Not yet implemented")
	}
}