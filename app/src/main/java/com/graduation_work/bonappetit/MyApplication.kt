package com.graduation_work.bonappetit

import android.app.Application
import androidx.room.Room
import com.graduation_work.bonappetit.data.database.AppDatabase
import com.graduation_work.bonappetit.data.repository.StockRepositoryImpl
import com.graduation_work.bonappetit.domain.repository.StockRepository
import com.graduation_work.bonappetit.domain.use_case.StockListUseCase
import com.graduation_work.bonappetit.ui.view_model.StockListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
        private val useCaseModule = module {
            single { StockListUseCase() }
        }
        private val repositoryModule = module {
            single<StockRepository> { StockRepositoryImpl() }
        }
        
        private val viewModelModule = module {
            viewModel { StockListViewModel() }
        }
    }

    override fun onCreate() {
        super.onCreate()
        
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