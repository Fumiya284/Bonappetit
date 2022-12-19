package com.graduation_work.bonappetit.data.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.domain.repository.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : HistoryRepository {

    private val database = MyApplication.database
    private val stockDao = database.stockDao()
    private val wastedHistoryDao = database.wastedHistoryDao()

    override suspend fun fetchConsumptionAndWastedQuantity(): Map<String, Int> {
        return withContext(dispatcher) {
            stockDao.selectConsumptionAndWastedQuantity()
        }
    }

    override suspend fun fetchWastedQuantityByReason(): Map<String, Int> {
        return withContext(dispatcher) {
            wastedHistoryDao.selectWastedQuantityByReason()
        }
    }

    override suspend fun fetchConsumptionQuantityByDate(): Map<String, Int> {
        return withContext(dispatcher) {
            stockDao.selectConsumptionQuantityByDate()
        }
    }

    override suspend fun fetchConsumedStock(): List<StockEntity> {
        return withContext(dispatcher) {
            stockDao.selectConsumedStock()
        }
    }
}
