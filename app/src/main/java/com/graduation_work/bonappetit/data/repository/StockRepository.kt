package com.graduation_work.bonappetit.data.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// あとで例外処理かく　たぶん
class StockRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val database = MyApplication.database
    private val stockDao = database.stockDao()
    private val stockWithFoodDao = database.stockWithFoodDao()
    
    suspend fun get(foodName: String? = null) = withContext(dispatcher) {
        if(foodName.isNullOrEmpty()) {
            return@withContext stockWithFoodDao.selectAll()
        } else {
            return@withContext stockWithFoodDao.selectByName(foodName)
        }
    }
    
    suspend fun register(stock: StockEntity) = withContext(dispatcher) {
        stockDao.insert(stock)
    }
}