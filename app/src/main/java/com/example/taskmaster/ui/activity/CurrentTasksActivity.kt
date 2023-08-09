package com.example.taskmaster.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.taskmaster.TaskmasterApp
import com.example.taskmaster.databinding.ActivityCurrentTaskBinding
import com.example.taskmaster.repository.TaskRepository
import com.example.taskmaster.ui.activity.viewModel.CurrentTasksViewModel
import com.example.taskmaster.ui.activity.viewModel.CurrentTasksViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmaster.R
import com.example.taskmaster.repository.CompletedTaskRepository
import com.example.taskmaster.ui.activity.recyclerAdapters.TaskClickDelegate
import com.example.taskmaster.ui.activity.recyclerAdapters.TaskRecyclerAdapter

class CurrentTasksActivity : AppCompatActivity(), TaskClickDelegate {

    private lateinit var binding: ActivityCurrentTaskBinding
    private lateinit var viewModel: CurrentTasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = TaskRecyclerAdapter(this)
        viewModel = ViewModelProvider(
            this, CurrentTasksViewModelFactory(
                TaskRepository(TaskmasterApp.INSTANCE.database.taskDao()),
                CompletedTaskRepository(TaskmasterApp.INSTANCE.database.completedTasksDao())
            )
        ).get(CurrentTasksViewModel::class.java)
        with(binding) {
            taskRecycleView.layoutManager = LinearLayoutManager(this@CurrentTasksActivity)
            taskRecycleView.adapter = adapter
            taskRecycleView.setItemViewCacheSize(2)
            setSupportActionBar(toolbar)

        }
        viewModel.getCurrentTasks().observe(this) { tasks ->
            adapter.setData(tasks)
            adapter.notifyDataSetChanged()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.zoom_out_button -> {
            true
        }

        R.id.progress_graph_button -> {
            true
        }

        R.id.planner_button -> {
            true
        }

        R.id.preferences_button -> {
            true
        }

        R.id.show_completed_togle -> {
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onTaskCompletedButtonClick(id: Long) {
        viewModel.processCompleteTaskButton(id)
//        TODO("Not yet implemented completed task date storage")
    }

    override fun onTaskExpandButton(id: Long) {
        viewModel
        TODO("Not yet implemented")
    }
}