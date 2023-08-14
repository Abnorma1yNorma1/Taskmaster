package com.example.taskmaster.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.model.Tag
import com.example.taskmaster.model.Task
import com.example.taskmaster.model.ValidateState
import com.example.taskmaster.repository.TagRepository
import com.example.taskmaster.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class TaskEditViewModel(
    private val tagRepository: TagRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val job = SupervisorJob()
    private val taskScope = CoroutineScope(job + Dispatchers.IO)

    private val _taskState: MutableLiveData<ValidateState> = MutableLiveData(ValidateState.DEFAULT)
    val taskState: LiveData<ValidateState> = _taskState

    private val _tagList: LiveData<List<Tag>> = tagRepository.getAllLiveData()
    val tagList: LiveData<List<Tag>> = _tagList

    val chosenTagList: MutableLiveData<List<Int>> = MutableLiveData()

    val date = MutableLiveData<Long>()

    val time = MutableLiveData<Long>()

    val description = MutableLiveData<String>()

    val priority = MutableLiveData<Byte>()

    private var taskId: Long? = null

    private var superTask: Long? = null

    fun addChosenTag(id: Int) {
        chosenTagList.postValue(chosenTagList.value?.plus(id))
    }

    fun removeChosenTag(id: Int) {
        chosenTagList.postValue(chosenTagList.value?.minus(id))
    }

    fun removeAllChosenTags() {
        chosenTagList.postValue(mutableListOf<Int>())
    }

    fun setDate(date: Long) {
        this.date.value = date

    }

    fun setTime(time: Long) {
        this.time.value = time
    }

    fun editTask(
        description: String?,
        priority: String?
    ) {
        val task = isDataValid(
            description,
            priority
        ) ?: return
        taskScope.launch {
            if (taskId == null) {
                taskRepository.insertTask(task)
            } else {
                with(taskRepository) {   //TODO(properly)
                    updateDescription(taskId!!, task.description)
                    updatePriority(taskId!!, task.priority)
                    updateTagList(taskId!!, task.tagList)
                    updateExpirationDate(taskId!!, task.expirationDate!!)
                    updateNotifyTime(taskId!!, task.notifyTime!!)
                    updateSuperTask(taskId!!, task.superTask!!)
                }


            }
        }
    }

    private fun isDataValid(
        description: String?,
        priority: String?
    ): Task? {
        return if (description.isNullOrBlank() ||
            priority.isNullOrBlank()
        ) {
            _taskState.value = ValidateState.FALSE
            null
        } else {
            _taskState.value = ValidateState.TRUE
//            try {
//                priority.toByte()
//            } catch (ex: NumberFormatException) {
//                _taskState.value = ValidateState.FALSE
//            }
            val list = mutableListOf<Int>()
            tagList.value?.forEach {
                list.add(it.id)
            }
            Task(
                description = description,
                priority = priority.toByte(),
                tagList = list,
                expirationDate = date.value,
                notifyTime = time.value,
                superTask = superTask
            )
        }
    }

    fun setTaskId(id: Long) {
        taskId = id
    }

    fun setSuperTask(id: Long) {
        superTask = id
    }
}

class TaskEditViewModelFactory(
    private val tagRepository: TagRepository,
    private val taskRepository: TaskRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskEditViewModel::class.java)) {
            return TaskEditViewModel(tagRepository, taskRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown viewModel!")
    }
}