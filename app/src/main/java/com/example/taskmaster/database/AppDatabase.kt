package com.example.taskmaster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmaster.database.dao.TaskDao
import com.example.taskmaster.model.Task

@Database(
    entities = [Task::class],
    version = AppDatabase.DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao


    companion object {
        const val DATABASE_VERSION = 1
        private val DATABASE_NAME = "TaskmasterDatabase"

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}