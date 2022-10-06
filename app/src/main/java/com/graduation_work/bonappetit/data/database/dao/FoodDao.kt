package com.graduation_work.bonappetit.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.graduation_work.bonappetit.data.database.entities.FoodEntity

@Dao
interface FoodDao {
    @Insert
    suspend fun insertFood(foodEntity: FoodEntity): Long

    @Update
    suspend fun updateFood(foodEntity: FoodEntity)

    @Delete
    suspend fun deleteFood(foodEntity: FoodEntity)

    @Query("DELETE FROM food")
    suspend fun deleteAll()

    @Query("SELECT * FROM food WHERE name == :name")
    suspend fun selectByName(name: String): FoodEntity

    @Query("SELECT * FROM food")
    suspend fun selectAll(): List<FoodEntity>
}