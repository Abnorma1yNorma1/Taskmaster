package com.example.taskmaster.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskmaster.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(task: Task)

    @Query("UPDATE taskTable SET description = :description WHERE id = :id")
    fun updateDescription(id:Long, description: String)

    @Query("UPDATE taskTable SET completed = :completed WHERE id = :id")
    fun updateCompleted(id:Long, completed: Char)

    @Query("SELECT * FROM taskTable WHERE completed = :$TRUE")
    fun getCompleteTasks():List<Task>


    companion object {

        const val TRUE: Char = Task.TRUE
        const val FALSE: Char = Task.FALSE
    }

}
