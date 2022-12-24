package com.graduation_work.bonappetit.domain.repository

import com.graduation_work.bonappetit.domain.dto.Stock

interface StockRepository {
	suspend fun fetchAll(): List<Stock>
	
	suspend fun fetchById(id: Long): Stock
	
	suspend fun fetchByName(name: String): List<Stock>
	
	suspend fun fetchByCategory(category: Array<String>): List<Stock>
	
	suspend fun fetchByNameAndCategory(searchString: String, category: Array<String>): List<Stock>
	
	suspend fun updateNoteAndQuantity(old: Stock, new: Stock)
	
	suspend fun save(stock: Stock)
}