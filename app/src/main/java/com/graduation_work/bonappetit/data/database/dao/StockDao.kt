package com.graduation_work.bonappetit.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.graduation_work.bonappetit.data.database.entities.StockEntity

@Dao
interface StockDao {
    @Insert
    suspend fun insert(stockEntity: StockEntity)

    @Delete
    suspend fun delete(stockEntity: StockEntity)

    @Query("DELETE FROM stock")
    suspend fun deleteAll()
    
    @Query("UPDATE stock SET quantity = :quantity WHERE id == :id")
    suspend fun updateQuantityById(quantity: Int, id: Long)
    
    @Query("UPDATE stock SET note = :note WHERE id == :id")
    suspend fun updateNoteById(note: String, id: Long)
}