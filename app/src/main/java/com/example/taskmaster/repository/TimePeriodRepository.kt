package com.example.taskmaster.repository

import com.example.taskmaster.database.dao.TimePeriodDao
import com.example.taskmaster.model.TimePeriod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TimePeriodRepository (private val timePeriodDao: TimePeriodDao){
    private val job = SupervisorJob()
    private val periodScope = CoroutineScope(job + Dispatchers.IO)

    fun insertPeriod(period:TimePeriod){
        periodScope.launch {
            timePeriodDao.insert(period)
        }
    }

    fun deletePeriod(id:Long){
        periodScope.launch {
            timePeriodDao.delete(id)
        }
    }

    fun deleteAll(){
        periodScope.launch {
            timePeriodDao.deleteAll()
        }
    }
}