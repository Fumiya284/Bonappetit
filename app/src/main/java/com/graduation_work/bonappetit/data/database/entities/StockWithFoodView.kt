package com.graduation_work.bonappetit.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.dto.Limit
import com.graduation_work.bonappetit.domain.dto.Stock
import java.time.LocalDate

@DatabaseView(
	viewName = "stock_with_food",
	value = "SELECT stock.id, stock.food_name, stock.count, stock.`limit`, stock.limit_type, food.unit, food.category FROM stock LEFT OUTER JOIN food ON stock.food_name = food.name"
)
data class StockWithFoodView(
	val id: Long,
	val category: String,
	@ColumnInfo(name = "food_name")
	val foodName: String,
	val unit: String,
	val count: Int,
	val limit: LocalDate? = null,
	@ColumnInfo(name = "limit_type")
	val limitType: String? = null
) {
	fun convertToStock(): Stock {
		return if (limit != null && limitType != null) {
			Stock(id, Food(foodName, unit, category), count, Limit(limit, limitType))
		} else {
			Stock(id, Food(foodName, unit, category), count, null)
		}
	}
}