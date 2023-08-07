package com.example.taskmaster.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.model.Task
import java.time.OffsetDateTime

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(task: Task)

    @Query("UPDATE taskTable SET description = :description WHERE id = :id")
    fun updateDescription(id: Long, description: String)

    @Query("UPDATE taskTable SET completed = :completed WHERE id = :id")
    fun updateCompleted(id: Long, completed: Char)

    @Query("SELECT * FROM taskTable WHERE completed = :completed")
    fun getTasksByCompletion(completed: Char): List<Task>

    @Query("UPDATE taskTable SET priority = :priority WHERE id = :id")
    fun updatePriority(id: Long, priority: Byte)

    @Query("UPDATE taskTable SET tagList = :tagList WHERE id = :id")
    fun updateTagList(id: Long, tagList: String)

    @Query("SELECT * FROM taskTable WHERE tagList LIKE :tagQuery")
    fun getTaskIdByMatchingTag(tagQuery: String): Long

    @Query("UPDATE taskTable SET expirationDate = :expirationDate WHERE id = :id")
    fun updateExpirationDate(id: Long, expirationDate: Long?)

    @Query("UPDATE taskTable SET notify = :notify WHERE id = :id")
    fun updateNotify(id: Long, notify: Char)

    @Query("UPDATE taskTable SET notifyTime = :notifyTime WHERE id = :id")
    fun updateNotifyTime(id: Long, notifyTime: Long?)

    @Query("UPDATE taskTable SET superTask = :superTask WHERE id = :id")
    fun updateESuperTask(id: Long, superTask: Long)

    @Query("SELECT * FROM taskTable WHERE id = :id")
    fun getTaskById(id: Long): Task

    @Query("SELECT * FROM taskTable")
    fun getAllTasks(): List<Task>

    @Query("DELETE FROM taskTable WHERE id = :id")
    fun delete(id:Long)

    @Query("DELETE FROM taskTable")
    fun deleteAll()
}
