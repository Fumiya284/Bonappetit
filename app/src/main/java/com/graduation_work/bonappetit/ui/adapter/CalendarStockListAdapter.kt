package com.graduation_work.bonappetit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.databinding.CalendarStockListItemBinding

class CalendarStockListAdapter(val onClick: (StockEntity) -> Unit) :
    ListAdapter<StockEntity, CalendarStockListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CalendarStockListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

    inner class ViewHolder(private val binding: CalendarStockListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(item: StockEntity) {
            binding.itemEventText.text = item.foodName
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<StockEntity>() {
        override fun areItemsTheSame(oldItem: StockEntity, newItem: StockEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StockEntity, newItem: StockEntity): Boolean {
            return oldItem == newItem
        }
    }
}