package com.graduation_work.bonappetit.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
	tableName = "wasted_history",
	foreignKeys = [ForeignKey(
		entity = StockEntity::class,
		parentColumns = arrayOf("id"),
		childColumns = arrayOf("stock_id"),
		onDelete = ForeignKey.CASCADE
	)]
)
data class WastedHistoryEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Long,
	@ColumnInfo(name = "stock_id", index = true)
	val stockId: Long,
	val reason: String
)
