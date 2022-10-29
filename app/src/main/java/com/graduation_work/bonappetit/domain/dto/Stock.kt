package com.graduation_work.bonappetit.domain.dto

data class Stock(
	val id: Long = 0,
	val food: Food,
	val count: Int,
	val limit: Limit?
)

