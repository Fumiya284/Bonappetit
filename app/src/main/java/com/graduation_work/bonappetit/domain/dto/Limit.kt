package com.graduation_work.bonappetit.domain.dto

import java.time.LocalDate

data class Limit(
	val date: LocalDate,
	val bestOrExpiry: String
)