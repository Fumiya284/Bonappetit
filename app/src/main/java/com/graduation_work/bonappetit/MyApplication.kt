package com.graduation_work.bonappetit

import android.app.Application
import androidx.room.Room
import com.graduation_work.bonappetit.data.database.AppDatabase

class MyApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}