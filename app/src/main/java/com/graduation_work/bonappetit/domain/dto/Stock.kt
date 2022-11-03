package com.graduation_work.bonappetit.domain.dto

import java.time.LocalDate

data class Stock(
	val id: Long = 0,   // 登録日保存するカラム作るまでの仮置き
	val food: Food,
	val count: Int,
	val limit: LocalDate?
)

