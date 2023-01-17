package com.graduation_work.bonappetit.data.network.json

data class CategoryRanking(
	val result: List<RankingInfo>
)

data class RankingInfo(
	val foodImageUrl: String,
	val mediumImageUrl: String,
	val nickname: String,
	val pickup: Int,
	val rank: String,
	val recipeCost: String,
	val recipeDescription: String,
	val recipeId: Int,
	val recipeIndication: String,
	val recipeMaterial: List<String>,
	val recipePublishday: String,
	val recipeTitle: String,
	val recipeUrl: String,
	val shop: Int,
	val smallImageUrl: String
)
