package com.graduation_work.bonappetit.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.graduation_work.bonappetit.domain.dto.Stock
import java.time.LocalDate

// create4Insertを使ってインスタンスを取得すること ほんとはconstructorをprivateにしたかった
@Entity(
	tableName = "stock", foreignKeys = [ForeignKey(
		entity = FoodEntity::class,
		parentColumns = arrayOf("name"),
		childColumns = arrayOf("food_name"),
		onDelete = ForeignKey.CASCADE
	)]
)
data class StockEntity(
	@PrimaryKey(autoGenerate = true) val id: Long,
	@ColumnInfo(name = "food_name", index = true) val foodName: String,
	val quantity: Int,
	@ColumnInfo(defaultValue = "")val note: String,
	@ColumnInfo(name = "registration_date", defaultValue = "2022-12-01") val registrationDate: LocalDate,
	@ColumnInfo(defaultValue = "2022-12-01") val limit: LocalDate,
	@ColumnInfo(name = "consumption_date") val consumptionDate: LocalDate? = null
) {
	companion object {
		fun createForInsert(stock: Stock): StockEntity {
			return StockEntity(
				id = 0,
				foodName = stock.food.name,
				quantity = stock.quantity,
				note = stock.note,
				registrationDate = LocalDate.now(),
				limit = stock.limit
			)
		}
	}
}
