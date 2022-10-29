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
    suspend fun insert(foodEntity: FoodEntity): Long
    
    @Query("DELETE FROM food")
    suspend fun deleteAll()
    
    @Query("SELECT DISTINCT category FROM food")   // categoryは重複する　別のテーブルにするべきかもしれないがカラム一つしかできないからめんどくささが勝る
    suspend fun selectAllCategory(): List<String>
    
    @Query("SELECT * FROM food")
    suspend fun selectAll(): List<FoodEntity>
}