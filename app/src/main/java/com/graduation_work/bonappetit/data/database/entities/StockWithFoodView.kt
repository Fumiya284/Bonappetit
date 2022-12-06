package com.graduation_work.bonappetit.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.dto.Stock
import java.time.LocalDate

@DatabaseView(
	viewName = "stock_with_food",
	value = "SELECT stock.id, stock.food_name, stock.quantity, stock.`limit`, stock.note, food.limit_type, food.unit, food.category FROM stock LEFT OUTER JOIN food ON stock.food_name = food.name"
)
data class StockWithFoodView(
	val id: Long,
	val category: String,
	@ColumnInfo(name = "food_name")
	val foodName: String,
	val unit: String,
	val quantity: Int,
	val note: String? = null,
	val limit: LocalDate,
	@ColumnInfo(name = "limit_type")
	val limitType: String
) {
	fun convertToStock(): Stock {
		return Stock(id, Food(foodName, unit, category, limitType), quantity, limit, note)
	}
}