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
        select * from (
            select "消費" as `key`, count(*) as quantity from stock where consumption_date <= `limit`
            union all
            select "廃棄" as `key`, count(*) as quantity from stock where consumption_date > `limit`
        )
        where quantity > 0
    """)
    suspend fun selectConsumptionAndWastedQuantity(): Map<String, Int>

    @MapInfo(keyColumn = "date", valueColumn = "quantity")
    @Query("""
        select consumption_date as date, count(*) as quantity from stock
        where consumption_date <= `limit`
        group by consumption_date
    """)
    suspend fun selectConsumptionQuantityByDate(): Map<String, Int>

    @Query("SELECT * FROM stock where consumption_date <= `limit`")
    suspend fun selectConsumedStock(): List<StockEntity>

    @MapInfo(keyColumn = "date", valueColumn = "quantity")
    @Query("""
        select consumption_date as date, count(*) as quantity from stock
        where consumption_date > `limit`
        group by consumption_date
    """)
    suspend fun selectWastedQuantityByDate(): Map<String, Int>

    @Query("SELECT * FROM stock where consumption_date > `limit`")
    suspend fun selectWastedStock(): List<StockEntity>
}