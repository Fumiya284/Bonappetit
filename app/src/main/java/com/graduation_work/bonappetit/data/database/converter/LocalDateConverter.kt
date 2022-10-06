package com.graduation_work.bonappetit.data.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): String? {
        return localDate?.toString()
    }
    @TypeConverter
    fun toLocalDate(stringDate: String?): LocalDate? {
        return if(!stringDate.isNullOrEmpty()) LocalDate.parse(stringDate) else null
    }
}
