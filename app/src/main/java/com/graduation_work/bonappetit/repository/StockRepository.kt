package com.graduation_work.bonappetit.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.database.entities.StockWithFoodView
import com.graduation_work.bonappetit.model.data.StockList
import com.graduation_work.bonappetit.repository.converter.StockConverter
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
            val stockEntities = stockWithFoodDao.selectAll()
            return@withContext toStockList(stockEntities)
        } else {
            val stockEntities = stockWithFoodDao.selectByName(foodName)
            return@withContext toStockList(stockEntities)
        }
    }
    
    private fun toStockList(entityList: List<StockWithFoodView>): StockList {
        val stockList = entityList.map { StockConverter.toStock(it) }
        return StockList(stockList)
    }
}