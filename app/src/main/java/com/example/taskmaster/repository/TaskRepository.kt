package com.example.taskmaster.repository

import com.example.taskmaster.database.dao.TaskDao
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

    suspend fun getCurrentTasks(date:Long): List<Task> {
        return taskScope.async {
            taskDao.getCurrentTasks(date)
        }.await()
    }

    fun getCurrentTasksLiveData(date:Long) = taskDao.getCurrentTasksLiveData(date)

    suspend fun deleteAllTasks() {
        taskScope.launch {
            taskDao.deleteAll()
        }
    }

}