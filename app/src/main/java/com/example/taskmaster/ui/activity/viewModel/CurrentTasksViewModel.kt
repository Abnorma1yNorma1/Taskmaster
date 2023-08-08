package com.example.taskmaster.ui.activity.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskmaster.model.Task
import com.example.taskmaster.repository.TaskRepository
import java.time.Clock

class CurrentTasksViewModel(repository: TaskRepository) : ViewModel() {

    val currentTasks: LiveData<List<Task>> = repository.getCurrentTasksLiveData(getCurrentTime())



    private fun getCurrentTime():Long{
       return Clock.systemUTC().millis()
    }

}

class CurrentTasksViewModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        if (modelClass.isAssignableFrom(CurrentTasksViewModel::class.java)){
           return CurrentTasksViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown viewModel!")
    }
}