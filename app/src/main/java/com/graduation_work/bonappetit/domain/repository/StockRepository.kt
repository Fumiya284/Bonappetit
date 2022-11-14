package com.graduation_work.bonappetit.domain.repository

import arrow.core.Either
import com.graduation_work.bonappetit.domain.dto.Stock
import com.graduation_work.bonappetit.domain.dto.StockRegistrationInfo
import com.graduation_work.bonappetit.domain.exception.FailedToRegisterException

interface StockRepository {
	suspend fun fetchAll(): List<Stock>
	
	suspend fun fetchByCondition(searchString: String, category: Array<String>): List<Stock>
	
	suspend fun fetchByName(name: String): List<Stock>
	
	suspend fun save(stock: StockRegistrationInfo): Either<FailedToRegisterException, Unit>
}