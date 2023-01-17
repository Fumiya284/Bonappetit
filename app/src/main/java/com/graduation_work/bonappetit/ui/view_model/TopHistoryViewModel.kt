package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.graduation_work.bonappetit.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class TopHistoryViewModel : ViewModel() {

    private val repository: HistoryRepository by inject(HistoryRepository::class.java)

    private var consumptionAndWastedQuantity = mapOf<String, Int>()

    private val _consumptionAndWastedChartData = MutableStateFlow(PieData())
    val consumptionAndWastedChartData: StateFlow<PieData> = _consumptionAndWastedChartData

    private var wastedQuantityByReason = mapOf<String, Int>()

    private val _reasonForWastedChartData = MutableStateFlow(PieData())
    val reasonForWastedChartData: StateFlow<PieData> = _reasonForWastedChartData

    init {
        viewModelScope.launch {
            consumptionAndWastedQuantity = repository.fetchConsumptionAndWastedQuantity()
            wastedQuantityByReason = repository.fetchWastedQuantityByReason()
            //④PieDataにPieDataSet格納
            if (consumptionAndWastedQuantity.isNotEmpty()) _consumptionAndWastedChartData.value =
                generateChartData(consumptionAndWastedQuantity)
            if (wastedQuantityByReason.isNotEmpty()) _reasonForWastedChartData.value =
                generateChartData(wastedQuantityByReason)
        }
    }

    private fun generateChartData(rawData: Map<String, Int>): PieData {
        val entryList = generateEntryList(rawData)
        val dataSet = generateDataset(entryList)
        return PieData(dataSet)
    }

    private fun generateEntryList(rawData: Map<String, Int>): List<PieEntry> {
        //①Entryにデータ格納
        val entryList = mutableListOf<PieEntry>()
        rawData.forEach { (key, value) ->
            entryList.add(PieEntry(value.toFloat(), key))
        }
        return entryList
    }

    private fun generateDataset(entryList: List<PieEntry>): PieDataSet {
        //②PieDataSetにデータ格納
        val pieDataSet = PieDataSet(entryList, "")
        //③DataSetのフォーマット指定
        pieDataSet.apply {
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "%.1f%%".format(value)
                }
            }
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextSize = 15f
        }
        return pieDataSet
    }
}
