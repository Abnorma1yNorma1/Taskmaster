package com.example.taskmaster

import android.app.Application
import com.example.taskmaster.database.AppDatabase

class taskmasterApp: Application() {

    val database by lazy { AppDatabase.getDatabase(this)}

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: taskmasterApp
    }
}