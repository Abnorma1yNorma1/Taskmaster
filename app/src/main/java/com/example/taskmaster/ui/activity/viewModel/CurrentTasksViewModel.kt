package com.example.taskmaster.ui.activity.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.model.Task
import com.example.taskmaster.repository.CompletedTaskRepository
import com.example.taskmaster.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.Clock

class CurrentTasksViewModel(
    private val taskRepository: TaskRepository,
    private val completedTaskRepository: CompletedTaskRepository
) : ViewModel() {

    private val currentTasks: LiveData<List<Task>> =
        taskRepository.getCurrentTasksLiveData(getCurrentTime())

    private val job = SupervisorJob()
    private val currentScope = CoroutineScope(job + Dispatchers.IO)

    private fun getCurrentTime(): Long {
        return Clock.systemUTC().millis()
    }

    fun getCurrentTasks(): LiveData<List<Task>> {
        return currentTasks
    }

    fun changeTaskCompletion(id: Long) =
        currentScope.launch { taskRepository.changeTaskCompletion(id) }

    suspend fun isTaskComplete(id: Long) =
        currentScope.async { taskRepository.isComplete(id) }.await()

    fun addToCompletedToday(id: Long) =
        currentScope.launch { completedTaskRepository.insertTaskId(id) }

    fun returnFromToCompletedToday(id: Long) {
        currentScope.launch {
            if (checkIfExists(id)) {
                completedTaskRepository.deleteByTaskID(id)
            }
        }
    }


    suspend fun checkIfExists(id: Long) =
        currentScope.async { completedTaskRepository.isExist(id) }.await()

    fun processCompleteTaskButton(id: Long) =
        currentScope.launch {
            changeTaskCompletion(id)
            if (isTaskComplete(id)) {
                addToCompletedToday(id)
            } else {
                returnFromToCompletedToday(id)
            }
        }


}

class CurrentTasksViewModelFactory(
    private val repository: TaskRepository,
    private val completedTaskRepository: CompletedTaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentTasksViewModel::class.java)) {
            return CurrentTasksViewModel(repository, completedTaskRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown viewModel!")
    }
}