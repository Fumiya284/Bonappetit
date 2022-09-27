package com.graduation_work.bonappetit.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// create4Insertを使ってインスタンスを取得すること ほんとはconstructorをprivateにしたかった
@Entity(tableName = "food")
data class FoodEntity (
    @PrimaryKey
    val name: String,
    val unit: String
)
{
    companion object {
        fun create4Insert(name: String, unit: String): FoodEntity {
            return FoodEntity( name, unit)
        }
    }
}
