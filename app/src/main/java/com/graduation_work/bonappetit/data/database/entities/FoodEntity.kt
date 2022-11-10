package com.graduation_work.bonappetit.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.graduation_work.bonappetit.domain.dto.Food

@Entity(tableName = "food")
data class FoodEntity (
    @PrimaryKey
    val name: String,
    val unit: String,
    @ColumnInfo(defaultValue = "sample")
    val category: String,
    @ColumnInfo(name = "limit_type", defaultValue = "賞味期限")
    val limitType: String
) {
   fun convertToFood(): Food {
       return Food(this.name, this.unit, this.category, this.limitType)
   }
}
