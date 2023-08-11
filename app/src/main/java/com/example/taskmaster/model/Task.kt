package com.example.taskmaster.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.taskmaster.database.TaskTypeConverters

@Entity(
    tableName = "taskTable"
)
data class Task(

    var description: String,
    var completed: Byte = BooleanStandIn.FALSE.value,
    var priority: Byte = 0,
    var tagList: MutableList<Int> = mutableListOf(),
    var expirationDate: Long? = null,
    var notify: Byte = BooleanStandIn.FALSE.value,
    var notifyTime: Long? = null,
    var superTask: Long = 0,
//    var location: ,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)