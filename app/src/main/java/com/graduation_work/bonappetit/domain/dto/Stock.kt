package com.graduation_work.bonappetit.domain.dto

import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.data.database.entities.StockWithFoodView
import java.time.LocalDate

data class Stock(
	val id: Long,
	val foodName: String,
	val unit: String,
	val count: Int,
	val limit: Limit?
) {
	fun toStockEntity(): StockEntity {
		return if(this.limit == null) {
			StockEntity.create4Insert(
				foodName,
				count
			)
		} else {
			StockEntity.create4Insert(
				foodName,
				count,
				limit.date,
				limit.bestOrExpiry
			)
		}
	}
	
	companion object {
		fun fromView(stockWithFoodView: StockWithFoodView): Stock {
			return if(stockWithFoodView.limit != null && stockWithFoodView.bestOrExpiry != null) {
				Stock(
					stockWithFoodView.id,
					stockWithFoodView.foodName,
					stockWithFoodView.unit,
					stockWithFoodView.count,
					Limit(stockWithFoodView.limit, stockWithFoodView.bestOrExpiry)
				)
			} else {
				Stock(
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