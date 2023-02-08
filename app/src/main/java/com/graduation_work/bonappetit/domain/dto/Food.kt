package com.graduation_work.bonappetit.domain.dto

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food (
	val name: String,
	val unit: String,
	val category: String,
	val limitType: String,
	val image: Bitmap
) : Parcelable