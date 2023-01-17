package com.graduation_work.bonappetit.domain.repository

import com.graduation_work.bonappetit.domain.dto.Recipe

interface RecipeRepository {
	suspend fun getRecipe(keyword: String): List<Recipe>?
}