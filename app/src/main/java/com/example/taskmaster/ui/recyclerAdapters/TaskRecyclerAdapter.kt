package com.example.taskmaster.ui.recyclerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.databinding.ItemTaskBinding
import com.example.taskmaster.model.Tag
import com.example.taskmaster.model.Task
import java.time.Clock
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class TaskRecyclerAdapter(
    private val delegate: TaskClickDelegate,

    private val context: Context
) : RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {

    private var taskList: MutableList<Task> = mutableListOf()

    private var subtaskList: MutableMap<Long, List<Task>> = mutableMapOf()

    private var tagList: MutableMap<Long, List<Tag>> = mutableMapOf()

    private var subTagList: MutableMap<Long, List<Tag>> = mutableMapOf()

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
                taskExpandButton.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked){
                        taskRecycler.visibility = View.GONE
                    }else{
                        taskRecycler.visibility = View.VISIBLE
                    }
                }
                taskTagRecycler.adapter = TagRecyclerAdapter()
                taskTagRecycler.layoutManager = LinearLayoutManager(context)
                taskTagRecycler.setItemViewCacheSize(4)
                val newTagList: MutableList<Tag> = mutableListOf()
                tagList.forEach { mapEntry ->
                    if (mapEntry.key == task.id) {
                        mapEntry.value.let {
                            it.forEach { it1 -> newTagList.add(it1) }
                        }
                    }
                }
                (taskTagRecycler.adapter as TagRecyclerAdapter).setData(newTagList)

                taskRecycler.adapter = SubtaskRecyclerAdapter(delegate, context)
                taskRecycler.layoutManager = LinearLayoutManager(context)
                taskRecycler.setItemViewCacheSize(2)
                val newSubtaskList: MutableList<Task> = mutableListOf()
                subtaskList.forEach { mapEntry ->
                    if (mapEntry.key == task.id) {
                        mapEntry.value.let { listEntry ->
                            listEntry.forEach {
                                newSubtaskList.add(it)
                            }
                        }
                    }
                }
                with((taskRecycler.adapter as SubtaskRecyclerAdapter)) {
                    setData(newSubtaskList)
                    setSubTagList(subTagList)
                }
            }
        }

        private fun untilTaskEnd(task: Task): String {
            return if (task.expirationDate == null) {
                "-"
            } else {
                LocalDate.ofEpochDay(task.expirationDate!!).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString()


//                val today = LocalDate.ofEpochDay(Clock.systemUTC().millis())
//                val date = LocalDate.ofEpochDay(task.expirationDate!!)
//                val timeLeft = Period.between(today, date)
//                "${timeLeft.years}Y\n${timeLeft.months}M\n${timeLeft.days}D"

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

    fun setSubtaskList(list: MutableMap<Long, List<Task>>) {
        subtaskList = list
    }

    fun setTagList(list: MutableMap<Long, List<Tag>>) {
        tagList = list
    }

    fun setSubTagList(list: MutableMap<Long, List<Tag>>) {
        subTagList = list
    }

}