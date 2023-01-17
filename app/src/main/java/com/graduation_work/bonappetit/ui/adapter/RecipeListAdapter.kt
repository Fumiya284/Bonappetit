package com.graduation_work.bonappetit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.databinding.RecipeItemBinding
import com.graduation_work.bonappetit.domain.dto.Recipe
import com.graduation_work.bonappetit.ui.view_model.StockDetailViewModel

class RecipeListAdapter( val viewModel: StockDetailViewModel) : ListAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(DiffCallback) {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = RecipeItemBinding.inflate(layoutInflater, parent, false)
		
		return RecipeViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
		holder.bind(getItem(position), viewModel)
	}
	
	private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
		override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
			return oldItem.name == newItem.name
		}
		
		override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
			return oldItem == newItem
		}
	}
	
	class RecipeViewHolder(private val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root) {
		fun bind(item: Recipe, viewModel: StockDetailViewModel) {
			binding.run {
				recipe = item
				this.viewModel = viewModel
				executePendingBindings()
			}
		}
	}
}