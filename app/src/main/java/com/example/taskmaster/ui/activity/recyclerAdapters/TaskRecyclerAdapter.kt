package com.example.taskmaster.ui.activity.recyclerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.databinding.ItemTaskBinding
import com.example.taskmaster.model.Task
import java.time.Clock
import java.time.LocalDate
import java.time.Period

class TaskRecyclerAdapter(
    private val delegate: TaskClickDelegate,
    private val subDelegate: SubtaskClickDelegate,
    private val context: Context
) : RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {

    private var taskList: MutableList<Task> = mutableListOf()

    inner class TaskViewHolder(private val itemBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(task: Task) {
            with(itemBinding) {
                taskDescription.text = task.description
                taskCompletedButton.setOnClickListener {
                    delegate.onTaskCompletedButtonClick(task.id)
                }
                taskPriority.text = task.priority.toString()
                taskTimeLeftText.text = untilTaskEnd(task)
                taskExpandButton.setOnClickListener {
                    if (itemBinding.taskExpandButton.isChecked) {
                        taskRecycler.visibility = View.GONE
                    } else {
                        taskRecycler.visibility = View.VISIBLE
                    }

                }
                taskTagRecycler.adapter = TagRecyclerAdapter()
                taskTagRecycler.layoutManager = LinearLayoutManager(context)
                taskTagRecycler.setItemViewCacheSize(4)
                taskRecycler.adapter = SubtaskRecyclerAdapter(subDelegate)
                taskRecycler.layoutManager = LinearLayoutManager(context)
                taskRecycler.setItemViewCacheSize(2)
            }
        }

        private fun untilTaskEnd(task: Task): String {
            return if (task.expirationDate == null) {
                "-"
            } else {
                val today = LocalDate.ofEpochDay(Clock.systemUTC().millis())
                val date = LocalDate.ofEpochDay(task.expirationDate!!)
                val timeLeft = Period.between(today, date)
                "${timeLeft.years}Y\n${timeLeft.months}M\n${timeLeft.days}D"

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    fun setData(newList: List<Task>) {
        val diffTask = TaskDiffCallback(taskList, newList)
        val result = DiffUtil.calculateDiff(diffTask)
        taskList.clear()
        taskList.addAll(newList)
        result.dispatchUpdatesTo(this)
    }

}