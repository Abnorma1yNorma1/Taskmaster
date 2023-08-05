package com.example.taskmaster.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.model.TimePeriod
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Period

@Dao
interface PeriodDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(timePeriod: TimePeriod)

    @Query("UPDATE timePeriodTable SET name = :name WHERE id = :id")
    fun updateName(id: Long, name: String)

    @Query("UPDATE timePeriodTable SET startDay = :startDate, endDay = :endDate WHERE id = :id")
    fun updateDates(id: Long, startDate: OffsetDateTime, endDate: OffsetDateTime)

    @Query("UPDATE timePeriodTable SET repeatable = :repeatable WHERE id = :id")
    fun updateRepeatability(id: Long, repeatable: Char)

    @Query("UPDATE timePeriodTable SET period = :period, skipPeriod = :skipPeriod WHERE id = :id")
    fun updatePeriods(id: Long, period: Period, skipPeriod: Period)

    @Query("UPDATE timePeriodTable SET startTime = :startTime, endTime = :endTime WHERE id = :id")
    fun updateTimes(id: Long, startTime: OffsetTime, endTime: OffsetTime)

    @Query("UPDATE timePeriodTable SET idList = :idList WHERE id = :id")
    fun updateIdList(id: Long, idList: String)

    @Query("UPDATE timePeriodTable SET exceptional = :exceptional WHERE id = :id")
    fun updateExceptional(id: Long, exceptional: Char)

    @Query("SELECT * FROM timePeriodTable WHERE id = :id")
    fun getTimePeriod(id: Long): TimePeriod

    @Query("DELETE FROM timePeriodTable WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM timePeriodTable")
    fun getAll(): List<TimePeriod>

    @Query("DELETE FROM timePeriodTable")
    fun deleteAll()
}
