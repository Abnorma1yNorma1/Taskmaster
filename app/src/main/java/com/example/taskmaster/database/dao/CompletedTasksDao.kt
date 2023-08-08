package com.example.taskmaster.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.model.Task

@Dao
interface CompletedTasksDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(id: Long)

    @Query("UPDATE completedToday SET date = :date WHERE taskId = :taskId")
    fun updateDate(taskId: Long, date: Long)

    @Query("SELECT * FROM completedToday WHERE taskId = :taskId")
    fun getTaskById(taskId: Long)

    @Query("DELETE FROM completedToday WHERE taskId = :taskId")
    fun delete(taskId:Long)

    @Query("DELETE FROM completedToday")
    fun deleteAll()
}