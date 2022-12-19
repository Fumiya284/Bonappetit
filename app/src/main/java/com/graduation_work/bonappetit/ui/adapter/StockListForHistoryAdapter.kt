package com.graduation_work.bonappetit.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.databinding.StockListForHistoryItemBinding
import java.time.format.DateTimeFormatter

class StockListForHistoryAdapter(private val state: String) :
    ListAdapter<StockEntity, StockListForHistoryAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        private val binding: StockListForHistoryItemBinding,
        private val state: String
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: StockEntity) {
            binding.foodName.text = item.foodName
            binding.limit.text = "期限：${item.limit.format(DateTimeFormatter.ISO_DATE)}"
            binding.consumptionDate.text = "$state：${item.consumptionDate?.format(DateTimeFormatter.ISO_DATE)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StockListForHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            state
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
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
