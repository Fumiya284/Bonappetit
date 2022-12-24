package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.StockDetailBinding
import com.graduation_work.bonappetit.ui.view_model.StockDetailViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StockDetailFragment : Fragment() {
	private val args: StockDetailFragmentArgs by navArgs()
	private val viewModel: StockDetailViewModel by viewModel { parametersOf(args.stockId) }
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockDetailBinding.inflate(inflater, container, false).also {
			it.viewModel = viewModel
			it.lifecycleOwner = viewLifecycleOwner
			it.sdBackBtn.setOnClickListener {
				onCancel()
			}
		}.root
	}
	
	fun onCancel() {
		findNavController().navigate(R.id.action_detail_to_main)
	}
}