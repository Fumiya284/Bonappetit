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
        onDelete = ForeignKey.CASCADE                               //登録した食材を削除した場合stockの情報を消してもいいか
    )]
)
data class StockEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "food_name", index = true)
    val foodName: String,
    val count: Int,
    @ColumnInfo(name = "limit")
    val limit: LocalDate? = null,
    @ColumnInfo(name = "best_or_expiry")
    val bestOrExpiry: String? = null // tureでbest falseでexpiry　あとでほかの人と話して決める SealClassのほうがよさげ　DBにはString
)
{
    companion object {
        fun create4Insert(stock: Stock): StockEntity {
            return if(stock.limit != null) {
                StockEntity(0, stock.foodName, stock.count, stock.limit.date, stock.limit.bestOrExpiry)
            } else {
                StockEntity(0, stock.foodName, stock.count)
            }
        }
    }
}
