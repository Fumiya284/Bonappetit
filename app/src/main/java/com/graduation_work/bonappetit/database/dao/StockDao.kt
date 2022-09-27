package com.graduation_work.bonappetit.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.graduation_work.bonappetit.database.entities.StockEntity

@Dao
interface StockDao {
    @Insert
    suspend fun insert(stockEntity: StockEntity): Long

    @Update
    suspend fun update(stockEntity: StockEntity)

    @Delete
    suspend fun delete(stockEntity: StockEntity)

    @Query("DELETE FROM stock")
    suspend fun deleteAll()
}