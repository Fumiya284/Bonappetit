package com.graduation_work.bonappetit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.graduation_work.bonappetit.R
import com.graduation_work.bonappetit.databinding.StockRegisterBinding
import com.graduation_work.bonappetit.ui.adapter.FoodSelectorAdapter
import com.graduation_work.bonappetit.ui.view_model.StockRegisterViewModel
import com.graduation_work.bonappetit.ui.view_model.StockRegisterViewModel.Message
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

/*
	クラスを作る基準とかパッケージの分け方がよくない気がする　adapterとかfragmentとかで区分してるけどどのクラスがどれ使ってるのか分りずらい
	あと小さいリスナをラムダ式で書いたりインナークラスにしたり場当たり的に書いてるからどこに何があるか分らない
	MVVMに似せてるつもりだけどほんとに責務の分離できてるのか？
	
	食材選択と個数入力だけ作った
	期間入力は作成中
	入力不足とかDBに登録失敗したとかのメッセージも後でつくる
	食材選択メニューのリスナをこのクラスの中に持たせてるけど別のクラスにした方がいいんだろうか
	キャンセルない　登録押したら登録するまで戻れない
 */
class StockRegisterFragment : Fragment() {
	private val viewModel: StockRegisterViewModel by viewModel()
	private lateinit var foodSelectorAdapter: FoodSelectorAdapter   // ここで初期化すると落ちる たぶんrequireContext()を呼び出せないから
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return StockRegisterBinding.inflate(inflater, container, false).also {
			it.viewModel = viewModel
			it.lifecycleOwner = viewLifecycleOwner
			it.foodSelectorSpn.apply {
				foodSelectorAdapter = FoodSelectorAdapter(requireContext(), emptyList())
				adapter = foodSelectorAdapter
				onItemSelectedListener = FoodSelectorListener(viewModel)
			}
		}.also { binding ->
			viewModel.message
				.onEach { onMessage(it) }
				.launchIn(lifecycleScope)
			
			viewModel.foods
				.onEach { binding.foodSelectorSpn.adapter = foodSelectorAdapter.getAdapterWithNewItem(it.map { food -> food.name }) }
				.launchIn(lifecycleScope)
		}.root
	}
	
	private fun onMessage(message: Message) {
		when(message) {
			Message.MOVE_TO_STOCK_MANAGER -> findNavController().navigate(R.id.action_register_to_main)
			Message.DISPLAY_ALERT -> {Toast.makeText(context, getString(R.string.sr_alert), Toast.LENGTH_SHORT).show()}
		}
	}
	
	// リスナ増えたら別のクラス作るかも
	private class FoodSelectorListener(private val viewModel: StockRegisterViewModel) : AdapterView.OnItemSelectedListener {
		override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
			val chosenFood = viewModel.foods.value[position]    // getItemAtPosition使ったほうが良い気がするけどAnyで返されるから型チェックしなきゃいけないそして失敗したときの対処考えるのめんどくさい　いい方法あったら教えて
			viewModel.onFoodChoice(chosenFood)
		}
		
		override fun onNothingSelected(parent: AdapterView<*>?) {
			viewModel.onFoodChoice(null)
		}
	}
}