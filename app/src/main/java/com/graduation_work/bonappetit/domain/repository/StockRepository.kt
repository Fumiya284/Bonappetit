package com.graduation_work.bonappetit.domain.repository

import com.graduation_work.bonappetit.domain.dto.Stock

interface StockRepository {
	suspend fun get(foodName: String? = null, tag: List<String>): List<Stock>
	
	suspend fun register(stock: Stock): Long
}