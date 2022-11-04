package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.graduation_work.bonappetit.databinding.MockFragmentBinding

class MockFragment: Fragment() {
    private lateinit var binding: MockFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MockFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}