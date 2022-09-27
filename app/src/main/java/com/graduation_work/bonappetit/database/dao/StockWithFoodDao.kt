package com.graduation_work.bonappetit.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.graduation_work.bonappetit.database.entities.StockWithFoodView

@Dao
interface StockWithFoodDao {
	@Query("SELECT * FROM stock_with_food")
	suspend fun selectAll(): List<StockWithFoodView>
	
	@Query("SELECT * FROM stock_with_food WHERE name = :name")
	suspend fun selectByName(name: String): List<StockWithFoodView>
}