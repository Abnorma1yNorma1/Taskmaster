package com.example.taskmaster.repository

import com.example.taskmaster.database.dao.CompletedTasksDao
import com.example.taskmaster.model.CompletedTodayTasks
import com.example.taskmaster.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CompletedTaskRepository(private val completedTaskDao: CompletedTasksDao) {

    private val job = SupervisorJob()
    private val taskScope = CoroutineScope(job + Dispatchers.IO)

    fun insertTaskId(id: Long) {
        taskScope.launch {
            completedTaskDao.insert(CompletedTodayTasks(id))
        }
    }

    fun deleteByTaskID(id: Long) {
        taskScope.launch {
            completedTaskDao.deleteByTaskId(id)
        }
    }

    suspend fun isExist(id: Long): Boolean {
        return taskScope.async {
            completedTaskDao.exists(id)
        }.await()
    }

    fun deleteAll(){
        taskScope.launch {
            completedTaskDao.deleteAll()
        }
    }

}