package com.example.taskmaster.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.model.Tag
import com.example.taskmaster.model.Task
import com.example.taskmaster.repository.CompletedTaskRepository
import com.example.taskmaster.repository.TagRepository
import com.example.taskmaster.repository.TaskRepository
import com.example.taskmaster.repository.TimePeriodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.Clock

class CurrentTasksViewModel(
    private val taskRepository: TaskRepository,
    private val completedTaskRepository: CompletedTaskRepository,
    private val tagRepository: TagRepository,
    private val timePeriodRepository: TimePeriodRepository
) : ViewModel() {

    private val job = SupervisorJob()
    private val currentScope = CoroutineScope(job + Dispatchers.IO)

    private val currentTasksLive: LiveData<List<Task>> =
        taskRepository.getCurrentTasksLiveData(getCurrentTime())

    private val currentSubtasks: MutableMap<Long, List<Task>> = mutableMapOf()
    lateinit var currentSubtasksMediator: MutableLiveData<Map<Long, List<Task>>>

    private val currentTags: MutableMap<Long, List<Tag>> = mutableMapOf()
    lateinit var currentTagMediator: MutableLiveData<Map<Long, List<Tag>>>

    private val currentSubTags: MutableMap<Long, List<Tag>> = mutableMapOf()
    lateinit var currentSubTagMediator: MutableLiveData<Map<Long, List<Tag>>>

    private fun getCurrentTime(): Long {
        return Clock.systemUTC().millis()
    }


    fun setCurrentSubtasks() {
        val mediator = MediatorLiveData<Map<Long, List<Task>>>()
        mediator.addSource(currentTasksLive) {
            currentSubtasks.clear()
            it.forEach { listEntry ->
                currentScope.launch {
                    currentSubtasks[listEntry.id] =
                        currentScope.async { taskRepository.getSubtasksOf(listEntry.id) }.await()
                }
            }
        }
        currentSubtasksMediator = mediator
    }

    fun setCurrentTag() {
        val mediator = MediatorLiveData<Map<Long, List<Tag>>>()
        mediator.addSource(currentTasksLive) {
            currentTags.clear()
            it.forEach { listEntry ->
                currentScope.launch {
                    currentTags[listEntry.id] =
                        currentScope.async {
                            val tagList = mutableListOf<Tag>()
                            taskRepository.getTags(listEntry.id).forEach { tagId ->
                                tagList.add(tagRepository.getTag(tagId))
                            }
                            tagList
                        }.await()
                }
            }
        }
        currentTagMediator = mediator
    }

    fun setCurrentSubTags() {
        val mediator = MediatorLiveData<Map<Long, List<Tag>>>()
        mediator.addSource(currentSubtasksMediator) {
            currentSubTags.clear()
            it.forEach { mapEntry ->
                mapEntry.value.forEach { listEntry ->
                    currentScope.launch {
                        currentSubTags[listEntry.id] =
                            currentScope.async {
                                val tagList = mutableListOf<Tag>()
                                taskRepository.getTags(listEntry.id).forEach { tagId ->
                                    tagList.add(tagRepository.getTag(tagId))
                                }
                                tagList
                            }.await()
                    }
                }

            }
        }
        currentTagMediator = mediator
    }

    fun getCurrentTasksLive(): LiveData<List<Task>> {
        return currentTasksLive
    }

    fun getCurrentSubtasks(): MutableMap<Long, List<Task>> {
        return currentSubtasks
    }

    fun getCurrentTag(): MutableMap<Long, List<Tag>> {
        return currentTags
    }

    fun getCurrentSubTag(): MutableMap<Long, List<Tag>> {
        return currentSubTags
    }

    private fun changeTaskCompletion(id: Long) =
        currentScope.launch { taskRepository.changeTaskCompletion(id) }

    private suspend fun isTaskComplete(id: Long) =
        currentScope.async { taskRepository.isComplete(id) }.await()

    private fun addToCompletedToday(id: Long) =
        currentScope.launch { completedTaskRepository.insertTaskId(id) }

    private fun returnFromToCompletedToday(id: Long) {
        currentScope.launch {
            if (checkIfExists(id)) {
                completedTaskRepository.deleteByTaskID(id)
            }
        }
    }


    private suspend fun checkIfExists(id: Long) =
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
    private val completedTaskRepository: CompletedTaskRepository,
    private val tagRepository: TagRepository,
    private val timePeriodRepository: TimePeriodRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentTasksViewModel::class.java)) {
            return CurrentTasksViewModel(repository, completedTaskRepository,tagRepository, timePeriodRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown viewModel!")
    }
}