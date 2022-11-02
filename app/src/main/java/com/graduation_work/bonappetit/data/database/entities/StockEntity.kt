package com.graduation_work.bonappetit.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.graduation_work.bonappetit.domain.dto.Stock
import java.time.LocalDate

// create4Insertを使ってインスタンスを取得すること ほんとはconstructorをprivateにしたかった
@Entity(
    tableName = "stock",
    foreignKeys = [ForeignKey(
        entity = FoodEntity::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("food_name"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class StockEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "food_name", index = true)
    val foodName: String,
    val count: Int,
    @ColumnInfo(name = "limit")
    val limit: LocalDate? = null
)
{
    companion object {
        fun create4Insert(stock: Stock): StockEntity {
            return StockEntity(stock.id, stock.food.name, stock.count, stock.limit)
        }
    }
}
