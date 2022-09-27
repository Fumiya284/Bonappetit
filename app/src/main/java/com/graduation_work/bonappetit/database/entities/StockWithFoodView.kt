package com.graduation_work.bonappetit.database.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import java.time.LocalDate

@DatabaseView(
	viewName = "stock_with_food",
	value = "SELECT * FROM stock LEFT OUTER JOIN food ON stock.food_name = food.name"
)
data class StockWithFoodView(
	val id: Long,
	@ColumnInfo(name = "name")
	val foodName: String,
	val unit: String,
	val count: Int,
	val limit: LocalDate? = null,
	@ColumnInfo(name = "best_or_expiry")
	val bestOrExpiry: Boolean? = null
){
	fun toStockEntity(): StockEntity {
		return StockEntity(id, foodName, count, limit, bestOrExpiry)
	}
}