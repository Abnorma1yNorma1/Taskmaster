package com.example.taskmaster.repository

import androidx.lifecycle.LiveData
import com.example.taskmaster.database.dao.TaskDao
import com.example.taskmaster.model.BooleanStandIn
import com.example.taskmaster.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskRepository(private val taskDao: TaskDao) {

    private val job = SupervisorJob()
    private val taskScope = CoroutineScope(job + Dispatchers.IO)

    suspend fun insertTask(task: Task) {
        taskScope.launch {
            taskDao.insert(task)
        }
    }

    suspend fun getAllTasks(): List<Task> {
        return taskScope.async {
            taskDao.getAllTasks()
        }.await()
    }

    suspend fun getCurrentTasks(date: Long): List<Task> {
        return taskScope.async {
            taskDao.getCurrentTasks(date)
        }.await()
    }

    fun getCurrentTasksLiveData(date: Long) = taskDao.getCurrentTasksLiveData(date)

    fun getTaskLiveData(id: Long) = taskDao.getTaskLiveData(id)

    suspend fun getSubtasksOf(id: Long): List<Task> = taskScope.async {
        taskDao.getSubtasksOf(id)
    }.await()

    fun getSubtasksLiveDataOf(id: Long): LiveData<List<Task>> = taskDao.getSubtasksLiveDataOf(id)

    suspend fun isComplete(id: Long): Boolean {
        return taskScope.async { taskDao.getCompletedBool(id) == BooleanStandIn.TRUE.value }.await()
    }

    fun changeTaskCompletion(id: Long) {
        taskScope.launch {
            if (isComplete(id)) {
                taskDao.updateCompleted(id, BooleanStandIn.FALSE.value)
            } else taskDao.updateCompleted(id, BooleanStandIn.TRUE.value)
        }
    }

    suspend fun deleteAllTasks() {
        taskScope.launch {
            taskDao.deleteAll()
        }
    }

}