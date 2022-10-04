package com.graduation_work.bonappetit.model.data

import com.graduation_work.bonappetit.model.enums.StockSortType
import java.time.LocalDate

data class StockList(
	val value: List<Stock> = emptyList()
) {
	fun sort(type: StockSortType): StockList {
		val sortedList = when(type) {
			StockSortType.ID_ASC -> {
				value.sortedWith(compareBy { it.stockId })
			}
			StockSortType.ID_DESC -> {
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
	val bestOrExpiry:String
)