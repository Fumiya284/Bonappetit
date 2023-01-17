package com.graduation_work.bonappetit.domain.use_case

import android.util.Log
import com.graduation_work.bonappetit.domain.dto.Recipe
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.repository.RecipeRepository
import com.graduation_work.bonappetit.domain.repository.StockRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent

class StockDetailUseCase {
	private val stockRepository: StockRepository by KoinJavaComponent.inject(StockRepository::class.java)
	private val recipeRepository: RecipeRepository by KoinJavaComponent.inject(RecipeRepository::class.java)
	private lateinit var editedStock: Stock
	private lateinit var initialStock: Stock
	
	private val _stock = MutableSharedFlow<Stock>()
	val stock: SharedFlow<Stock> = _stock
	
	private val _recipe = MutableSharedFlow<List<Recipe>>()
	val recipe: SharedFlow<List<Recipe>> = _recipe
	
	private val _isNoteChanged = MutableStateFlow<Boolean>(false)
	val isNoteChanged: StateFlow<Boolean> = _isNoteChanged
	
	private val _isQuantityChanged = MutableStateFlow<Boolean>(false)
	val isQuantityChanged: StateFlow<Boolean> = _isQuantityChanged
	
	fun setQuantity(quantity: Int) {
		editedStock = editedStock.copy(quantity = quantity)
		_isQuantityChanged.value = editedStock.quantity != initialStock.quantity
	}
	
	fun setNote(note: String) {
		editedStock = editedStock.copy(note = note)
		_isNoteChanged.value = editedStock.note != initialStock.note
	}
	
	suspend fun saveEdits() {
		if (editedStock == initialStock) { return }
		
		stockRepository.updateNoteAndQuantity(initialStock, editedStock)
	}
	
	suspend fun markAsConsumed(stockId: Long) {
	
	}
	
	suspend fun loadStock(stockId: Long) {
		initialStock = stockRepository.fetchById(stockId)
		editedStock = initialStock
		_isNoteChanged.value = false
		_isQuantityChanged.value = false
		_stock.emit(initialStock)
		val recipe = recipeRepository.getRecipe(initialStock.food.name)
		if (recipe != null) {
			_recipe.emit(recipe)
		}
	}
}