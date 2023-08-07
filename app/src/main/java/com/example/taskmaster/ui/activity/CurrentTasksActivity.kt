package com.example.taskmaster.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmaster.TaskmasterApp
import com.example.taskmaster.databinding.ActivityCurrentTaskBinding
import com.example.taskmaster.model.Task
import com.example.taskmaster.repository.TaskRepository
import com.example.taskmaster.ui.activity.viewModel.CurrentTasksViewModel

class CurrentTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentTaskBinding
    private lateinit var viewModel: CurrentTasksViewModel
    private lateinit var taskRepository: TaskRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        taskRepository = TaskRepository(TaskmasterApp.INSTANCE.database.taskDao())
        viewModel = CurrentTasksViewModel(taskRepository)
        with (TaskmasterApp.INSTANCE.database.taskDao()){
            insert(Task("do this", expirationDate = 1))
            println(getAllTasks())
            deleteAll()
        }


    }
}