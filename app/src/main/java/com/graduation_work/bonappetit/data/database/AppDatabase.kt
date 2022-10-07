package com.graduation_work.bonappetit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.graduation_work.bonappetit.data.database.converter.LocalDateConverter
import com.graduation_work.bonappetit.data.database.dao.FoodDao
import com.graduation_work.bonappetit.data.database.dao.StockDao
import com.graduation_work.bonappetit.data.database.dao.StockWithFoodDao
import com.graduation_work.bonappetit.data.database.entities.FoodEntity
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.data.database.entities.StockWithFoodView

@Database(
    version = 1,
    entities = [FoodEntity::class, StockEntity::class],
    views = [StockWithFoodView::class]
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    abstract fun stockDao(): StockDao
    
    abstract fun stockWithFoodDao(): StockWithFoodDao
}
