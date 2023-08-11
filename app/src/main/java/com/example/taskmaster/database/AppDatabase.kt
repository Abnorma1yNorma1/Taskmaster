package com.example.taskmaster.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmaster.database.dao.CompletedTasksDao
import com.example.taskmaster.database.dao.TagDao
import com.example.taskmaster.database.dao.TaskDao
import com.example.taskmaster.database.dao.TimePeriodDao
import com.example.taskmaster.model.CompletedTodayTasks
import com.example.taskmaster.model.Tag
import com.example.taskmaster.model.Task
import com.example.taskmaster.model.TimePeriod

@Database(
    entities = [Task::class , CompletedTodayTasks::class, Tag::class, TimePeriod::class],
    version = AppDatabase.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(TaskTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun completedTasksDao():CompletedTasksDao
    abstract fun tagDao():TagDao
    abstract fun timePeriodDao(): TimePeriodDao


    companion object {
        const val DATABASE_VERSION = 3
        private const val DATABASE_NAME = "TaskmasterDatabase"

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).addTypeConverter(TaskTypeConverters()).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}