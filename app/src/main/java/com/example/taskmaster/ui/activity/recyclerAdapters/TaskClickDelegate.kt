package com.example.taskmaster.ui.activity.recyclerAdapters

interface TaskClickDelegate {
    fun onTaskCompletedButtonClick(id: Long)
    fun onTaskExpandButton(id: Long)
}