package com.example.taskmaster.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmaster.TaskmasterApp
import com.example.taskmaster.databinding.ActivityCurrentTaskBinding
import com.example.taskmaster.repository.TaskRepository
import com.example.taskmaster.ui.activity.viewModel.CurrentTasksViewModel
import com.example.taskmaster.ui.activity.viewModel.CurrentTasksViewModelFactory
import androidx.lifecycle.ViewModelProvider

class CurrentTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentTaskBinding
    private lateinit var viewModel: CurrentTasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, CurrentTasksViewModelFactory(
            TaskRepository(TaskmasterApp.INSTANCE.database.taskDao())
        )).get(CurrentTasksViewModel::class.java)
        viewModel.currentTasks.observe(this){task ->
//            binding.taskRecycleView.

        }
    }
}