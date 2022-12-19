package com.graduation_work.bonappetit.data.database.dao

import androidx.room.Dao
import androidx.room.MapInfo
import androidx.room.Query

@Dao
interface WastedHistoryDao {

    @MapInfo(keyColumn = "reason", valueColumn = "quantity")
    @Query("select reason, count(*) as quantity from wasted_history group by reason")
    suspend fun selectWastedQuantityByReason(): Map<String, Int>
}