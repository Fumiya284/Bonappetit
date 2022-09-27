package com.graduation_work.bonappetit.model.data

import com.graduation_work.bonappetit.model.SortType
import java.time.LocalDate

data class StockList(
	val value: List<Stock> = emptyList()
) {
	
	fun sort(type: SortType): StockList {
		val sortedList = when(type) {
			SortType.ID_ASC -> {
				value.sortedWith(compareBy { it.stockId })
			}
			SortType.ID_DESC -> {
				value.sortedWith(compareByDescending { it.stockId })
			}
		}
		
		return StockList(sortedList)
	}
}

data class Stock(
	val stockId: Long,
	val foodName: String,
	val count: Int,
	val unit: String,
	val limit: Limit? = null
)

data class Limit(
	val date: LocalDate,
	val bestOrExpiry: Boolean
)