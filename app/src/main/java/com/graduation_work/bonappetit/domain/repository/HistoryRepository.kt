package com.graduation_work.bonappetit.domain.repository

import com.graduation_work.bonappetit.data.database.entities.StockEntity
import java.time.LocalDate

interface HistoryRepository {

    suspend fun fetchConsumptionAndWastedQuantity(): Map<String, Int>

    suspend fun fetchWastedQuantityByReason(): Map<String, Int>

    suspend fun fetchConsumptionQuantityByDate(month: LocalDate): Map<String, Int>

    suspend fun fetchConsumedStock(month: LocalDate): List<StockEntity>

    suspend fun fetchWastedQuantityByDate(month: LocalDate): Map<String, Int>

    suspend fun fetchWastedStock(month: LocalDate): List<StockEntity>
}