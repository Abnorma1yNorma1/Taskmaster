package com.example.taskmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period

@Entity(
    tableName = "timePeriodTable"
)
data class TimePeriod(

    var name: String,
    var startDay: OffsetDateTime,
    var endDay: OffsetDateTime,
    var repeatable: Char = FALSE,
    var period: Period,
    var skipPeriod: Period,
    var startTime: OffsetTime,
    var endTime: OffsetTime,
    var idList: String,             //list of tags or periods
    var exceptional: Char = FALSE,  // switch for including tags or excluding periods
    //var location: ,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) {
    companion object {

        const val TRUE: Char = '1'
        const val FALSE: Char = '0'
    }
}
