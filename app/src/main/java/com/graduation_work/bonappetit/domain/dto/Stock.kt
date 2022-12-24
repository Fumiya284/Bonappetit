package com.graduation_work.bonappetit.domain.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

data class Stock(
	val id: Long = 0,
	val food: Food,
	val quantity: Int,
	val limit: LocalDate,
	val note: String
)

