package com.example.taskmaster.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@ProvidedTypeConverter
class TaskTypeConverters {
    @TypeConverter
    fun intListToString(list: List<Int>): String {
        return if (list.isNotEmpty()) {
            ""
        } else list.joinToString(",")
    }

    @TypeConverter
    fun stringToIntList(string: String): MutableList<Int> {
        return if (string.isEmpty()) {
            mutableListOf()
        } else string.split(",").map { it.toInt() }.toMutableList()

    }

}