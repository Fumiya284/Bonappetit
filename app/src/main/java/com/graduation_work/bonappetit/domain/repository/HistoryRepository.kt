package com.graduation_work.bonappetit.domain.repository

interface HistoryRepository {

    suspend fun fetchConsumptionAndWastedQuantity(): Map<String, Int>
}