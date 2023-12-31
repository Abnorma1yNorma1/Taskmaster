package com.example.taskmaster.ui.recyclerAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.databinding.ItemSubtaskBinding
import com.example.taskmaster.model.Tag
import com.example.taskmaster.model.Task
import java.time.Clock
import java.time.LocalDate
import java.time.Period

class SubtaskRecyclerAdapter(
    private val delegate: TaskClickDelegate,
    private val context: Context
) : RecyclerView.Adapter<SubtaskRecyclerAdapter.SubtaskViewHolder>() {

    private var subtaskList: MutableList<Task> = mutableListOf()
    private var subTagList: MutableMap<Long, List<Tag>> = mutableMapOf()

    inner class SubtaskViewHolder(private val itemBinding: ItemSubtaskBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(subtask: Task) {
            with(itemBinding) {
                subtaskTagRecycler.adapter = TagRecyclerAdapter()
                subtaskTagRecycler.layoutManager = LinearLayoutManager(context)
                subtaskTagRecycler.setItemViewCacheSize(4)
                val newTagList: MutableList<Tag> = mutableListOf()
                subTagList.forEach { mapEntry ->
                    if (mapEntry.key == subtask.id) {
                        mapEntry.value.let {
                            it.forEach { it1 -> newTagList.add(it1) }
                        }
                    }
                }
                (subtaskTagRecycler.adapter as TagRecyclerAdapter).setData(newTagList)

                subtaskDescription.text = subtask.description
                subtaskCompletedButton.setOnClickListener {
                    delegate.onTaskCompletedButtonClick(subtask.id)
                }
                subtaskPriority.text = subtask.priority.toString()
                subtaskTimeLeftText.text = untilTaskEnd(subtask)
            }

        }

        private fun untilTaskEnd(subtask: Task): String {
            return if (subtask.expirationDate == null) {
                "-"
            } else {
                val today = LocalDate.ofEpochDay(Clock.systemUTC().millis())
                val date = LocalDate.ofEpochDay(subtask.expirationDate!!)
                val timeLeft = Period.between(today, date)
                "${timeLeft.years}Y\n${timeLeft.months}M\n${timeLeft.days}D"

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtaskViewHolder {
        val itemBinding =
            ItemSubtaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubtaskViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return subtaskList.size
    }

    override fun onBindViewHolder(holder: SubtaskViewHolder, position: Int) {
        val subtask = subtaskList[position]
        holder.bind(subtask)
    }

    fun setData(newList: MutableList<Task>) {
        val diffTask = TaskDiffCallback(subtaskList, newList)
        val result = DiffUtil.calculateDiff(diffTask)
        subtaskList.clear()
        subtaskList.addAll(newList)
        result.dispatchUpdatesTo(this)
    }

    fun setSubTagList(list:MutableMap<Long, List<Tag>>){
        subTagList = list
    }
}