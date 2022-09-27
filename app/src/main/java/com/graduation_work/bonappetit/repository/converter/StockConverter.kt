package com.graduation_work.bonappetit.repository.converter

import com.graduation_work.bonappetit.database.entities.StockEntity
import com.graduation_work.bonappetit.database.entities.StockWithFoodView
import com.graduation_work.bonappetit.model.data.Limit
import com.graduation_work.bonappetit.model.data.Stock

object StockConverter {
	fun toStock(entity: StockWithFoodView): Stock {
		return if(entity.limit != null && entity.bestOrExpiry != null) {
			Stock(
				entity.id,
				entity.foodName,
				entity.count,
				entity.unit,
				Limit(
					entity.limit,
					entity.bestOrExpiry
				)
			)
		} else {
			Stock(
				entity.id,
				entity.foodName,
				entity.count,
				entity.unit
			)
		}
	}
	
	fun toEntity(stock: Stock): StockEntity {
		return if(stock.limit != null) {
			StockEntity.create4Insert(
				stock.foodName,
				stock.count,
				stock.limit.date,
				stock.limit.bestOrExpiry
			)
		} else {
			StockEntity.create4Insert(
				stock.foodName,
				stock.count
			)
		}
	}
}