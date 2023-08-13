package com.example.taskmaster.repository

import androidx.lifecycle.LiveData
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

    suspend fun isExists(id:Int):Boolean{
        return tagScope.async { tagDao.isTagExists(id)==1 }.await()
    }

    fun updateTagName(id: Int, name:String){
        tagScope.launch {
            tagDao.updateName(id, name)
        }
    }

    suspend fun getTag(id:Int): Tag {
        return tagScope.async { tagDao.getTag(id) }.await()
    }

    suspend fun getAll(): List<Tag> {
        return tagScope.async { tagDao.getAll() }.await()
    }

    fun getAllLiveData(): LiveData<List<Tag>> = tagDao.getAllLive()

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