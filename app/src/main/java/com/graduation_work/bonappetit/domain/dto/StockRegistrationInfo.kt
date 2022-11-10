package com.graduation_work.bonappetit.domain.dto

import java.time.LocalDate

data class StockRegistrationInfo (
	val foodName: String,
	val quantity: Int,
	val limit: LocalDate?
)