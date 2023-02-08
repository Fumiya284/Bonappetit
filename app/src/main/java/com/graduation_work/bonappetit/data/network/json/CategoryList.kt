package com.graduation_work.bonappetit.data.network.json

import com.squareup.moshi.Json

data class CategoryList(
	val result: CategoryType
)

data class CategoryType(
	val large: List<CategoryInfo>,
	val medium: List<CategoryInfo>,
	val small: List<CategoryInfo>
)

data class CategoryInfo(
	val categoryId: String,
	val categoryName: String,
	val categoryUrl: String,
	val parentCategoryId: String = ""
)