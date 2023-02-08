package com.graduation_work.bonappetit.data.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.domain.repository.HistoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    override suspend fun fetchConsumptionQuantityByDate(month: LocalDate): Map<String, Int> {
        return withContext(dispatcher) {
            stockDao.selectConsumptionQuantityByDate(getFirstDayOfMonth(month), getLastDayOfMonth(month))
        }
    }

    override suspend fun fetchConsumedStock(month: LocalDate): List<StockEntity> {
        return withContext(dispatcher) {
            stockDao.selectConsumedStock(getFirstDayOfMonth(month), getLastDayOfMonth(month))
        }
    }

    override suspend fun fetchWastedQuantityByDate(month: LocalDate): Map<String, Int> {
        return withContext(dispatcher) {
            stockDao.selectWastedQuantityByDate(getFirstDayOfMonth(month), getLastDayOfMonth(month))
        }
    }

    override suspend fun fetchWastedStock(month: LocalDate): List<StockEntity> {
        return withContext(dispatcher) {
            stockDao.selectWastedStock(getFirstDayOfMonth(month), getLastDayOfMonth(month))
        }
    }

    private fun getFirstDayOfMonth(month: LocalDate): String {
        return month.withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE)
    }

    private fun getLastDayOfMonth(month: LocalDate): String {
        return month.withDayOfMonth(month.lengthOfMonth()).format(DateTimeFormatter.ISO_DATE)
    }
}
