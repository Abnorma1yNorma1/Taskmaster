package com.example.taskmaster.ui.activity.recyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmaster.databinding.ItemSubtaskBinding
import com.example.taskmaster.model.Task
import java.time.Clock
import java.time.LocalDate
import java.time.Period

class SubtaskRecyclerAdapter(
    private val delegate: TaskClickDelegate
) : RecyclerView.Adapter<SubtaskRecyclerAdapter.SubtaskViewHolder>() {

    private var subtaskList: MutableList<Task> = mutableListOf()

    inner class SubtaskViewHolder(private val itemBinding: ItemSubtaskBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(subtask: Task) {
            with(itemBinding) {
                subtaskTagRecycler //TODO()
                subtaskDescription.text = subtask.description
                subtaskCompletedButton.setOnClickListener{
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
}