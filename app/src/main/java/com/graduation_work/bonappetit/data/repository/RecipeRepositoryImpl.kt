package com.graduation_work.bonappetit.data.repository

import android.graphics.BitmapFactory
import android.util.Log
import com.graduation_work.bonappetit.data.network.RakutenRecipeApiService
import com.graduation_work.bonappetit.domain.dto.Recipe
import com.graduation_work.bonappetit.domain.repository.RecipeRepository
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.BufferedInputStream

//　例外処理はあとで書くかもしれない
class RecipeRepositoryImpl: RecipeRepository {
	private val client = OkHttpClient.Builder().build()
	private val moshi = Moshi.Builder()
		.add(KotlinJsonAdapterFactory())
		.build()
	private val recipeService = Retrofit.Builder()
		.baseUrl("https://app.rakuten.co.jp/services/api/")
		.client(client)
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.build()
		.create(RakutenRecipeApiService::class.java)
	
	override suspend fun getRecipe(keyword: String): List<Recipe>? {
		val listApiResponse = recipeService.getCategoryList()
		if (!listApiResponse.isSuccessful) { return null }
		
		val result = listApiResponse.body()?.result ?: return null
		val nameDict = mutableMapOf<String, String>()
		val parentIdDict = mutableMapOf<String, String>()
		
		result.large.onEach {
			nameDict[it.categoryId] = it.categoryName
		}
		
		result.medium.onEach {
			val id = "${it.parentCategoryId}-${it.categoryId}"
			nameDict[id] = it.categoryName
			parentIdDict[it.categoryId] = id
		}
		
		result.small.onEach {
			val id = "${parentIdDict[it.parentCategoryId]}-${it.categoryId}"
			nameDict[id] = it.categoryName
		}
		
		val matchedId = nameDict.filterValues { it.contains(keyword, true) }.keys.toList()
		if (matchedId.isEmpty()) { return null }
		val rankingApiResponse = recipeService.getCategoryRanking(query = matchedId[0])
		if (!rankingApiResponse.isSuccessful) { return null }
		
		val recipes = rankingApiResponse.body()?.result ?: return null
		
		val recipeList = mutableListOf<Recipe>()
		recipes.onEach {
			recipeList.add(Recipe(it.recipeTitle, it.recipeUrl, it.foodImageUrl))
		}
		
		return recipeList
	}
}