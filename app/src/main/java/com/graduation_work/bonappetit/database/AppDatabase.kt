package com.graduation_work.bonappetit.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.graduation_work.bonappetit.database.converter.LocalDateConverter
import com.graduation_work.bonappetit.database.dao.FoodDao
import com.graduation_work.bonappetit.database.dao.StockDao
import com.graduation_work.bonappetit.database.dao.StockWithFoodDao
import com.graduation_work.bonappetit.database.entities.FoodEntity
import com.graduation_work.bonappetit.database.entities.StockEntity
import com.graduation_work.bonappetit.database.entities.StockWithFoodView

@Database(
    version = 3,
    entities = [FoodEntity::class, StockEntity::class],
    views = [StockWithFoodView::class],
    autoMigrations = [
        AutoMigration(
            from = 2,
            to = 3
        )
    ]
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    abstract fun stockDao(): StockDao
    
    abstract fun stockWithFoodDao(): StockWithFoodDao
}

