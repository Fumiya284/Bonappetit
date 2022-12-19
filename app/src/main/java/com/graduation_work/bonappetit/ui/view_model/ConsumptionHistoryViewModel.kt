package com.graduation_work.bonappetit.ui.view_model

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.graduation_work.bonappetit.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class ConsumptionHistoryViewModel : ViewModel() {

    private val repository: HistoryRepository by inject(HistoryRepository::class.java)

    private var consumptionQuantityByDate = mapOf<String, Int>()

    private var _xAxisValues = listOf<String>()
    val xAxisValues: List<String> = _xAxisValues

    private val _chartData = MutableStateFlow(LineData())
    val chartData: StateFlow<LineData> = _chartData

    init {
        viewModelScope.launch {
            consumptionQuantityByDate = repository.fetchConsumptionQuantityByDate()
            //④DataにDataSet格納
            if (consumptionQuantityByDate.isNotEmpty()) prepareDrawChart(consumptionQuantityByDate)
        }
    }

    private fun prepareDrawChart(rawData: Map<String, Int>) {
        val list = rawData.toList().sortedBy { it.first }
        _xAxisValues = generateXAxisValues(list)
        _chartData.value = generateChartData(list)
    }

    // Entryのxの値には文字列を設定できないため、チャートデータとは別にx軸の値を用意
    private fun generateXAxisValues(list: List<Pair<String, Int>>): List<String> {
        val values = mutableListOf<String>()
        list.forEach { values.add(it.first) }
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
        val lineDataSet = LineDataSet(entryList, "消費数")
        //③DataSetにフォーマット指定(3章で詳説)
        lineDataSet.color = Color.BLUE
        lineDataSet.setDrawValues(false)
        return lineDataSet
    }
}