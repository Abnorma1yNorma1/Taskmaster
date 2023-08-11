package com.example.taskmaster.repository

import com.example.taskmaster.database.dao.TagDao
import com.example.taskmaster.model.Tag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TagRepository (private val tagDao: TagDao) {

    private val job = SupervisorJob()
    private val tagScope = CoroutineScope(job + Dispatchers.IO)

    fun insertTag(tagName:String){
        tagScope.launch {
            tagDao.insert(Tag(tagName))
        }
    }

    suspend fun getTag(id:Int): Tag {
        return tagScope.async { tagDao.getTag(id) }.await()
    }

    fun deleteTag(tagName:String){
        tagScope.launch {
            tagDao.delete(tagDao.getTagIdByName(tagName))
        }
    }

    fun deleteAll(){
        tagScope.launch {
            tagDao.deleteAll()
        }
    }

}