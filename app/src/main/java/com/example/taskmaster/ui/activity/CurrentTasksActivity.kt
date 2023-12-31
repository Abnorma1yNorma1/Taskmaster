package com.example.taskmaster.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.example.taskmaster.TaskmasterApp
import com.example.taskmaster.databinding.ActivityCurrentTaskBinding
import com.example.taskmaster.repository.TaskRepository
import com.example.taskmaster.ui.viewModel.CurrentTasksViewModel
import com.example.taskmaster.ui.viewModel.CurrentTasksViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmaster.R
import com.example.taskmaster.repository.CompletedTaskRepository
import com.example.taskmaster.repository.TagRepository
import com.example.taskmaster.repository.TimePeriodRepository
import com.example.taskmaster.ui.recyclerAdapters.TaskClickDelegate
import com.example.taskmaster.ui.recyclerAdapters.TaskRecyclerAdapter

class CurrentTasksActivity : AppCompatActivity(), TaskClickDelegate {

    private lateinit var binding: ActivityCurrentTaskBinding
    private lateinit var viewModel: CurrentTasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = TaskRecyclerAdapter(this, this)
        viewModel = ViewModelProvider(
            this, CurrentTasksViewModelFactory(
                TaskRepository(TaskmasterApp.INSTANCE.database.taskDao()),
                CompletedTaskRepository(TaskmasterApp.INSTANCE.database.completedTasksDao()),
                TagRepository(TaskmasterApp.INSTANCE.database.tagDao()),
                TimePeriodRepository(TaskmasterApp.INSTANCE.database.timePeriodDao())
            )
        ).get(CurrentTasksViewModel::class.java)
        with(binding) {
            taskRecycleView.layoutManager = LinearLayoutManager(this@CurrentTasksActivity)
            taskRecycleView.adapter = adapter
            taskRecycleView.setItemViewCacheSize(2)
//            setSupportActionBar(toolbar)
        }
        viewModel.setCurrentSubtasks()  //TODO(safe to delete+-)
        viewModel.getCurrentTasksLive().observe(this) { tasks ->
            adapter.setData(tasks)
            viewModel.setCurrentSubtasks()
            viewModel.setCurrentTag()
        }
        viewModel.currentSubtasksMediator.observe(this) {
            viewModel.setCurrentSubTags()
            with(adapter) {
                setSubtaskList(viewModel.getCurrentSubtasks())
                setTagList(viewModel.getCurrentTag())
                setSubTagList(viewModel.getCurrentSubTag())
                notifyDataSetChanged()
            }
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#6200EE")))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.current_tasks_toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.add_button -> {
            val intent: Intent = Intent(this, EditActivity::class.java)
//            intent.putExtra("tab",1)
            startActivity(intent)
            true
        }

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
//        TODO(" completed task date storage")
    }

}