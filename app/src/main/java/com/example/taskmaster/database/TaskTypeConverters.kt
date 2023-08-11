package com.example.taskmaster.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class TaskTypeConverters {
    @TypeConverter
    fun intListToString(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun stringToIntList(string: String): MutableList<Int> {
        return string.split(",").map { it.toInt() }.toMutableList()
    }

}