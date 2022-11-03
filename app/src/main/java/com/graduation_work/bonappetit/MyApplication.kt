package com.graduation_work.bonappetit

import android.app.Application
import androidx.room.Room
import com.graduation_work.bonappetit.data.database.AppDatabase
import com.graduation_work.bonappetit.data.repository.FoodRepositoryImpl
import com.graduation_work.bonappetit.data.repository.StockRepositoryImpl
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import com.graduation_work.bonappetit.domain.repository.StockRepository
import com.graduation_work.bonappetit.domain.use_case.StockManagerUseCase
import com.graduation_work.bonappetit.domain.use_case.StockRegisterUseCase
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel
import com.graduation_work.bonappetit.ui.view_model.StockRegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }
    
    override fun onCreate() {
        super.onCreate()
    
        val viewModelModule = module {
            viewModel { StockManagerViewModel(this@MyApplication) }
            viewModel { StockRegisterViewModel() }
        }
    
        val repositoryModule = module {
            single<StockRepository> { StockRepositoryImpl() }
            single<FoodRepository> { FoodRepositoryImpl() }
        }
    
        val useCaseModule = module {
            factory { StockManagerUseCase() }
            factory { StockRegisterUseCase() }
        }
        
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
        
        startKoin {
            modules(useCaseModule, repositoryModule, viewModelModule)
        }
    }
}