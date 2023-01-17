package com.graduation_work.bonappetit.data.network

import retrofit2.http.GET

interface RakutenApiService {
	@GET("https://app.rakuten.co.jp/services/api/Recipe/CategoryList/20170426?format=json&applicationId=1021168325374360105")
	suspend fun getRecipeCategoryList
}