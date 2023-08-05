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
    var priority: Byte,
    var tagList: String,
//    var location: ,
    var expirationDate: OffsetDateTime,
    var superTask: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) {
    companion object {

        const val TRUE: Char = '1'
        const val FALSE: Char = '0'
    }
}
