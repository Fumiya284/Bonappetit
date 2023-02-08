package com.graduation_work.bonappetit.data.repository

import com.graduation_work.bonappetit.MyApplication
import com.graduation_work.bonappetit.data.database.entities.FoodEntity
import com.graduation_work.bonappetit.data.files.ImageFileAccessor
import com.graduation_work.bonappetit.domain.dto.Food
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

// そのうち例外処理かく
class FoodRepositoryImpl(
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FoodRepository {
	private val database = MyApplication.database
	private val dao = database.foodDao()
	private val ifa: ImageFileAccessor by inject(ImageFileAccessor::class.java)
	
	override suspend fun fetchAllCategory(): List<String> = withContext(dispatcher) {
		return@withContext dao.selectAllCategory()
	}
	
	override suspend fun fetchAllFood(): List<Food> = withContext(dispatcher) {
		val entities = dao.selectAll()
		return@withContext entities.map { convertToFood(it, it.imageFilename) }
	}
	
	override suspend fun registerFood(food: Food): Unit = withContext(dispatcher) {
		val entity = FoodEntity.createForInsert(food)
		
		dao.insert(entity)
		ifa.write(food.image, entity.imageFilename)
	}
	
	private fun convertToFood(foodEntity: FoodEntity, imageFilePath: String): Food {
		val image = ifa.read(imageFilePath)
		return Food(
			foodEntity.name,
			foodEntity.unit,
			foodEntity.category,
			foodEntity.limitType,
			image
		)
	}
}