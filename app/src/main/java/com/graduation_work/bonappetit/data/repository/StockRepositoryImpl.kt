package com.graduation_work.bonappetit.data.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// あとで例外処理かく　たぶん
class StockRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {
    private val database = MyApplication.database
    private val stockDao = database.stockDao()
    private val stockWithFoodDao = database.stockWithFoodDao()
    
    override suspend fun get(foodName: String?, tag: List<String>) : List<Stock> = withContext(dispatcher) {
        if(foodName.isNullOrEmpty()) {
            return@withContext stockWithFoodDao.selectAll().map { it.convertToStock() }
        } else {
            return@withContext stockWithFoodDao.selectByName(foodName).map { it.convertToStock() }
        }
    }
    
    override suspend fun register(stock: Stock) = withContext(dispatcher) {
        val entity = StockEntity.create4Insert(stock)
        stockDao.insert(entity)
    }
}