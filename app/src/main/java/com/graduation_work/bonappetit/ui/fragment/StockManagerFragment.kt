package com.graduation_work.bonappetit.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.StockManagerBinding
import com.graduation_work.bonappetit.ui.adapter.StockListAdapter
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel.Message
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockManagerFragment: Fragment() {
	private val viewModel: StockManagerViewModel by viewModel()
	private lateinit var stockListAdapter: StockListAdapter // viewLifeCycleOwnerが必要なためここで初期化すると落ちる
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockManagerBinding.inflate(inflater, container, false).also {
			it.viewModel = viewModel
			it.lifecycleOwner = viewLifecycleOwner
			it.stockListRv.apply {
				stockListAdapter = StockListAdapter(viewLifecycleOwner, viewModel)
				adapter = stockListAdapter
				layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
			}
		
			viewModel.message
				.onEach { msg -> onMessage(msg) }
				.launchIn(lifecycleScope)
			
			viewModel.stocks
				.onEach { stocks ->  stockListAdapter.submitList(stocks) }
				.launchIn(lifecycleScope)
		}.root
	}
	
	private fun onMessage(message: Message) {
		when (message) {
			is Message.DisplaySearchDialog -> onMsgDisplaySearchDialog()
			is Message.MoveToRegisterScreen -> onMsgMoveToRegisterScreen()
			is Message.MoveToDetailScreen -> onMsgMoveToDetailScreen(message)
		}
	}
	
	private fun onMsgMoveToRegisterScreen() {
		findNavController().navigate(R.id.action_main_to_register)
	}
	
	private fun onMsgDisplaySearchDialog() {
		val categoryList = viewModel.categories.value.keys.toTypedArray()
		val checkedItem = viewModel.categories.value.values.toBooleanArray()
		
		AlertDialog.Builder(this.context)
			.setTitle(getString(R.string.sm_search_dialog_title))
			.setMultiChoiceItems(categoryList, checkedItem) { _, which, isChecked ->
				viewModel.onDialogItemClick(categoryList[which], isChecked)
			}
			.show()
	}
	
	private fun onMsgMoveToDetailScreen(message: Message.MoveToDetailScreen) {
		val action = MainScreenFragmentDirections.actionMainToDetail(message.stock.id)
		findNavController().navigate(action)
	}
}