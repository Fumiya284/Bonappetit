package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.domain.repository.CalendarRepository
import com.kizitonwose.calendar.core.atStartOfMonth
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarViewModel : ViewModel() {

    private val repository: CalendarRepository by inject(CalendarRepository::class.java)

    val titleSameYearFormatter = DateTimeFormatter.ofPattern("M月")
    val titleFormatter = DateTimeFormatter.ofPattern("yyyy年 M月")
    val selectionFormatter = DateTimeFormatter.ofPattern("yyyy年 M月 d日")

    private var stockListByThisMonth = listOf<StockEntity>()

    private var _stockListByLimit = mapOf<LocalDate, List<StockEntity>>()
    val stockListByLimit: Map<LocalDate, List<StockEntity>>
        get() = _stockListByLimit

    suspend fun reloadCalendarData(month: YearMonth) {
        val first = month.atStartOfMonth().format(DateTimeFormatter.ISO_DATE)
        val last = month.atEndOfMonth().format(DateTimeFormatter.ISO_DATE)
        viewModelScope.launch {
            stockListByThisMonth = repository.fetchStockListByThisMonth(first, last)
            _stockListByLimit = stockListByThisMonth.groupBy { it.limit }
        }.join()
    }
}