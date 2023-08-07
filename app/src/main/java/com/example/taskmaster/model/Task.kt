package com.example.taskmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(
    tableName = "taskTable"
)
data class Task(

    var description: String,
    var completed: Char = FALSE,
    var priority: Byte = 0,
    var tagList: String = "",
    var expirationDate: Long?,
    var notify: Char = FALSE,
    var notifyTime: Long? = null,
    var superTask: Long = 0,
//    var location: ,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) {
    companion object {

        const val TRUE: Char = '1'
        const val FALSE: Char = '0'
    }
}
