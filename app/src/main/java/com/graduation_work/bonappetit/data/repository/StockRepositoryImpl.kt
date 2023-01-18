package com.graduation_work.bonappetit.data.repository

import androidx.room.withTransaction
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.data.database.entities.StockWithFoodView
import com.graduation_work.bonappetit.data.database.entities.WastedHistoryEntity
import com.graduation_work.bonappetit.data.files.ImageFileAccessor
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate

/**
 * あとで例外処理する　Dataレイヤの例外をアプリ固有の例外にラッピングする
 */
class StockRepositoryImpl(
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {
	
	private val database = MyApplication.database
	private val stockDao = database.stockDao()
	private val historyDao = database.wastedHistoryDao()
	private val stockWithFoodDao = database.stockWithFoodDao()
	private val ifa: ImageFileAccessor by inject(ImageFileAccessor::class.java)
	
	override suspend fun fetchAll(): List<Stock> {
		return withContext(dispatcher) {
			stockWithFoodDao.selectAll()
				.map { convertToStock(it, it.imageFilename) }
		}
	}
	
	override suspend fun fetchById(id: Long): Stock {
		return withContext(dispatcher) {
			val stockView = stockWithFoodDao.selectById(id)
			convertToStock(stockView, stockView.imageFilename)
		}
	}
	
	override suspend fun fetchByName(name: String): List<Stock> {
		return withContext(dispatcher) {
			stockWithFoodDao.selectByName(name)
				.map { convertToStock(it, it.imageFilename) }
		}
	}
	
	override suspend fun fetchByCategory(category: Array<String>): List<Stock> {
		return withContext(dispatcher) {
			stockWithFoodDao.selectByCategory(*category)
				.map { convertToStock(it, it.imageFilename) }
		}
	}
	
	override suspend fun fetchByNameAndCategory(
		searchString: String,
		category: Array<String>
	): List<Stock> {
		return withContext(dispatcher) {
			stockWithFoodDao.selectByNameAndCategory(searchString, *category)
				.map { convertToStock(it, it.imageFilename) }
		}
	}
	
	override suspend fun updateNoteAndQuantity(old: Stock, new: Stock) = withContext(dispatcher) {
		if (old.id != new.id) {
			return@withContext
		}
		
		if (old.quantity != new.quantity) {
			stockDao.updateQuantityById(new.quantity, new.id)
		} else if (old.note != new.note) {
			stockDao.updateNoteById(new.note, new.id)
		}
	}
	
	override suspend fun save(stock: Stock) {
		withContext(dispatcher) {
			val entity = StockEntity.createForInsert(stock)
			stockDao.insert(entity)
		}
	}
	
	override suspend fun markAsConsumed(stockId: Long, date: LocalDate, reasonForWasted: String?) {
		return withContext(dispatcher) {
			database.withTransaction {
				stockDao.updateConsumptionDate(date, stockId)
				if (!reasonForWasted.isNullOrBlank()) {
					historyDao.insertHistory(WastedHistoryEntity(0, stockId, reasonForWasted))
				}
			}
		}
	}
	
	private suspend fun convertToStock(view: StockWithFoodView, imageFilePath: String): Stock =
		withContext(dispatcher) {
			val foodImage = ifa.read(imageFilePath)
			
			return@withContext Stock(
				id = view.id,
				quantity = view.quantity,
				limit = view.limit,
				note = view.note,
				food = Food(
					view.foodName,
					view.unit,
					view.category,
					view.limitType,
					foodImage
				)
			)
		}
}