package com.graduation_work.bonappetit.data.database.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.graduation_work.bonappetit.domain.dto.Food

@Entity(tableName = "food")
data class FoodEntity(
	@PrimaryKey val name: String,
	val unit: String,
	@ColumnInfo(defaultValue = "sample") val category: String,
	@ColumnInfo(name = "limit_type", defaultValue = "賞味期限") val limitType: String,
	@ColumnInfo(name = "image_filename", defaultValue = "default.JPEG") val imageFilename: String
) {
	
	
	companion object {
		fun createForInsert(food: Food): FoodEntity {
			return FoodEntity(
				name = food.name,
				unit = food.unit,
				category = food.category,
				limitType = food.limitType,
				imageFilename = food.name
			)
		}
	}
}
