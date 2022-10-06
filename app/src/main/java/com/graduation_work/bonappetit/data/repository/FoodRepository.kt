package com.graduation_work.bonappetit.data.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.FoodEntity

// 例外処理は後回し
class FoodRepository {
    private val database = MyApplication.database
    private val foodDao = database.foodDao()

    suspend fun register(newFood: FoodEntity): Long {
        return foodDao.insertFood(newFood)
    }

    suspend fun getByName(name: String): FoodEntity{
        return foodDao.selectByName(name)
    }

    suspend fun getAll(): List<FoodEntity> {
        return foodDao.selectAll()
    }

    suspend fun deleteAll() {
        foodDao.deleteAll()
    }

    suspend fun delete(target: FoodEntity) {
        foodDao.deleteFood(target)
    }

    suspend fun update(newInfo: FoodEntity) {
        foodDao.updateFood(newInfo)
    }
}