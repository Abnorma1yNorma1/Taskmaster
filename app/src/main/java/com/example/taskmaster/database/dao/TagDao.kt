package com.example.taskmaster.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.model.Tag

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(tag: Tag)

    @Query("SELECT COUNT(1) FROM tagTable WHERE id = :id")
    fun isTagExists(id: Int): Int

    @Query("UPDATE tagTable SET tagName = :tagName WHERE id = :id")
    fun updateName(id: Int, tagName: String)

    @Query("SELECT * FROM tagTable WHERE id = :id")
    fun getTag(id: Int): Tag

    @Query("SELECT * FROM tagTable WHERE tagName = :tagName")
    fun getTagIdByName(tagName: String): Int

    @Query("SELECT * FROM tagTable")
    fun getAll(): List<Tag>

    @Query("SELECT * FROM tagTable")
    fun getAllLive(): LiveData<List<Tag>>

    @Query("DELETE FROM tagTable WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM tagTable")
    fun deleteAll()

}