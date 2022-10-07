package com.graduation_work.bonappetit.domain.dto

import com.graduation_work.bonappetit.data.database.entities.StockWithFoodView
import java.time.LocalDate

data class StockDto(
	val id: Long,
	val foodName: String,
	val unit: String,
	val count: Int,
	val limit: Limit?
) {
	companion object {
		fun fromEntity(stockWithFoodView: StockWithFoodView): StockDto {
			return if(stockWithFoodView.limit != null && stockWithFoodView.bestOrExpiry != null) {
				StockDto(
					stockWithFoodView.id,
					stockWithFoodView.foodName,
					stockWithFoodView.unit,
					stockWithFoodView.count,
					Limit(stockWithFoodView.limit, stockWithFoodView.bestOrExpiry)
				)
			} else {
				StockDto(
					stockWithFoodView.id,
					stockWithFoodView.foodName,
					stockWithFoodView.unit,
					stockWithFoodView.count,
					null
				)
			}
		}
	}
}

data class Limit(
	val date: LocalDate,
	val bestOrExpiry: String
)