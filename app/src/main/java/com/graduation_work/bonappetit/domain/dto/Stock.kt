package com.graduation_work.bonappetit.domain.dto

import java.time.LocalDate

data class Stock(
	val id: Long = 0,   // 登録日保存するカラム作るまでの仮置き はよつくれ
	val food: Food,
	val quantity: Int,
	val limit: LocalDate?
)

