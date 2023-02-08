package com.graduation_work.bonappetit.ui.view_model

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.graduation_work.bonappetit.data.database.entities.StockEntity
import com.graduation_work.bonappetit.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ConsumptionHistoryViewModel : ViewModel() {

    private val repository: HistoryRepository by inject(HistoryRepository::class.java)

    private var consumptionQuantityByDate = mapOf<String, Int>()

    private var _xAxisValues = listOf<String>()
    val xAxisValues: List<String>
        get() = _xAxisValues

    private val _chartData = MutableStateFlow(LineData())
    val chartData: StateFlow<LineData> = _chartData

    private val _consumedStockList = MutableStateFlow<List<StockEntity>>(emptyList())
    val consumedStockList: StateFlow<List<StockEntity>> = _consumedStockList

    private var selectedYearAndMonth = LocalDate.now()

    private val _yearAndMonth = MutableStateFlow("")
    val yearAndMonth: StateFlow<String>
        get() = _yearAndMonth

    init {
        _yearAndMonth.value = formatDate(selectedYearAndMonth)
    }

    private fun formatDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("yy年M月"))
    }

    fun fetchChartData() {
        viewModelScope.launch {
            consumptionQuantityByDate = repository.fetchConsumptionQuantityByDate(selectedYearAndMonth)
            _consumedStockList.value = repository.fetchConsumedStock(selectedYearAndMonth)
            //④DataにDataSet格納
            if (consumptionQuantityByDate.isNotEmpty()) {
                prepareDrawChart(consumptionQuantityByDate)
            } else {
                _chartData.value = LineData()
                _consumedStockList.value = emptyList()
            }
        }
    }

    fun showPreviousMonth() {
        selectedYearAndMonth = selectedYearAndMonth.minusMonths(1)
        _yearAndMonth.value = formatDate(selectedYearAndMonth)
        fetchChartData()
    }

    fun showNextMonth() {
        selectedYearAndMonth = selectedYearAndMonth.plusMonths(1)
        _yearAndMonth.value = formatDate(selectedYearAndMonth)
        fetchChartData()
    }

    private fun prepareDrawChart(rawData: Map<String, Int>) {
        val list = rawData.toList().sortedBy { it.first }
        _xAxisValues = generateXAxisValues(list)
        _chartData.value = generateChartData(list)
    }

    // Entryのxの値には文字列を設定できないため、チャートデータとは別にx軸の値を用意
    private fun generateXAxisValues(list: List<Pair<String, Int>>): List<String> {
        val values = mutableListOf<String>()
        list.forEach { values.add(it.first.substring(8)) }
        return values
    }

    private fun generateChartData(list: List<Pair<String, Int>>): LineData {
        val entryList = generateEntryList(list)
        val dataset = generateDataset(entryList)
        val lineDataSets = mutableListOf<ILineDataSet>()
        lineDataSets.add(dataset)
        return LineData(lineDataSets)
    }

    private fun generateEntryList(list: List<Pair<String, Int>>): List<Entry> {
        //①Entryにデータ格納
        val entryList = mutableListOf<Entry>()
        for (i in list.indices) {
            entryList.add(Entry(i.toFloat(), list[i].second.toFloat()))
        }
        return entryList
    }

    private fun generateDataset(entryList: List<Entry>): LineDataSet {
        //②DataSetにデータ格納
        val lineDataSet = LineDataSet(entryList, "消費在庫数")
        //③DataSetにフォーマット指定(3章で詳説)
        lineDataSet.color = Color.BLUE
        lineDataSet.setDrawValues(false)
        return lineDataSet
    }
}