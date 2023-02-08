package com.graduation_work.bonappetit

import android.app.Application
import android.graphics.Bitmap
import androidx.room.Room
import com.graduation_work.bonappetit.data.database.AppDatabase
import com.graduation_work.bonappetit.data.repository.CalendarRepositoryImpl
import com.graduation_work.bonappetit.data.files.ImageFileAccessor
import com.graduation_work.bonappetit.data.repository.FoodRepositoryImpl
import com.graduation_work.bonappetit.data.repository.RecipeRepositoryImpl
import com.graduation_work.bonappetit.data.repository.HistoryRepositoryImpl
import com.graduation_work.bonappetit.data.repository.StockRepositoryImpl
import com.graduation_work.bonappetit.domain.repository.CalendarRepository
import com.graduation_work.bonappetit.domain.repository.FoodRepository
import com.graduation_work.bonappetit.domain.repository.RecipeRepository
import com.graduation_work.bonappetit.domain.repository.HistoryRepository
import com.graduation_work.bonappetit.domain.repository.StockRepository
import com.graduation_work.bonappetit.domain.use_case.StockDetailUseCase
import com.graduation_work.bonappetit.domain.use_case.StockManagerUseCase
import com.graduation_work.bonappetit.domain.use_case.StockRegisterUseCase
import com.graduation_work.bonappetit.ui.view_model.StockDetailViewModel
import com.graduation_work.bonappetit.ui.view_model.StockManagerViewModel
import com.graduation_work.bonappetit.ui.view_model.StockRegisterViewModel
import com.graduation_work.bonappetit.ui.view_model.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
        
        const val flowTimeoutMillis: Long = 50000000
        
        val imageFileFormat = Bitmap.CompressFormat.JPEG
        const val imageQuality = 100
    }
    
    override fun onCreate() {
        super.onCreate()
    
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    
        val appModule = module {
            viewModel { StockManagerViewModel(this@MyApplication) }
            viewModel { StockRegisterViewModel(this@MyApplication) }
            viewModel { (stockId: Long) -> StockDetailViewModel(this@MyApplication, stockId) }
            viewModel { TopHistoryViewModel() }
            viewModel { ConsumptionHistoryViewModel() }
            viewModel { WastedHistoryViewModel() }
            viewModel { CalendarViewModel() }
        }
    
        val repositoryModule = module {
            single<StockRepository> { StockRepositoryImpl() }
            single<FoodRepository> { FoodRepositoryImpl() }
            single<HistoryRepository> { HistoryRepositoryImpl() }
            single<CalendarRepository> { CalendarRepositoryImpl() }
        }
    
        val useCaseModule = module {
            single { StockManagerUseCase() }
            single { StockRegisterUseCase() }
            factory { StockDetailUseCase() }
            
            single<HistoryRepository> { HistoryRepositoryImpl() }
            single<StockRepository> { StockRepositoryImpl() }
            single<FoodRepository> { FoodRepositoryImpl() }
            single<RecipeRepository> { RecipeRepositoryImpl() }
            single { ImageFileAccessor(this@MyApplication) }
        }
        
        startKoin {
            modules(appModule)
        }
    }
}