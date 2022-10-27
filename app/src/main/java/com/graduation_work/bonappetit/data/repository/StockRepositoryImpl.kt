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
    
    override suspend fun fetchAll(): List<Stock> = withContext(dispatcher) {
        return@withContext stockWithFoodDao.selectAll().map { it.convertToStock() }
    }
    
    override suspend fun fetchByCondition(
        searchString: String,
        category: Array<String>
    ): List<Stock> = withContext(dispatcher) {
        return@withContext if(searchString.isBlank()) {
            stockWithFoodDao.selectByCategory(*category).map{ it.convertToStock() }
        } else if(category.isEmpty()) {
            stockWithFoodDao.selectByName(searchString).map { it.convertToStock() }
        } else {
            stockWithFoodDao.selectByNameAndCategory(searchString, *category).map { it.convertToStock() }
        }
    }
    
    override suspend fun register(stock: Stock) = withContext(dispatcher) {
        val entity = StockEntity.create4Insert(stock)
        stockDao.insert(entity)
    }
}