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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.graduation_work.bonappetit.databinding.TopHistoryFragmentBinding
import com.graduation_work.bonappetit.ui.view_model.TopHistoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopHistoryFragment : Fragment() {

    private var _binding: TopHistoryFragmentBinding? = null
    val binding: TopHistoryFragmentBinding
        get() = _binding!!

    private val viewModel: TopHistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TopHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNoDataText(binding.consumptionAndWastedPieChart)
        showNoDataText(binding.reasonForWastedPieChart)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.consumptionAndWastedChartData.collect {
                        drawChart(binding.consumptionAndWastedPieChart, it, "消費と廃棄")
                    }
                }
                launch {
                    viewModel.reasonForWastedChartData.collect {
                        drawChart(binding.reasonForWastedPieChart, it, "廃棄理由")
                    }
                }
            }
        }
    }

    private fun showNoDataText(pieChart: PieChart) {
        pieChart.let {
            it.setNoDataText("データが存在しません")
            it.setNoDataTextColor(Color.BLACK)
            it.getPaint(Chart.PAINT_INFO).textSize = 60f
        }
    }

    private fun drawChart(pieChart: PieChart, chartData: PieData, title: String) {
        if (chartData.dataSets.isEmpty()) {
            pieChart.let {
                it.clear()
                it.invalidate()
            }
            return
        }

        pieChart.apply {
            //⑤PieChartにPieData格納
            data = chartData
            //⑥Chartのフォーマット指定
            description.isEnabled = false
            legend.textSize = 20f
            legend.isWordWrapEnabled = true
            centerText = title
        }
        //⑦PieChart更新
        pieChart.let {
            it.setCenterTextSize(25f)
            it.setEntryLabelColor(Color.BLACK)
            it.setUsePercentValues(true)
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
