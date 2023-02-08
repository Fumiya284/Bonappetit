package com.graduation_work.bonappetit.data.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.domain.repository.CalendarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalendarRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CalendarRepository {

    private val database = MyApplication.database
    private val stockDao = database.stockDao()

    override suspend fun fetchStockListByThisMonth(first: String, last: String): List<StockEntity> {
        return withContext(dispatcher) {
            stockDao.selectStockListByThisMonth(first, last)
        }
    }
}