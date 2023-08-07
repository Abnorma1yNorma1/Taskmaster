package com.example.taskmaster.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Entity(
    tableName = "timePeriodTable"
)
data class TimePeriod(

    var name: String,
    var startDay: Long = OffsetDateTime.now().truncatedTo(ChronoUnit.DAYS).toEpochSecond(),
    var endDay: Long = OffsetDateTime.MAX.toEpochSecond(),
    var repeatable: Byte = BooleanStandIn.FALSE.value,
    var periodYears: Byte = 0,
    var periodMonths: Byte = 0,
    var periodDays: Byte = 0,
    var skipPeriodYears: Byte = 0,
    var skipPeriodMonths: Byte = 0,
    var skipPeriodDays: Byte = 0,
    var startTimeHours: Byte,
    var startTimeMinutes: Byte,
    var startTimeSeconds: Byte,
    var endTimeHours: Byte,
    var endTimeMinutes: Byte,
    var endTimeSeconds: Byte,
    var idList: String = "",                                 //list of tags or periods
    var exceptional: Byte = BooleanStandIn.FALSE.value,      // switch for including tags or excluding periods
    //var location: ,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
