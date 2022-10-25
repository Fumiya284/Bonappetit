package com.graduation_work.bonappetit.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// create4Insertを使ってインスタンスを取得すること
@Entity(tableName = "food")
data class FoodEntity (
    @PrimaryKey
    val name: String,
    val unit: String,
    @ColumnInfo(defaultValue = "sample")
    val category: String
)
