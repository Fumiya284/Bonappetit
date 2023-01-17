package com.graduation_work.bonappetit.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import com.graduation_work.bonappetit.data.database.entities.StockWithFoodView

@Dao
interface StockWithFoodDao {
	@Query("SELECT * FROM stock_with_food WHERE stock_with_food.consumption_date IS NULL")
	suspend fun selectAll(): List<StockWithFoodView>
	
	@Query("SELECT * FROM stock_with_food WHERE id == :id")
	suspend fun selectById(id: Long): StockWithFoodView
	
	@Query("SELECT * FROM stock_with_food WHERE stock_with_food.consumption_date IS NULL AND food_name GLOB '*' || :searchString || '*'")
	suspend fun selectByName(searchString: String): List<StockWithFoodView>
	
	@Query("SELECT * FROM stock_with_food WHERE stock_with_food.consumption_date IS NULL AND category IN (:category)")
	suspend fun selectByCategory(vararg category: String): List<StockWithFoodView>
	
	@Query("SELECT * FROM stock_with_food WHERE stock_with_food.consumption_date IS NULL AND food_name GLOB '*' || :searchString || '*' AND category IN (:category)")
	suspend fun selectByNameAndCategory(searchString: String, vararg category: String): List<StockWithFoodView>
}