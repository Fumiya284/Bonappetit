package com.graduation_work.bonappetit.model.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.database.entities.StockEntity
import com.graduation_work.bonappetit.database.entities.StockWithFoodView
import com.graduation_work.bonappetit.model.data.Limit
import com.graduation_work.bonappetit.model.data.Stock
import com.graduation_work.bonappetit.model.data.StockList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// あとで例外処理かく　たぶん
class StockRepository(
    private val defaultDispatcher: CoroutineDispatcher
) {
    private val database = MyApplication.database
    private val stockDao = database.stockDao()
    private val stockWithFoodDao = database.stockWithFoodDao()
    
    suspend fun getByName(name: String? = null) = withContext(defaultDispatcher) {
        if(name.isNullOrEmpty()) {
            val stockEntities = stockWithFoodDao.selectAll()
            return@withContext toStockList(stockEntities)
        } else {
            val stockEntities = stockWithFoodDao.selectByName(name)
            return@withContext toStockList(stockEntities)
        }
    }
    
    private fun toStockList(entityList: List<StockWithFoodView>): StockList {
        val stockList = entityList.map { toStock(it) }
        return StockList(stockList)
    }
    
    private fun toStock(entity: StockWithFoodView): Stock {
        return if(entity.limit != null && entity.bestOrExpiry != null) {
            Stock(
                entity.id,
                entity.foodName,
                entity.count,
                entity.unit,
                Limit(
                    entity.limit,
                    entity.bestOrExpiry
                )
            )
        } else {
            Stock(
                entity.id,
                entity.foodName,
                entity.count,
                entity.unit
            )
        }
    }
    
    private fun toEntity(stock: Stock): StockEntity {
        return if(stock.limit == null) {
            StockEntity.create4Insert(
                stock.foodName,
                stock.count
            )
        } else {
            StockEntity.create4Insert(
                stock.foodName,
                stock.count,
                stock.limit.date,
                stock.limit.bestOrExpiry
            )
        }
    }
}