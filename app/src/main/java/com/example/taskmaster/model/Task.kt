package com.example.taskmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "taskTable"
)
data class Task(

    var description: String,
    var completed: Byte = BooleanStandIn.FALSE.value,
    var priority: Byte = 0,
    var tagList: String = "",
    var expirationDate: Long?,
    var notify: Byte = BooleanStandIn.FALSE.value,
    var notifyTime: Long? = null,
    var superTask: Long = 0,
//    var location: ,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)