package com.example.taskmaster.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.model.TimePeriod
import java.time.OffsetTime
import java.time.Period

@Dao
interface TimePeriodDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(timePeriod: TimePeriod)

    @Query("UPDATE timePeriodTable SET name = :name WHERE id = :id")
    fun updateName(id: Long, name: String)

    @Query("UPDATE timePeriodTable SET startDay = :startDate, endDay = :endDate WHERE id = :id")
    fun updateDates(id: Long, startDate: Long, endDate: Long)

    @Query("UPDATE timePeriodTable SET repeatable = :repeatable WHERE id = :id")
    fun updateRepeatability(id: Long, repeatable: Byte)

    @Query("UPDATE timePeriodTable SET " +
            "periodYears = :periodYears, periodMonths = :periodMonths, periodDays = :periodDays," +
            " skipPeriodYears = :skipPeriodYears, skipPeriodMonths = :skipPeriodMonths, skipPeriodDays = :skipPeriodDays" +
            " WHERE id = :id")
    fun updatePeriods(id: Long, periodYears: Byte, periodMonths: Byte, periodDays: Byte,
                      skipPeriodYears: Byte, skipPeriodMonths: Byte, skipPeriodDays: Byte)

    @Query("UPDATE timePeriodTable SET " +
            "startTimeHours = :startTimeHours, startTimeMinutes = :startTimeMinutes, startTimeSeconds = :startTimeSeconds," +
            " endTimeHours = :endTimeHours, endTimeMinutes = :endTimeMinutes, endTimeSeconds = :endTimeSeconds " +
            "WHERE id = :id")
    fun updateTimes(id: Long, startTimeHours: Byte, startTimeMinutes: Byte, startTimeSeconds: Byte,
                    endTimeHours: Byte, endTimeMinutes: Byte, endTimeSeconds: Byte)

    @Query("UPDATE timePeriodTable SET idList = :idList WHERE id = :id")
    fun updateIdList(id: Long, idList: String)

    @Query("UPDATE timePeriodTable SET exceptional = :exceptional WHERE id = :id")
    fun updateExceptional(id: Long, exceptional: Byte)

    @Query("SELECT * FROM timePeriodTable WHERE id = :id")
    fun getTimePeriod(id: Long): TimePeriod

    @Query("DELETE FROM timePeriodTable WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT * FROM timePeriodTable")
    fun getAll(): List<TimePeriod>

    @Query("DELETE FROM timePeriodTable")
    fun deleteAll()
}
