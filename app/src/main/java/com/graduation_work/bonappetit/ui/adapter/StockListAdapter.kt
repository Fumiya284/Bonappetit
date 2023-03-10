package com.graduation_work.bonappetit.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.StockListItemBinding
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit


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
				this.viewModel = viewModel
				
				val remain = ChronoUnit.DAYS.between(LocalDate.now(), item.limit) + 1
				if (remain <= 0) {
					background.background = ContextCompat.getDrawable(this.root.context, R.drawable.sm_expired_item)
					remainDate.text = "期限切れ"
				} else if (remain <= 3) {
					background.background = ContextCompat.getDrawable(this.root.context, R.drawable.sm_close_item)
					remainDate.text = """あと${remain}日"""
				} else {
					remainDate.text = """あと${remain}日"""
				}
				
				executePendingBindings()
			}
		}
	}
}