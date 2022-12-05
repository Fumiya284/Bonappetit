package com.graduation_work.bonappetit.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.databinding.StockListItemBinding
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.generated.callback.OnClickListener
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel


class StockListAdapter(
	private val viewLifecycleOwner: LifecycleOwner,
	private val viewModel: StockManagerViewModel
) : ListAdapter<Stock, StockListAdapter.StockViewHolder>(DiffCallback) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = StockListItemBinding.inflate(layoutInflater, parent, false)
		
		return StockViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
		holder.bind(getItem(position), viewLifecycleOwner, viewModel)
	}
	
	private object DiffCallback : DiffUtil.ItemCallback<Stock>() {
		override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
			return oldItem.id == newItem.id
		}
		
		override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
			return oldItem == newItem
		}
	}

	class StockViewHolder(private val binding: StockListItemBinding) : RecyclerView.ViewHolder(binding.root) {
		/**
		 * ViewHolderがバインドされる時に呼び出し、
		 * RecyclerViewに表示するViewの初期設定を行う
		 */
		fun bind(item: Stock, viewLifecycleOwner: LifecycleOwner, viewModel: StockManagerViewModel) {
			binding.run {
				lifecycleOwner = viewLifecycleOwner
				stock = item
				this.viewModel = viewModel //現状viewModelはつかってない　こっちはあとで使う
				
				executePendingBindings()
			}
		}
	}
}