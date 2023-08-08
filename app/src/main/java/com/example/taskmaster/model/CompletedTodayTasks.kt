package com.example.taskmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "completedToday"
)
data class CompletedTodayTasks(

    var taskId: Long,
    var date: Long,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)
