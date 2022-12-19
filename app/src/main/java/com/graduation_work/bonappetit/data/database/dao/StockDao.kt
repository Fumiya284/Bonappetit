package com.graduation_work.bonappetit.data.database.dao

import androidx.room.*
import com.graduation_work.bonappetit.data.database.entities.StockEntity

@Dao
interface StockDao {
    @Insert
    suspend fun insert(stockEntity: StockEntity)

    @Update
    suspend fun update(stockEntity: StockEntity)

    @Delete
    suspend fun delete(stockEntity: StockEntity)

    @Query("DELETE FROM stock")
    suspend fun deleteAll()

    @MapInfo(keyColumn = "key", valueColumn = "quantity")
    @Query("""
        select "消費" as `key`,count(*) as quantity from stock where consumption_date <= `limit`
        union all
        select "廃棄" as `key`, count(*) as quantity from stock where consumption_date > `limit`
    """)
    suspend fun selectConsumptionAndWastedQuantity(): Map<String, Int>

    @MapInfo(keyColumn = "date", valueColumn = "quantity")
    @Query("""
        select consumption_date as date, count(*) as quantity from stock
        where consumption_date <= `limit`
        group by consumption_date
    """)
    suspend fun selectConsumptionQuantityByDate(): Map<String, Int>
}