package com.graduation_work.bonappetit.domain.repository

import com.graduation_work.bonappetit.data.database.entities.StockEntity

interface HistoryRepository {

    suspend fun fetchConsumptionAndWastedQuantity(): Map<String, Int>

    suspend fun fetchWastedQuantityByReason(): Map<String, Int>

    suspend fun fetchConsumptionQuantityByDate(): Map<String, Int>

    suspend fun fetchConsumedStock(): List<StockEntity>
}