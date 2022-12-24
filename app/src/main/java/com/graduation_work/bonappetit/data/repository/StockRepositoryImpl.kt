package com.graduation_work.bonappetit.data.repository

import android.util.Log
import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.data.database.entities.StockWithFoodView
import com.graduation_work.bonappetit.data.files.ImageFileAccessor
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

/**
 * あとで例外処理する　Dataレイヤの例外をアプリ固有の例外にラッピングする
 */
class StockRepositoryImpl(
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {
	private val database = MyApplication.database
	private val stockDao = database.stockDao()
	private val stockWithFoodDao = database.stockWithFoodDao()
	private val ifa: ImageFileAccessor by inject(ImageFileAccessor::class.java)
	
	override suspend fun fetchAll(): List<Stock> = withContext(dispatcher) {
		return@withContext stockWithFoodDao.selectAll()
			.map { convertToStock(it, it.imageFilename) }
	}
	
	override suspend fun fetchById(id: Long): Stock = withContext(dispatcher) {
		val stockView = stockWithFoodDao.selectById(id)
		return@withContext convertToStock(stockView, stockView.imageFilename)
	}
	
	override suspend fun fetchByName(name: String): List<Stock> = withContext(dispatcher) {
		return@withContext stockWithFoodDao.selectByName(name)
			.map { convertToStock(it, it.imageFilename) }
	}
	
	override suspend fun fetchByCategory(category: Array<String>): List<Stock> =
		withContext(dispatcher) {
			return@withContext stockWithFoodDao.selectByCategory(*category)
				.map { convertToStock(it, it.imageFilename) }
		}
	
	override suspend fun fetchByNameAndCategory(
		searchString: String,
		category: Array<String>
	): List<Stock> = withContext(dispatcher) {
		return@withContext stockWithFoodDao.selectByNameAndCategory(searchString, *category)
			.map { convertToStock(it, it.imageFilename) }
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
	
	override suspend fun save(stock: Stock) = withContext(dispatcher) {
		val entity = StockEntity.createForInsert(stock)
		return@withContext stockDao.insert(entity)
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