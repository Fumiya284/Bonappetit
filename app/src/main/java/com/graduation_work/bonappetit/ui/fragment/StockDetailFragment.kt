package com.graduation_work.bonappetit.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.StockDetailBinding
import com.graduation_work.bonappetit.ui.adapter.RecipeListAdapter
import com.graduation_work.bonappetit.ui.view_model.StockDetailViewModel
import com.graduation_work.bonappetit.ui.view_model.StockDetailViewModel.Message
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StockDetailFragment : Fragment() {
	private val args: StockDetailFragmentArgs by navArgs()
	private val viewModel: StockDetailViewModel by viewModel { parametersOf(args.stockId) }
	private lateinit var recipeListAdapter: RecipeListAdapter
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockDetailBinding.inflate(inflater, container, false).also { it ->
			it.viewModel = viewModel
			it.lifecycleOwner = viewLifecycleOwner
			it.recipeRv.apply {
				recipeListAdapter = RecipeListAdapter(viewModel)
				adapter = recipeListAdapter
				layoutManager = LinearLayoutManager(activity)
			}
			
			viewModel.recipe
				.onEach { recipes -> recipeListAdapter.submitList(recipes) }
				.launchIn(lifecycleScope)
			
			viewModel.message
				.onEach { message -> onMessage(message) }
				.launchIn(lifecycleScope)
		}.root
	}
	
	private fun onMessage(message: Message) {
		when(message) {
			is Message.MoveToManager -> { onMessageMoveToManager() }
			is Message.OpenUrl -> { onMessageOpenUrl(message) }
			is Message.DisplayLimitExceedDialog -> { onMessageDisplayLimitExceedDialog() }
		}
	}
	
	private fun onMessageMoveToManager() {
		findNavController().navigate(R.id.action_detail_to_main)
	}
	
	private fun onMessageOpenUrl(message: Message.OpenUrl) {
		val intent = Intent(Intent.ACTION_VIEW, message.url)
		startActivity(intent)
	}
	
	private fun onMessageDisplayLimitExceedDialog() {
		val items = arrayOf<String>("美味しくなかった", "期限切れ", "傷んでしまった", "その他")
		val defaultItem = 0
		var checkedItem = defaultItem
		
		AlertDialog.Builder(this.context)
			.setTitle("廃棄理由を選択してください")
			.setSingleChoiceItems(items, defaultItem, DialogInterface.OnClickListener { _, which ->
				checkedItem = which
			})
			.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
				viewModel.markAsConsumed(items[checkedItem])
			})
			.setNegativeButton("キャンセル", null)
			.show()
	}
}