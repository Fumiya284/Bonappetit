package com.graduation_work.bonappetit.domain.repository

import com.graduation_work.bonappetit.domain.dto.Food

interface FoodRepository {
	suspend fun fetchAllCategory(): List<String>
	
	suspend fun fetchAllFood(): List<Food>
}