package com.graduation_work.bonappetit.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.CalendarDayBinding
import com.graduation_work.bonappetit.databinding.CalendarFragmentBinding
import com.graduation_work.bonappetit.databinding.CalendarHeaderBinding
import com.graduation_work.bonappetit.ui.adapter.CalendarStockListAdapter
import com.graduation_work.bonappetit.ui.view_model.CalendarViewModel
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: CalendarFragmentBinding? = null
    val binding: CalendarFragmentBinding
        get() = _binding!!

    private val viewModel: CalendarViewModel by viewModel()

    private val stockListAdapter = CalendarStockListAdapter {
        Toast.makeText(requireContext(), it.foodName, Toast.LENGTH_LONG)
            .show()
    }

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CalendarFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rV.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = stockListAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        binding.calendar.monthScrollListener = {
            viewLifecycleOwner.lifecycleScope.launch {
                binding.monthTitle.text = if (it.yearMonth.year == today.year) {
                    viewModel.titleSameYearFormatter.format(it.yearMonth)
                } else {
                    viewModel.titleFormatter.format(it.yearMonth)
                }
                viewModel.reloadCalendarData(it.yearMonth)
                binding.calendar.notifyMonthChanged(it.yearMonth)
                // Select the first day of the visible month.
                selectDate(it.yearMonth.atDay(1))
            }
        }

        binding.nextMonthImage.setOnClickListener {
            binding.calendar.findFirstVisibleMonth()?.let {
                binding.calendar.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }

        binding.previousMonthImage.setOnClickListener {
            binding.calendar.findFirstVisibleMonth()?.let {
                binding.calendar.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }

        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(24)
        val endMonth = currentMonth.plusMonths(24)

        configureBinders(daysOfWeek)

        binding.calendar.apply {
            setup(startMonth, endMonth, daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        if (savedInstanceState == null) {
            // Show today's events initially.
            binding.calendar.post { selectDate(today) }
        }
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendar.notifyDateChanged(it) }
            binding.calendar.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun updateAdapterForDate(date: LocalDate) {
        stockListAdapter.submitList(viewModel.stockListByLimit[date].orEmpty())
        binding.selectedDateText.text = viewModel.selectionFormatter.format(date)
    }

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        binding.calendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view, ::selectDate)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.binding.dayText
                val dotView = container.binding.dotView

                textView.text = data.date.dayOfMonth.toString()

                if (data.position == DayPosition.MonthDate) {
                    when (data.date) {
                        today -> {
                            textView.setTextColor(Color.parseColor("#D9FFFFFF"))
                            textView.setBackgroundResource(R.drawable.today_bg)
                            dotView.visibility = View.INVISIBLE
                        }
                        selectedDate -> {
                            textView.setTextColor(Color.parseColor("#1973E8"))
                            textView.setBackgroundResource(R.drawable.selected_bg)
                            dotView.visibility = View.INVISIBLE
                        }
                        else -> {
                            textView.setTextColor(Color.parseColor("#D9000000"))
                            textView.background = null
                            dotView.isVisible = viewModel.stockListByLimit[data.date].orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.setTextColor(Color.GRAY)
                    textView.background = null
                    dotView.visibility = View.INVISIBLE
                }
            }
        }
        binding.calendar.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = data.yearMonth
                        container.legendLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.getDefault())
                                tv.setTextColor(Color.parseColor("#D9000000"))
                            }
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class DayViewContainer(view: View, onClick: (LocalDate) -> Unit) : ViewContainer(view) {
    lateinit var day: CalendarDay // Will be set when this container is bound.
    val binding = CalendarDayBinding.bind(view)

    init {
        view.setOnClickListener {
            if (day.position == DayPosition.MonthDate) {
                onClick(day.date)
            }
        }
    }
}

class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.container
}