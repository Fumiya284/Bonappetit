package com.graduation_work.bonappetit.model.data

import java.time.LocalDate

data class StockList(
	val value: List<Stock> = emptyList()
) {
	fun addStock(stock: Stock): StockList {
		return StockList(value + stock)
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