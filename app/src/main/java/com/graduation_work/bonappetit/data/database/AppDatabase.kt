package com.graduation_work.bonappetit.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.graduation_work.bonappetit.data.database.converter.LocalDateConverter
import com.graduation_work.bonappetit.data.database.dao.FoodDao
import com.graduation_work.bonappetit.data.database.dao.StockDao
import com.graduation_work.bonappetit.data.database.dao.StockWithFoodDao
import com.graduation_work.bonappetit.data.database.entities.FoodEntity
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.data.database.entities.StockWithFoodView

@Database(
    version = 3,
    entities = [FoodEntity::class, StockEntity::class],
    views = [StockWithFoodView::class],
    autoMigrations = [
        AutoMigration(from = 1, to =2, spec = AppDatabase.Migration1to2::class),
        AutoMigration(from = 2, to = 3, spec = AppDatabase.Migration2to3::class)
    ]
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    abstract fun stockDao(): StockDao
    
    abstract fun stockWithFoodDao(): StockWithFoodDao
    
    @RenameColumn(tableName = "stock", fromColumnName = "best_or_expiry", toColumnName = "limit_type")
    class Migration1to2: AutoMigrationSpec
    
    @DeleteColumn(tableName = "stock", columnName = "limit_type")
    class Migration2to3: AutoMigrationSpec
}


