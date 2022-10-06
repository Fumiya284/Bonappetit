package com.graduation_work.bonappetit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.graduation_work.bonappetit.databinding.StockListFragmentBinding

class StockListFragment: Fragment() {
	private lateinit var binding: StockListFragmentBinding
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = StockListFragmentBinding.inflate(inflater, container, false)
		return binding.root
	}
}