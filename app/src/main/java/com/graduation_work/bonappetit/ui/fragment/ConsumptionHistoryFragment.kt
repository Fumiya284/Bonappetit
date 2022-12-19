package com.graduation_work.bonappetit.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.graduation_work.bonappetit.databinding.ConsumptionHistoryFragmentBinding
import com.graduation_work.bonappetit.ui.adapter.StockListForHistoryAdapter
import com.graduation_work.bonappetit.ui.view_model.ConsumptionHistoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConsumptionHistoryFragment : Fragment() {

    private var _binding: ConsumptionHistoryFragmentBinding? = null
    val binding: ConsumptionHistoryFragmentBinding
        get() = _binding!!

    private val viewModel: ConsumptionHistoryViewModel by viewModel()

    private lateinit var listAdapter: StockListForHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ConsumptionHistoryFragmentBinding.inflate(inflater, container, false)
        listAdapter = StockListForHistoryAdapter("消費")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.consumedStockList.adapter = listAdapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.chartData.collect { drawChart(it) } }
                launch { viewModel.consumedStockList.collect { listAdapter.submitList(it) } }
            }
        }
    }

    private fun drawChart(chartData: LineData) {
        binding.consumptionLineChart.apply {
            //⑤ChartにData格納
            data = chartData
            //⑥Chartのフォーマット指定
            description.isEnabled = false
            xAxis.apply {
                isEnabled = true
                textColor = Color.BLACK
                position = XAxis.XAxisPosition.BOTTOM
                isGranularityEnabled = true
                granularity = 1f
                labelCount = if (viewModel.xAxisValues.size > 10) 10 else viewModel.xAxisValues.size
                valueFormatter = IndexAxisValueFormatter(viewModel.xAxisValues)
            }
            axisLeft.textSize = 15f
            axisLeft.axisMinimum = 0f
            axisRight.isEnabled = false
        }
        //⑦chart更新
        binding.consumptionLineChart.let {
            it.xAxis.setAvoidFirstLastClipping(true)
            it.data.notifyDataChanged()
            it.notifyDataSetChanged()
            it.invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
