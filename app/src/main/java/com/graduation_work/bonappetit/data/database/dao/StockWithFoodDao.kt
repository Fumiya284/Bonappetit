package com.graduation_work.bonappetit.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import com.graduation_work.bonappetit.data.database.entities.StockWithFoodView

@Dao
interface StockWithFoodDao {
	@Query("SELECT * FROM stock_with_food")
	suspend fun selectAll(): List<StockWithFoodView>
	
	@Query("SELECT * FROM stock_with_food WHERE food_name GLOB '*' || :name || '*'")
	suspend fun selectByName(name: String): List<StockWithFoodView>
	
	// @Query("SELECT * FROM stock_with_food WHERE food_name GLOB :name")
}