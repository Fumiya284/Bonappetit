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
import com.graduation_work.bonappetit.databinding.WastedHistoryFragmentBinding
import com.graduation_work.bonappetit.ui.view_model.WastedHistoryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WastedHistoryFragment : Fragment() {

    private var _binding: WastedHistoryFragmentBinding? = null
    val binding: WastedHistoryFragmentBinding
        get() = _binding!!

    private val viewModel: WastedHistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WastedHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.chartData.collect { drawChart(it) } }
            }
        }
    }

    private fun drawChart(chartData: LineData) {
        //⑤ChartにData格納
        binding.wastedLineChart.apply {
            data = chartData
            //⑥Chartのフォーマット指定
            description.isEnabled = false
            xAxis.apply {
                isEnabled = true
                textColor = Color.BLACK
                position = XAxis.XAxisPosition.BOTTOM
                isGranularityEnabled = true
                granularity = 1f
                valueFormatter = IndexAxisValueFormatter(viewModel.xAxisValues)
            }
            axisLeft.textSize = 15f
            axisLeft.axisMinimum = 0f
            axisRight.isEnabled = false
        }
        //⑦chart更新
        binding.wastedLineChart.let {
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
