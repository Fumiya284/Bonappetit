package com.graduation_work.bonappetit.domain.repository

import com.graduation_work.bonappetit.data.database.entities.StockEntity

interface CalendarRepository {

    suspend fun fetchStockListByThisMonth(first: String, last: String): List<StockEntity>
}