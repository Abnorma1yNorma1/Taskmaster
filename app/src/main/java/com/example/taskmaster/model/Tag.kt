package com.example.taskmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tagTable"
)
data class Tag(

    var tagName:String = "",
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
