package com.example.taskmaster.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.model.CompletedTodayTasks
import com.example.taskmaster.model.Task

@Dao
interface CompletedTasksDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(completedTodayTasks: CompletedTodayTasks)

    @Query("SELECT EXISTS(SELECT 1 FROM completedToday WHERE taskId = :taskId)")
    fun exists(taskId: Long):Boolean

    @Query("DELETE FROM completedToday WHERE taskId = :taskId")
    fun deleteByTaskId(taskId:Long)

    @Query("DELETE FROM completedToday")
    fun deleteAll()
}