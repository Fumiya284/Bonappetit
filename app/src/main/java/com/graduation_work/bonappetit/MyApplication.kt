package com.graduation_work.bonappetit

import android.app.Application
import androidx.room.Room
import com.graduation_work.bonappetit.data.database.AppDatabase
import org.koin.core.context.startKoin

class MyApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        
        startKoin {
        
        }
        
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}