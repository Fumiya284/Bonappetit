package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.graduation_work.bonappetit.databinding.WastedHistoryFragmentBinding

class WastedHistoryFragment : Fragment() {

    private var _binding: WastedHistoryFragmentBinding? = null
    val binding: WastedHistoryFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WastedHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
