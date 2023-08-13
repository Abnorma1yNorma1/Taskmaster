package com.example.taskmaster.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.model.Tag
import com.example.taskmaster.model.ValidateState
import com.example.taskmaster.repository.TagRepository
import com.example.taskmaster.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class TaskEditViewModel (private val tagRepository: TagRepository, private val taskRepository: TaskRepository):ViewModel(){

    private val job = SupervisorJob()
    private val taskScope = CoroutineScope(job + Dispatchers.IO)

    private val _taskState: MutableLiveData<ValidateState> = MutableLiveData(ValidateState.DEFAULT)
    val taskState: LiveData<ValidateState> = _taskState

    private val _tagList :LiveData<List<Tag>> = tagRepository.getAllLiveData()
    val tagList: LiveData<List<Tag>> = _tagList

    val chosenTagList: MutableLiveData<List<Int>> = MutableLiveData()

    val date= MutableLiveData<Long>()

    val time= MutableLiveData<Long>()

    fun addChosenTag(id:Int){
        chosenTagList.postValue(chosenTagList.value?.plus(id))
    }

    fun removeChosenTag(id: Int){
        chosenTagList.postValue(chosenTagList.value?.minus(id))
    }

    fun removeAllChosenTags(){
        chosenTagList.postValue(mutableListOf<Int>())
    }

    fun setDate(date:Long){
        this.date.value = date

    }

    fun setTime(time:Long){
        this.time.value = time
    }
}

class TaskEditViewModelFactory(
    private val tagRepository: TagRepository,
    private val taskRepository: TaskRepository
): ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(TaskEditViewModel::class.java)){
            return TaskEditViewModel(tagRepository,taskRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown viewModel!")
    }
}