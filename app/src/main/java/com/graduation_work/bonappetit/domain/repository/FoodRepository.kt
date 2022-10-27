package com.graduation_work.bonappetit.domain.repository

interface FoodRepository {
	suspend fun fetchAllCategory(): List<String>
}