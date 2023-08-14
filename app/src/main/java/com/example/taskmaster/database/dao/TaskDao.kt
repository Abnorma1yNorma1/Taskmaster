package com.example.taskmaster.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.model.BooleanStandIn
import com.example.taskmaster.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(task: Task)

    @Query("UPDATE taskTable SET description = :description WHERE id = :id")
    fun updateDescription(id: Long, description: String)

    @Query("UPDATE taskTable SET completed = :completed WHERE id = :id")
    fun updateCompleted(id: Long, completed: Byte)

    @Query("SELECT completed FROM taskTable WHERE id = :id")
    fun getCompletedBool(id: Long):Byte

    @Query("SELECT * FROM taskTable WHERE completed = :completed")
    fun getTasksByCompletion(completed: Byte): List<Task>

    @Query("UPDATE taskTable SET priority = :priority WHERE id = :id")
    fun updatePriority(id: Long, priority: Byte)

    @Query("UPDATE taskTable SET tagList = :tagList WHERE id = :id")
    fun updateTagList(id: Long, tagList: List<Int>)

    @Query("SELECT tagList FROM taskTable WHERE id = :id")
    fun getTagsAsString(id:Long): String

    @Query("SELECT * FROM taskTable WHERE tagList LIKE :tagQuery")
    fun getTaskIdByMatchingTags(tagQuery: List<Int>): List<Task>

    @Query("UPDATE taskTable SET expirationDate = :expirationDate WHERE id = :id")
    fun updateExpirationDate(id: Long, expirationDate: Long?)

    @Query("UPDATE taskTable SET notify = :notify WHERE id = :id")
    fun updateNotify(id: Long, notify: Byte)

    @Query("UPDATE taskTable SET notifyTime = :notifyTime WHERE id = :id")
    fun updateNotifyTime(id: Long, notifyTime: Long?)

    @Query("UPDATE taskTable SET superTask = :superTask WHERE id = :id")
    fun updateSuperTask(id: Long, superTask: Long)

    @Query("SELECT * FROM taskTable WHERE id = :id")
    fun getTaskById(id: Long): Task

    @Query("SELECT * FROM taskTable WHERE expirationDate < :date " +
            "AND completed = :completed ")
    fun getCurrentTasks(date: Long, completed: Byte = FALSE): List<Task>

    @Query("SELECT * FROM taskTable WHERE expirationDate < :date " +
            "AND completed = :completed AND superTask IS NULL ")
    fun getCurrentTasksLiveData(date: Long, completed: Byte = FALSE): LiveData <List<Task>>


    @Query("SELECT * FROM taskTable WHERE id = :id")
    fun getTaskLiveData(id: Long): LiveData <Task>

    @Query("SELECT * FROM taskTable WHERE superTask = :id")
    fun getSubtasksOf(id: Long): List<Task>

    @Query("SELECT * FROM taskTable WHERE superTask = :id")
    fun getSubtasksLiveDataOf(id: Long): LiveData<List<Task>>

    @Query("SELECT * FROM taskTable")
    fun getAllTasks(): List<Task>

    @Query("DELETE FROM taskTable WHERE id = :id")
    fun delete(id:Long)

    @Query("DELETE FROM taskTable")
    fun deleteAll()

    companion object{
        @JvmStatic
        val FALSE = BooleanStandIn.FALSE.value
        @JvmStatic
        val TRUE = BooleanStandIn.TRUE.value
    }
}
