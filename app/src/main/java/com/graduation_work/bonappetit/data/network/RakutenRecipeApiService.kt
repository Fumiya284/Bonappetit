package com.graduation_work.bonappetit.data.network

import com.graduation_work.bonappetit.data.network.json.CategoryList
import com.graduation_work.bonappetit.data.network.json.CategoryRanking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RakutenRecipeApiService {
	@GET("Recipe/CategoryList/20170426")
	suspend fun getCategoryList(
		@Query("format") format: String = "json",
		@Query("applicationId") applicationId: String = "1021168325374360105"
	): Response<CategoryList>
	
	@GET("Recipe/CategoryRanking/20170426")
	suspend fun getCategoryRanking(
		@Query("format") format: String = "json",
		@Query("applicationId") applicationId: String = "1021168325374360105",
		@Query("categoryId") query: String
	): Response<CategoryRanking>
}