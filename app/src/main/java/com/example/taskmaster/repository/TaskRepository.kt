package com.example.taskmaster.repository

import com.example.taskmaster.database.dao.TaskDao
import com.example.taskmaster.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TaskRepository(val taskDao: TaskDao) {

    private val job = SupervisorJob()
    private val taskScope = CoroutineScope(job + Dispatchers.IO)

    suspend fun insertTask(task: Task) {
        taskScope.launch {
            taskDao.incert(task)
        }
    }

    suspend fun updateTask(task: Task) {
        taskScope.launch {
            taskDao.update(task)
        }
    }

    suspend fun getCompleteTasks():List<Task> {
       return taskScope.async {
            taskDao.getCompleteTasks()
        }.await()
    }

}