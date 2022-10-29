package com.graduation_work.bonappetit.ui.view_model

import androidx.lifecycle.ViewModel
import com.graduation_work.bonappetit.domain.use_case.StockRegisterUseCase
import org.koin.java.KoinJavaComponent.inject

class StockRegisterViewModel : ViewModel() {
	private val useCase: StockRegisterUseCase by inject(StockRegisterUseCase::class.java)
	
}