package com.graduation_work.bonappetit.domain.dto

data class Stock(
	val id: Long,
	val foodName: String,
	val unit: String,
	val count: Int,
	val limit: Limit?
)

