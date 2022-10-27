package com.graduation_work.bonappetit.domain.repository

import com.graduation_work.bonappetit.domain.dto.Stock

interface StockRepository {
	suspend fun fetchAll(): List<Stock>
	
	suspend fun fetchByCondition(searchString: String, category: Array<String>): List<Stock>
	
	suspend fun register(stock: Stock): Long
}