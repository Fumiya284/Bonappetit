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
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.graduation_work.bonappetit.databinding.WastedHistoryFragmentBinding
import com.graduation_work.bonappetit.ui.adapter.StockListForHistoryAdapter
import com.graduation_work.bonappetit.ui.view_model.WastedHistoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WastedHistoryFragment : Fragment() {

    private var _binding: WastedHistoryFragmentBinding? = null
    val binding: WastedHistoryFragmentBinding
        get() = _binding!!

    private val viewModel: WastedHistoryViewModel by viewModel()

    private lateinit var listAdapter: StockListForHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = WastedHistoryFragmentBinding.inflate(inflater, container, false)
        listAdapter = StockListForHistoryAdapter("廃棄")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNoDataText(binding.wastedLineChart)
        binding.includeBottomSheet.stockList.adapter = listAdapter
        binding.previous.setOnClickListener { viewModel.showPreviousMonth() }
        binding.next.setOnClickListener { viewModel.showNextMonth() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.yearAndMonth.collect {
                        binding.selectedYearAndMonth.text = it
                        binding.includeBottomSheet.title.text = "${it}に廃棄した食材リスト"
                    }
                }
                launch { viewModel.chartData.collect { drawChart(it) } }
                launch {
                    viewModel.wastedStockList.collect {
                        listAdapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun showNoDataText(lineChart: LineChart) {
        lineChart.let {
            it.setNoDataText("データが存在しません")
            it.setNoDataTextColor(Color.BLACK)
            it.getPaint(Chart.PAINT_INFO).textSize = 60f
        }
    }

    private fun drawChart(chartData: LineData) {
        if (chartData.dataSets.isEmpty()) {
            binding.wastedLineChart.let {
                it.clear()
                it.invalidate()
            }
            return
        }

        binding.wastedLineChart.apply {
            //⑤ChartにData格納
            data = chartData
            //⑥Chartのフォーマット指定
            description.isEnabled = false
            legend.textSize = 15f
            xAxis.apply {
                isEnabled = true
                textColor = Color.BLACK
                textSize = 15f
                position = XAxis.XAxisPosition.BOTTOM
                isGranularityEnabled = true
                granularity = 1f
                labelCount = if (viewModel.xAxisValues.size > 10) 10 else viewModel.xAxisValues.size
                valueFormatter = IndexAxisValueFormatter(viewModel.xAxisValues)
            }
            axisLeft.apply {
                textSize = 15f
                axisMinimum = 0f
                isGranularityEnabled = true
                granularity = 1f
            }
            axisRight.isEnabled = false
        }
        //⑦chart更新
        binding.wastedLineChart.let {
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
